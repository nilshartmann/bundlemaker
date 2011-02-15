package org.bundlemaker.core.batch.tk;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

/**
 * <p>
 * Implements an eclipse application that performs a batch parse of a given tk
 * easy system.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BatchParserApplication implements IApplication {

	// private static final String PROP_TEMPLATE_DIRECTORY =
	// "template.directory";

	//
	private static final String CONFIGURATION_SHORTOPTION = "c";
	private static final String CONFIGURATION_OPTION = "configuration";

	//
	private static final String PARSE_SHORTOPTION = "p";
	private static final String PARSE_OPTION = "parse";

	/** - */
	private static final String[][] COMMANDLINE_OPTIONS = new String[][] {
			{ CONFIGURATION_SHORTOPTION, CONFIGURATION_OPTION,
					Boolean.TRUE.toString() },
			{ PARSE_SHORTOPTION, PARSE_OPTION, Boolean.FALSE.toString() } };

	/** - */
	private CommandLine _commandLine;

	/**
	 * <p>
	 * Parses the bundle maker project.
	 * </p>
	 * 
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {

		// initialize command line and configuration
		initializeCommandLine(context);
		assertOption(CONFIGURATION_OPTION);

		//
		BundleMakerTKeasyBatch batch = new BundleMakerTKeasyBatch(
				_commandLine.getOptionValue(CONFIGURATION_OPTION),
				_commandLine.hasOption(PARSE_OPTION));

		//
		batch.execute(true);

		if (!_commandLine.hasOption(PARSE_OPTION)) {

			// export
			batch.export();
		}
		// return OK
		return IApplication.EXIT_OK;
	}

	/**
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		// nothing to do here...
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws ParseException
	 */
	private void initializeCommandLine(IApplicationContext context)
			throws ParseException {

		//
		String[] arguments = (String[]) context.getArguments().get(
				IApplicationContext.APPLICATION_ARGS);

		//
		CommandLineParser commandLineParser = new PosixParser();

		//
		Options options = new Options();

		// add all options
		for (String[] option : COMMANDLINE_OPTIONS) {
			options.addOption(option[0], option[1],
					Boolean.parseBoolean(option[2]), "");
		}

		// parse the command line
		_commandLine = commandLineParser.parse(options, arguments);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param attributeName
	 */
	private void assertOption(String attributeName) {

		//
		if (!_commandLine.hasOption(attributeName)) {
			throw new RuntimeException("Attribute '" + attributeName
					+ "' has to be set!");
		}
	}
}
