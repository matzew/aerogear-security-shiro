package org.jboss.aerogear.security.shiro.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.subject.Subject;
import org.jboss.aerogear.security.auth.AuthenticationManager;
import org.jboss.aerogear.security.auth.SessionId;
import org.jboss.aerogear.security.model.AeroGearUser;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Logger;

@ApplicationScoped
public class AuthenticationManagerImpl implements AuthenticationManager {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationManagerImpl.class.getSimpleName());

    @Inject
    private Subject subject;

    @SessionId
    @Produces
    private Serializable sessionId;

    @Override
    public boolean login(AeroGearUser aeroGearUser) {
        UsernamePasswordToken token = new UsernamePasswordToken(aeroGearUser.getUsername(),
                new Sha512Hash(aeroGearUser.getPassword()).toHex());

        subject.login(token);
        if (!subject.isAuthenticated()) {
            throw new RuntimeException("Authentication failed");
        }
        sessionId = subject.getSession().getId();
        return true;
    }

    @Override
    public void logout() {
        subject.logout();
    }
}
