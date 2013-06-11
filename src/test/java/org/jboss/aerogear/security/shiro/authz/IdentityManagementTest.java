package org.jboss.aerogear.security.shiro.authz;

import org.apache.shiro.subject.Subject;
import org.jboss.aerogear.security.shiro.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.when;

public class IdentityManagementTest {


    @Mock
    private Subject subject;

    @Mock
    private EntityManager entityManager;

    @Mock
    private User user;

    @InjectMocks
    private IdentityManagementImpl identityManagement;

    @Before
    public void setUp() throws Exception {
        identityManagement = new IdentityManagementImpl();
        MockitoAnnotations.initMocks(this);
        when(subject.getPrincipal()).thenReturn(1L);
        when(entityManager.find(User.class, 1L)).thenReturn(user);
    }

    @Test
    public void testGetSecret() throws Exception {
        final String secret = "XXCJVM65LRQXCJ4M";
        when(user.getSecret()).thenReturn(secret);
        assertEquals(secret, identityManagement.getSecret());
    }

    @Test
    public void testGetNewSecret() throws Exception {
        final String secret = "XXCJVM65LRQXCJ4M";
        when(entityManager.find(User.class, 1L)).thenReturn(new User("homer", "123"));
        assertNotSame(secret, identityManagement.getSecret());
        assertNotNull(identityManagement.getSecret());
    }
}
