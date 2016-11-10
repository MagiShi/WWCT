package src.Tests;

import junit.framework.TestCase;
import org.junit.Before;
import org.testng.annotations.Test;
import src.model.Registration;
import src.model.UserType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Maggie on 11/9/2016.
 */
public class RegistrationTest extends TestCase {

    public static final int TIMEOUT = 200;

    //need to check : checkOriginal -> in, not in. (Is String so no other testing). //database empty
    //passwords match/don't match/both blank/one blank
    //

    private ArrayList<String> notIn;
    private ArrayList<String> inData;
    private ArrayList<String> passwords;
    private Registration[] reg;

    public void setUp() throws Exception {
        super.setUp();

        reg = new Registration[10];
        reg[0] = new Registration("", "", "", UserType.USER, "");
        reg[1] = new Registration("", "pass", "pass", UserType.USER, "");
        reg[2] = new Registration("a", "pass", "pass", UserType.USER, "");
        reg[3] = new Registration("b", "pass", "", UserType.USER, "");
        reg[4] = new Registration("c", "not", "pass", UserType.USER, "");
        reg[5] = new Registration("abcdefghijklmnopqrstuvwxyz", "", "", UserType.USER, "");
        reg[6] = new Registration("aaa", "password", "password", UserType.USER, "");
        reg[7] = new Registration("bbb", "pass", "", UserType.USER, "");
        reg[8] = new Registration("ccc", "not", "pass", UserType.USER, "");
        reg[9] = new Registration("ddd", "", "", UserType.USER, "");

    }

    @Test (timeOut = TIMEOUT)
    public void testNotInDatabase() throws Exception {
        boolean check = reg[0].checkOriginal();
        assertTrue(check);
        check = reg[1].checkOriginal();
        assertTrue(check);
        check = reg[2].checkOriginal();
        assertTrue(check);
        check = reg[3].checkOriginal();
        assertTrue(check);
        check = reg[4].checkOriginal();
        assertTrue(check);
        check = reg[5].checkOriginal();
        assertTrue(check);
    }

    @Test (timeOut = TIMEOUT)
    public void testInDatabase() throws Exception {
        boolean check = reg[6].checkOriginal();
        assertTrue(!check);
        check = reg[7].checkOriginal();
        assertTrue(!check);
        check = reg[8].checkOriginal();
        assertTrue(!check);
        check = reg[9].checkOriginal();
        assertTrue(!check);
    }

    @Test (timeOut = TIMEOUT)
    public void testPasswordsMatch() throws Exception {
        boolean check = reg[1].checkPassword();
        assertTrue(check);
        check = reg[2].checkPassword();
        assertTrue(check);
        check = reg[6].checkPassword();
        assertTrue(check);
    }

    @Test (timeOut = TIMEOUT)
    public void testPasswordsNoMatch() throws Exception {
        boolean check = reg[3].checkPassword();
        assertTrue(!check);
        check = reg[4].checkPassword();
        assertTrue(!check);
        check = reg[7].checkPassword();
        assertTrue(!check);
        check = reg[8].checkPassword();
        assertTrue(!check);
    }

    @Test (timeOut = TIMEOUT)
    public void testPasswordBlank() throws Exception {
        boolean check = reg[0].checkPassword();
        assertTrue(!check);
        check = reg[9].checkPassword();
        assertTrue(!check);
    }

}

