package org.jboss.aerogear.security.shiro.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.jboss.aerogear.security.shiro.authz.SecurityRealm;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@Singleton
public class ShiroConfig {

    private SecurityManager securityManager;

    @Inject
    private SecurityRealm securityRealm;

    @PostConstruct
    public void init() {
        SecurityManager securityManager = new DefaultSecurityManager(securityRealm);
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Produces
    @Named("securityManager")
    public SecurityManager getSecurityManager() {
        return securityManager;
    }

    @Produces
    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}

