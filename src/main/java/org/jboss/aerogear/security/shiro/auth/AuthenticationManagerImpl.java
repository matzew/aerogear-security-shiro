package org.jboss.aerogear.security.shiro.auth;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jboss.aerogear.security.auth.AuthenticationManager;
import org.jboss.aerogear.security.auth.SessionId;
import org.jboss.aerogear.security.model.AeroGearUser;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class AuthenticationManagerImpl implements AuthenticationManager {

    @Inject
    private Subject subject;

    @SessionId
    @Produces
    private static java.io.Serializable sessionId;

    @Override
    public boolean login(AeroGearUser aeroGearUser) {
        boolean isLoggedIn;
        UsernamePasswordToken token = new UsernamePasswordToken(aeroGearUser.getUsername(), aeroGearUser.getPassword());
        try {
            subject.login(token);
            sessionId = subject.getSession().getId();
            isLoggedIn = subject.isAuthenticated();
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new RuntimeException("Authentication failed");
        }
        return isLoggedIn;
    }

    @Override
    public void logout() {
        subject.logout();
    }
}
