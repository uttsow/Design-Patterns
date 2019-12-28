package courseplanner.driver;

import courseplanner.util.Results;
import courseplanner.util.FileDisplayInterface;
import courseplanner.util.StdoutDisplayInterface;
import courseplanner.util.FileProcessor;
import courseplanner.util.ErrorChecking;
import courseplanner.context.CoursePlannerContext;
import courseplanner.debug.Debug;

import java.util.ArrayList;

/**
 * @author Uttsow
 *
 */
public class Driver {
	public static void main(String[] args) {

		/*
		 * As the build.xml specifies the arguments as argX, in case the
		 * argument value is not given java takes the default value specified in
		 * build.xml. To avoid that, below condition is used
		 */
		if (args.length != 2 || args[0].equals("${arg0}") || args[1].equals("${arg1}")){
			System.err.println("Error: Incorrect number of arguments. Program accepts 2 arguments.");
			System.exit(1);
		}


		//Step 1. Reading the file to an array
		FileProcessor studentInfo = new FileProcessor(args[0]);
		String student = studentInfo.readFile();

		// Step 2. Create an instance of the Context Class and call a method in it to determine the student's outcome, along with the input file handle.
		CoursePlannerContext context = new CoursePlannerContext(student);
		context.checkInput();
		context.initalize();
		context.determineOutcome();

		// Step 3: Get the output and write it to file
		FileDisplayInterface display = new Results(context.getOutput());
		display.writeToFile(args[1]);
		// ((StdoutDisplayInterface)display).writeToDisplay();



	}
}
