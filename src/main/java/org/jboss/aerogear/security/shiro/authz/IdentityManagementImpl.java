package org.jboss.aerogear.security.shiro.authz;

import org.apache.shiro.crypto.hash.Sha512Hash;
import org.jboss.aerogear.security.authz.IdentityManagement;
import org.jboss.aerogear.security.model.AeroGearUser;
import org.jboss.aerogear.security.shiro.model.Role;
import org.jboss.aerogear.security.shiro.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class IdentityManagementImpl implements IdentityManagement {

    @Inject
    private EntityManager entityManager;

    @Inject
    private GrantConfiguration grantConfiguration;

    @Override
    public GrantMethods grant(String... roles) {
        return grantConfiguration.roles(roles);
    }

    @Override
    public AeroGearUser get(String id) throws RuntimeException {
        return null;
    }

    @Override
    public void remove(AeroGearUser aeroGearUser) {

    }

    @Override
    public List<AeroGearUser> findAllByRole(String role) {
        return null;
    }

    @Override
    public void create(AeroGearUser aeroGearUser) {
        User user = new User(aeroGearUser.getUsername(),
                new Sha512Hash(aeroGearUser.getPassword()).toHex());
        entityManager.persist(user);
    }
}
