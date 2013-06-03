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
import org.jboss.aerogear.security.shiro.model.Role;
import org.jboss.aerogear.security.shiro.model.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SecurityRealm extends AuthorizingRealm {

    @Inject
    private EntityManager entityManager;

    public SecurityRealm() {
        setName("SecurityRealm");
        setCredentialsMatcher(new HashedCredentialsMatcher(Sha512Hash.ALGORITHM_NAME));
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        Query query = entityManager.createNamedQuery("User.findByUsername", User.class)
                .setParameter("username", token.getUsername());
        User user = (User) query.getSingleResult();

        if (user != null) {
            return new SimpleAuthenticationInfo(user.getId(), new Sha512Hash(user.getPassword()), getName());
        } else {
            return null;
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Long userId = (Long) principals.fromRealm(getName()).iterator().next();
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            for (Role role : user.getRoles()) {
                info.addRole(role.getName());
                info.addStringPermissions(role.getPermissions());
            }
            return info;
        } else {
            return null;
        }
    }


}
