package loadbalancer.driver;

import java.util.ArrayList;
import loadbalancer.debug.Debug;
import loadbalancer.subject.Cluster;
import loadbalancer.subject.SubjectI;
import loadbalancer.util.FileDisplayInterface;
import loadbalancer.util.FileProcessor;
import loadbalancer.util.Results;
import loadbalancer.util.StdoutDisplayInterface;



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


		//Start file processor and read file size
		FileProcessor fp = new FileProcessor(args[0]);
		fp.readFileSize();


		//Send file processor to cluster to do line by line reading and executing
		SubjectI cluster = new Cluster(fp);
		cluster.checkErrors();
	 	cluster.start();

		//Writing to file 
		FileDisplayInterface d = new Results(cluster.getResults());
		d.writeToFile(args[1]);

		//To see stdout dispaly
		((StdoutDisplayInterface)d).writeToDisplay();

	}
}
