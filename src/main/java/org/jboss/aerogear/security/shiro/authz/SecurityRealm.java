package org.jboss.aerogear.security.shiro.authz;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jboss.aerogear.security.authz.IdentityManagement;
import org.jboss.aerogear.security.shiro.model.Role;
import org.jboss.aerogear.security.shiro.model.User;

import javax.inject.Inject;

public class SecurityRealm extends AuthorizingRealm {

    @Inject
    private IdentityManagement identityManagerment;

    public SecurityRealm() {
        setName("SecurityRealm");
        setCredentialsMatcher(new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME));
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authToken;

        User user = (User) identityManagerment.findByUsername(token.getUsername());

        if (user != null) {
            return new SimpleAuthenticationInfo(user.getId(), new Sha512Hash(user.getPassword()), getName());
        } else {
            throw new RuntimeException("Authentication failed");
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        try {
            Long userId = (Long) (principals.fromRealm(getName()).iterator().next());
            User user = (User) identityManagerment.findById(userId);
            if (user != null) {
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                for (Role role : user.getRoles()) {
                    info.addRole(role.getName());
                    info.addStringPermissions(role.getPermissions());
                }
                return info;
            } else {
                throw new RuntimeException("Not authorized");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Authorization has failed");
        }
    }


}
