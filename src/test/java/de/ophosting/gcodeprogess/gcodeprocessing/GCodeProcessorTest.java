package de.ophosting.gcodeprogess.gcodeprocessing;

import org.junit.Before;
import org.junit.Test;

import static de.ophosting.gcodeprogess.gcodeprocessing.GCodeProcessor.PROCESSING_THRESHOLD;
import static org.junit.Assert.*;

/**
 * @author Jan Ophey
 */
public class GCodeProcessorTest {

    private GCodeProcessor sut;

    @Before
    public void setUp() {
        sut = new GCodeProcessor();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatProgressNegativeValueThrows() throws Exception {
        sut.formatProgress(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatProgressValueLargerThan1000Throws() throws Exception {
        sut.formatProgress(1001);
    }

    @Test
    public void testFormatProgressValue1000() throws Exception {
        final String val = sut.formatProgress(1000);
        assertEquals("val should be '100,0'", "100,0", val);
    }


    @Test
    public void testFormatProgressValue0() throws Exception {
        final String val = sut.formatProgress(0);
        assertEquals("val should be '  0,0'", "  0,0", val);
    }

    @Test
    public void testFormatProgressValue1() throws Exception {
        final String val = sut.formatProgress(1);
        assertEquals("val should be '  0,1'", "  0,1", val);
    }

    @Test
    public void testFormatProgressValue999() throws Exception {
        final String val = sut.formatProgress(999);
        assertEquals("val should be ' 99,9'", " 99,9", val);
    }

    @Test
    public void testContainsCommand() throws Exception {
        assertTrue(sut.containsCommand("G28"));
        assertTrue(sut.containsCommand("G1"));
        assertTrue(sut.containsCommand("M117"));
        assertFalse(sut.containsCommand(";This is a comment"));
        assertFalse(sut.containsCommand(""));
    }

    @Test
    public void calculateStepsPerPermilleWithCommandCountSmallerThanThreshold() throws Exception {
        final int commandCount = GCodeProcessor.PROCESSING_THRESHOLD - 1;
        assertEquals(PROCESSING_THRESHOLD, sut.calculateStepsPerPermille(commandCount), 0.1);
    }

    @Test
    public void calculateStepsPerPermilleWithCommandCountEqualsThreshold() throws Exception {
        final int commandCount = GCodeProcessor.PROCESSING_THRESHOLD;
        assertEquals(PROCESSING_THRESHOLD, sut.calculateStepsPerPermille(commandCount), 0.1);
    }

    @Test
    public void calculateStepsPerPermilleWithCommandCountGreaterThanThreshold() throws Exception {
        final int commandCount = GCodeProcessor.PROCESSING_THRESHOLD + 1;
        assertEquals(commandCount / 1000.0, sut.calculateStepsPerPermille(commandCount), 0.1);
    }
}
