package src.Tests;

import junit.framework.TestCase;
import org.testng.annotations.Test;
import src.model.StatusChange;

/**
 * JUnits for the Registration Object's public methods
 * Testing for full branch coverage by checking the if statements of the register method
 */
public class BanTests extends TestCase {
    private static final int TIMEOUT = 200;
    private StatusChange[] sc;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        sc = new StatusChange[10];

        sc[6] = new StatusChange("aa");
        sc[7] = new StatusChange("");
        sc[8] = new StatusChange("DNE");
    }

    /**
     * Tests banning a user that exists in the database
     * @throws Exception if any Exceptions occur
     */
    @Test (timeOut = TIMEOUT)
    public void testBanExistingUser() throws Exception {
        sc[0] = new StatusChange("aaa");
        assertTrue(sc[0].changeBanStatus("ban"));
        sc[1] = new StatusChange("bbb");
        assertTrue(sc[1].changeBanStatus("ban"));
        sc[2] = new StatusChange("ccc");
        assertTrue(sc[2].changeBanStatus("ban"));
    }

    /**
     * Tests unbanning a user that exists in the database
     * @throws Exception if any Exceptions occur
     */
    @Test (timeOut = TIMEOUT)
    public void testUnbanExistingUser() throws Exception {
        sc[3] = new StatusChange("ddd");
        assertTrue(sc[3].changeBanStatus("unban"));
        sc[4] = new StatusChange("zzz");
        assertTrue(sc[4].changeBanStatus("unban"));
        sc[5] = new StatusChange("123");
        assertTrue(sc[5].changeBanStatus("unban"));
    }

    /**
     * Tests banning a user that doesn't exist in the database
     * @throws Exception if any Exceptions occur
     */
    @Test (timeOut = TIMEOUT)
    public void testBanNonexistentUser() throws Exception {
        assertTrue(!sc[6].changeBanStatus("ban"));
        assertTrue(!sc[7].changeBanStatus("ban"));
        assertTrue(!sc[8].changeBanStatus("ban"));
    }

    /**
     * Tests unbanning a user that doesn't exist in the database
     * @throws Exception if any Exceptions occur
     */
    @Test (timeOut = TIMEOUT)
    public void testUnbanNonexistentUser() throws Exception {
        assertTrue(!sc[6].changeBanStatus("unban"));
        assertTrue(!sc[7].changeBanStatus("unban"));
        assertTrue(!sc[8].changeBanStatus("unban"));
    }


}

