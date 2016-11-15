package src.Tests;

import junit.framework.TestCase;
import org.testng.annotations.Test;
import src.model.LogIn;
import src.model.User;

/**
 * Created by Ji Won Lee on 11/14/2016.
 */
public class LogInTest extends TestCase {
    private static final int TIMEOUT = 200;
    private LogIn[] login;
    @Override
    public void setUp() throws Exception {
        super.setUp();

        login = new LogIn[10];
    }

    /**

     */
    /**
     * tries logging in with invalid username that's blank or not stored in the database
     * @throws Exception Any exceptions that occur
     */
    @Test (timeOut = TIMEOUT)
    public void testInvalidUsername() throws Exception {
        login[0] = new LogIn("","pass");
        assertTrue(login[0].logIn() == null);
        login[1] = new LogIn("THISUSERNAMEDOESNOTEXIST","pass");
        assertTrue(login[1].logIn() == null);
    }
    /**
     * tries logging in with invalid password that's blank or not stored in the database
     * @throws Exception Any exceptions that occur
     */
    @Test (timeOut = TIMEOUT)
    public void testInvalidPassword() throws Exception {
        login[2] = new LogIn("user","");
        assertTrue(login[2].logIn() == null);
        login[3] = new LogIn("user","THISPASSWORDDOESNOTEXIST");
        assertTrue(login[3].logIn() == null);
    }
    /**
     * tries logging in with valid username and password stored in the database
     * @throws Exception Any exceptions that occur
     */
    @Test (timeOut = TIMEOUT)
    public void validUsernamePassword() throws Exception {
        login[4] = new LogIn("user","pass");
        assertTrue((login[4].logIn()).equals(new User("user", "pass", "user", "USER", "[set email]", "[set address]", "Not Banned")));
        login[5] = new LogIn("bbb","bbb");
        assertTrue((login[5].logIn()).equals(new User("bbb", "bbb", "bbb", "USER", "[set email]", "[set address]", "Not Banned")));
    }
    /**
     * tries logging in with both invalid username and invalid password
     * @throws Exception Any exceptions that occur
     */
    @Test (timeOut = TIMEOUT)
    public void invalidUsernamePassword() throws Exception {
        login[6] = new LogIn("THISUSERNAMEDOESNOTEXIST","THISPASSWORDDOESNOTEXIST");
        assertTrue(login[6].logIn() == null);
        login[7] = new LogIn("","");
        assertTrue(login[7].logIn() == null);
    }



}
