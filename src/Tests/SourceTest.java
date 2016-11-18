package src.Tests;

import junit.framework.TestCase;
import org.testng.annotations.Test;
import src.model.*;


/**
 * JUnit for checking valid location and string input in SourceFinding
 */
public class SourceTest extends TestCase {

    private static final int TIMEOUT = 200;

    private SourceFinding[] sources;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        sources = new SourceFinding[6];
        sources[0] = new SourceFinding("", "", SourceType.BOTTLED, WaterCondition.CLEAR, 0.0, 0.0);
        sources[1] = new SourceFinding("a", "b ", SourceType.BOTTLED, WaterCondition.CLEAR, 0.0, 0.0);
        sources[2] = new SourceFinding("a", "b ", SourceType.BOTTLED, WaterCondition.CLEAR, Double.MAX_VALUE, Double.MIN_VALUE);
        sources[3] = new SourceFinding("", "", SourceType.BOTTLED, WaterCondition.CLEAR, -200.00, 200.00);
        sources[4] = new SourceFinding("a", "b ", SourceType.BOTTLED, WaterCondition.CLEAR, 0.0, 0.0);
        sources[5] = new SourceFinding("a", "b ", SourceType.BOTTLED, WaterCondition.CLEAR, 0.0, 0.0);
    }

    /**
     * checks that the location is within range -180 to 180
     *
     * @throws Exception exceptions
     */
    @Test(timeOut = TIMEOUT)
    public void testValidLocation() throws Exception {
        sources[3].addToFile();
        boolean check = sources[1].valid();
        assertFalse(check);
    }
    /**
     * checks that the String values are not null
     *
     * @throws Exception exceptions
     */
    @Test(timeOut = TIMEOUT)
    public void testValidString() throws Exception {
        sources[1].addToFile();
        boolean check = sources[1].valid();
        assertTrue(check);
    }
}