package de.ophosting.gcodeprogess.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.MissingArgumentException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Jan Ophey
 */
public class CommandLineParameterHandlerTest {

    private static final String PARAM_INPUT = "--" + CliOptions.INPUT;
    private static final String FILENAME_INPUT = "in.gcode";
    private static final String PARAM_OUTPUT = "--" + CliOptions.OUTPUT;
    private static final String OUTPUT_FILENAME = "out.gcode";
    private CommandLineParameterHandler sut;

    @Before
    public void setUp() {
        sut = new CommandLineParameterHandler();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseCommandLineWithNoParametersThrows() throws Exception {
        final String commandLine = "";
        sut.parseCommandLine(commandLine.split(" "));
    }

    @Test(expected = MissingArgumentException.class)
    public void testParseCommandLineWithMissingInputValueAndNoOutputParametersThrows() throws Exception {
        final String commandLine = PARAM_INPUT;
        sut.parseCommandLine(commandLine.split(" "));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testParseCommandLineWithNoOutputParametersThrows() throws Exception {
        final String commandLine = PARAM_INPUT + " " + FILENAME_INPUT;
        sut.parseCommandLine(commandLine.split(" "));
    }

    @Test(expected = MissingArgumentException.class)
    public void testParseCommandLineWithValidInputValueAndMissingOutputValueThrows() throws Exception {
        final String commandLine = PARAM_INPUT + " " + FILENAME_INPUT + " " + PARAM_OUTPUT;
        sut.parseCommandLine(commandLine.split(" "));
    }


    @Test
    public void testParseCommandLineWithValidParameters() throws Exception {
        final String commandLine = PARAM_INPUT + " " + FILENAME_INPUT + " " + PARAM_OUTPUT + " " + OUTPUT_FILENAME;
        final CommandLine params = sut.parseCommandLine(commandLine.split(" "));

        assertTrue("Two parameters must be parsed", params.getOptions().length == 2);
        assertTrue("Parameter '" + PARAM_INPUT + "' is '" + FILENAME_INPUT + "'", FILENAME_INPUT.equals(params.getOptionValue(CliOptions.INPUT)));
        assertTrue("Parameter '" + PARAM_OUTPUT + "' is '" + OUTPUT_FILENAME + "'", OUTPUT_FILENAME.equals(params.getOptionValue(CliOptions.OUTPUT)));
    }

}
