package org.jboss.aerogear.security.shiro.authz;

import org.jboss.aerogear.security.authz.IdentityManagement;
import org.jboss.aerogear.security.model.AeroGearUser;

import java.util.List;

public class IdentityManagementImpl implements IdentityManagement {

    @Override
    public GrantMethods grant(String... roles) {
        return null;
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
//        User user = new User(username, new Sha512Hash(password).toHex());
//        entityManager.persist(user);
        //TODO
    }
}
