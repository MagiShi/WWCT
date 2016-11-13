package src.Tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.model.Graph;
import src.model.Report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * JUnits for the Graph Object's methods
 */
public class GraphTests {;
    private static final int TIMEOUT = 200;
    private Graph graph;
    private final Collection<Float> expectedVirus = new ArrayList<>();
    private final Collection<Float> expectedContam = new ArrayList<>();
    private final Collection<Integer> expectedYear = new ArrayList<>();
    private final Collection<Integer> expectedMonth = new ArrayList<>();

    private void assertException(Class<? extends Exception> exceptionClass, Runnable code) {
        assertException(
                new Class[]{exceptionClass}, code);
    }
    private void assertException(Class<? extends Exception>[] exceptionClasses, Runnable code) {
        try {
            code.run();
            Assert.fail("The graph should throw an exception when an invalid year is used.");
        } catch (Exception e) {
            boolean foundException = false;
            for (Class<? extends Exception> exceptionClass: exceptionClasses) {
                if (exceptionClass.equals(e.getClass())) {
                    foundException = true;
                }
            }

            if (!foundException) {
                e.printStackTrace();
                Assert.fail("The graph should throw an exception when an invalid year is used.");
            } else {
                assertNotNull(
                        "Exception messages must not be empty",
                        e.getMessage());
                assertNotEquals(
                        "Exception messages must not be empty",
                        "",
                        e.getMessage());
            }
        }
    }

    /**
     * Prepares for testing
     */
    @Before
    public void setUp() {
        ArrayList<Report> currentReports = new ArrayList<>();
        File tempFile = new File("purityReports.csv");
        boolean alreadyExists = tempFile.exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("purityReports.csv"))) {
                String line;
                line = br.readLine();
                while (line != null) {
                    String[] data = line.split(",");
                    Double lat = new Double(data[7]);
                    Double longit = new Double(data[8]);
                    if ((33 == lat) && (-88 == longit)) {
                        Report r = new Report(data[1], data[2], Float.parseFloat(data[4]),
                                Float.parseFloat(data[5]), data[6]);
                        currentReports.add(r);
                    }
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        graph = new Graph(currentReports);
    }

    /**
     * Sets initial values in the graph for testing
     */
    @Test(timeout = TIMEOUT)
    public void initialGraphValues() {
        Collection<Float> expectedAllVirus = new ArrayList<>();
        expectedAllVirus.add(89f);
        expectedAllVirus.add(23f);
        expectedAllVirus.add(13f);
        expectedAllVirus.add(1f);
        Collection<Float> expectedAllContam = new ArrayList<>();
        expectedAllContam.add(98f);
        expectedAllContam.add(325f);
        expectedAllContam.add(13f);
        expectedAllContam.add(1f);
        Collection<Integer> expectedAllMonth = new ArrayList<>();
        expectedAllMonth.add(10);
        expectedAllMonth.add(10);
        expectedAllMonth.add(10);
        expectedAllMonth.add(10);
        Collection<Integer> expectedAllYear = new ArrayList<>();
        expectedAllYear.add(2016);
        expectedAllYear.add(2016);
        expectedAllYear.add(2016);
        expectedAllYear.add(2016);
        assertEquals(graph.getAllVirusNumList(), expectedAllVirus);
        assertEquals(graph.getAllContamNumList(), expectedAllContam);
        assertEquals(graph.getAllMonthList(), expectedAllMonth);
        assertEquals(graph.getAllYearList(), expectedAllYear);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
    }

    /**
     * Check all years
     */
    @Test(timeout = TIMEOUT)
    public void allYears() {
        graph.setShowAll();
        graph.setVirusDisplayed(false);
        graph.setContaminantDisplayed(false);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(true);
        expectedYear.add(2016);
        expectedYear.add(2016);
        expectedYear.add(2016);
        expectedYear.add(2016);
        expectedVirus.add(89f);
        expectedVirus.add(23f);
        expectedVirus.add(13f);
        expectedVirus.add(1f);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(false);
        graph.setContaminantDisplayed(true);
        expectedVirus.clear();
        expectedContam.add(98f);
        expectedContam.add(325f);
        expectedContam.add(13f);
        expectedContam.add(1f);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(true);
        expectedYear.add(2016);
        expectedYear.add(2016);
        expectedYear.add(2016);
        expectedYear.add(2016);
        expectedVirus.add(89f);
        expectedVirus.add(23f);
        expectedVirus.add(13f);
        expectedVirus.add(1f);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
    }

    /**
     * Checks years that aren't valid (<2000 or >2020)
     */
    @Test(timeout = TIMEOUT)
    public void testInvalidYears() {
        assertException(
                IllegalArgumentException.class,
                () -> graph.setCurrentYear(1995));
        assertException(
                IllegalArgumentException.class,
                () -> graph.setCurrentYear(1999));
        assertException(
                IllegalArgumentException.class,
                () -> graph.setCurrentYear(2021));

    }

    /**
     * Checks years that are valid
     */
    @Test(timeout = TIMEOUT)
    public void testValidYears() {
        graph.setCurrentYear(2016);
        graph.setVirusDisplayed(false);
        graph.setContaminantDisplayed(false);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(true);
        expectedMonth.add(10);
        expectedMonth.add(10);
        expectedMonth.add(10);
        expectedMonth.add(10);
        expectedVirus.add(89f);
        expectedVirus.add(23f);
        expectedVirus.add(13f);
        expectedVirus.add(1f);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(false);
        graph.setContaminantDisplayed(true);
        expectedVirus.clear();
        expectedContam.add(98f);
        expectedContam.add(325f);
        expectedContam.add(13f);
        expectedContam.add(1f);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(true);
        expectedMonth.add(10);
        expectedMonth.add(10);
        expectedMonth.add(10);
        expectedMonth.add(10);
        expectedVirus.add(89f);
        expectedVirus.add(23f);
        expectedVirus.add(13f);
        expectedVirus.add(1f);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        expectedContam.clear();
        expectedVirus.clear();
        expectedMonth.clear();
        expectedYear.clear();
        graph.setCurrentYear(2000);
        graph.setVirusDisplayed(false);
        graph.setContaminantDisplayed(false);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(true);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(false);
        graph.setContaminantDisplayed(true);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(true);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setContaminantDisplayed(false);
        graph.setVirusDisplayed(false);
        graph.setCurrentYear(2020);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(true);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(false);
        graph.setContaminantDisplayed(true);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(true);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
    }
}