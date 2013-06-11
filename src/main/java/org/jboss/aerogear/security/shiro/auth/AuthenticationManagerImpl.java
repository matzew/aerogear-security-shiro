/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.aerogear.security.shiro.auth;

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
    public boolean login(AeroGearUser user) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),
                new Sha512Hash(user.getPassword()).toHex());

        subject.login(token);
        if (subject.isAuthenticated()) {
            sessionId = subject.getSession().getId();
        } else {
            throw new RuntimeException("Authentication failed");
        }

        return true;
    }

    @Override
    public void logout() {
        subject.logout();
    }
}
