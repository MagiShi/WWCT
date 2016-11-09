package src.Tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import src.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Cassie on 11/9/2016.
 */
public class GraphTests {;
    public static final int TIMEOUT = 200;
    private Graph graph;
    private ArrayList<Float> expectedVirus = new ArrayList<>();
    private ArrayList<Float> expectedContam = new ArrayList<>();
    private ArrayList<Integer> expectedYear = new ArrayList<>();
    private ArrayList<Integer> expectedMonth = new ArrayList<>();

    private void assertException(String message, Class<? extends Exception> exceptionClass, Runnable code) {
        assertException(message, new Class[]{exceptionClass}, code);
    }
    private void assertException(String message, Class<? extends Exception>[] exceptionClasses, Runnable code) {
        try {
            code.run();
            Assert.fail(message);
        } catch (Exception e) {
            boolean foundException = false;
            for (Class<? extends Exception> exceptionClass: exceptionClasses) {
                if (exceptionClass.equals(e.getClass())) {
                    foundException = true;
                }
            }

            if (!foundException) {
                e.printStackTrace();
                Assert.fail(message);
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

    @Before
    public void setUp() {
        ArrayList<Report> currentReports = new ArrayList<>();
        boolean alreadyExists = new File("purityReports.csv").exists();
        if (alreadyExists) {
            try (BufferedReader br = new BufferedReader(new FileReader("purityReports.csv"))) {
                String line = "";
                while (((line = br.readLine()) != null)) {
                    String[] data = line.split(",");
                    for (int i = 0; i < data.length; i++) {
                        System.out.print(data[i] + ", ");
                    }
                    System.out.println();
                    Double lat = new Double(data[7]);
                    Double longit = new Double(data[8]);
                    if (33 == lat && -88 == longit) {
                        Report r = new Report(data[1], data[2], Float.parseFloat(data[4]), Float.parseFloat(data[5]), data[6]);
                        currentReports.add(r);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        graph = new Graph(currentReports);
    }

    @Test(timeout = TIMEOUT)
    public void initialGraphValues() {
        ArrayList<Float> expectedAllVirus = new ArrayList<>();
        expectedAllVirus.add(new Float(89));
        expectedAllVirus.add(new Float(23));
        expectedAllVirus.add(new Float(13));
        expectedAllVirus.add(new Float(1));
        ArrayList<Float> expectedAllContam = new ArrayList<>();
        expectedAllContam.add(new Float(98));
        expectedAllContam.add(new Float(325));
        expectedAllContam.add(new Float(13));
        expectedAllContam.add(new Float(1));
        ArrayList<Integer> expectedAllMonth = new ArrayList<>();
        expectedAllMonth.add(new Integer(10));
        expectedAllMonth.add(new Integer(10));
        expectedAllMonth.add(new Integer(10));
        expectedAllMonth.add(new Integer(10));
        ArrayList<Integer> expectedAllYear = new ArrayList<>();
        expectedAllYear.add(new Integer(2016));
        expectedAllYear.add(new Integer(2016));
        expectedAllYear.add(new Integer(2016));
        expectedAllYear.add(new Integer(2016));
        assertEquals(graph.getAllVirusNumList(), expectedAllVirus);
        assertEquals(graph.getAllContamNumList(), expectedAllContam);
        assertEquals(graph.getAllMonthList(), expectedAllMonth);
        assertEquals(graph.getAllYearList(), expectedAllYear);
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
    }
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
        expectedVirus.add(new Float(89));
        expectedVirus.add(new Float(23));
        expectedVirus.add(new Float(13));
        expectedVirus.add(new Float(1));
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(false);
        graph.setContaminantDisplayed(true);
        expectedVirus.clear();
        expectedContam.add(new Float(98));
        expectedContam.add(new Float(325));
        expectedContam.add(new Float(13));
        expectedContam.add(new Float(1));
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(true);
        expectedYear.add(2016);
        expectedYear.add(2016);
        expectedYear.add(2016);
        expectedYear.add(2016);
        expectedVirus.add(new Float(89));
        expectedVirus.add(new Float(23));
        expectedVirus.add(new Float(13));
        expectedVirus.add(new Float(1));
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
    }

    @Test(timeout = TIMEOUT)
    public void testInvalidYears() {
        assertException(
                "The graph should throw an exception when an invalid year is used.",
                IllegalArgumentException.class,
                () -> graph.setCurrentYear(1995));
        assertException(
                "The graph should throw an exception when an invalid year is used.",
                IllegalArgumentException.class,
                () -> graph.setCurrentYear(1999));
        assertException(
                "The graph should throw an exception when an invalid year is used.",
                IllegalArgumentException.class,
                () -> graph.setCurrentYear(2021));

    }
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
        expectedVirus.add(new Float(89));
        expectedVirus.add(new Float(23));
        expectedVirus.add(new Float(13));
        expectedVirus.add(new Float(1));
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(false);
        graph.setContaminantDisplayed(true);
        expectedVirus.clear();
        expectedContam.add(new Float(98));
        expectedContam.add(new Float(325));
        expectedContam.add(new Float(13));
        expectedContam.add(new Float(1));
        assertEquals(expectedVirus, graph.getCurrentVirusNumList());
        assertEquals(expectedContam, graph.getCurrentContamNumList());
        assertEquals(expectedMonth, graph.getCurrentMonthList());
        assertEquals(expectedYear, graph.getCurrentYearList());
        graph.setVirusDisplayed(true);
        expectedMonth.add(10);
        expectedMonth.add(10);
        expectedMonth.add(10);
        expectedMonth.add(10);
        expectedVirus.add(new Float(89));
        expectedVirus.add(new Float(23));
        expectedVirus.add(new Float(13));
        expectedVirus.add(new Float(1));
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
