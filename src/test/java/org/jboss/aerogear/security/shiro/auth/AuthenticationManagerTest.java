package org.jboss.aerogear.security.shiro.auth;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.jboss.aerogear.security.auth.AuthenticationManager;
import org.jboss.aerogear.security.model.AeroGearUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthenticationManagerTest {

    @Mock
    private AeroGearUser user;
    @Mock
    private Subject subject;
    @Mock
    private Session session;

    @InjectMocks
    private AuthenticationManager authenticationManager = new AuthenticationManagerImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(user.getUsername()).thenReturn("john");
        when(user.getPassword()).thenReturn("123");
        when(session.getId()).thenReturn("test");
        when(subject.getSession()).thenReturn(session);
    }

    @Test
    public void testLogin() throws Exception {
        when(subject.isAuthenticated()).thenReturn(true);
        authenticationManager.login(user);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidLogin() throws Exception {
        when(subject.isAuthenticated()).thenReturn(false);
        authenticationManager.login(user);
    }

    @Test
    public void testLogout() throws Exception {
        when(subject.isAuthenticated()).thenReturn(true);
        authenticationManager.logout();
        verify(subject).logout();
    }
}
