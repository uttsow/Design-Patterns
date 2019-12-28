package genericcheckpointing.driver;

import genericcheckpointing.util.FileProcessor;
import genericcheckpointing.util.MyLogger;
import genericcheckpointing.util.ProxyCreator;
import genericcheckpointing.util.MyAllTypesFirst;
import genericcheckpointing.util.MyAllTypesSecond;
import genericcheckpointing.util.MySpecialTypes;
import genericcheckpointing.util.SerializableObject;
import genericcheckpointing.xmlStoreRestore.StoreRestoreHandler;
import genericcheckpointing.server.StoreRestoreI;
import genericcheckpointing.server.StoreI;
import genericcheckpointing.server.RestoreI;
import java.util.ArrayList;

public class Driver{

    public static void main(String[] args) {

		/*
		 * As the build.xml specifies the arguments as argX, in case the
		 * argument value is not given java takes the default value specified in
		 * build.xml. To avoid that, below condition is used
		 */
		if (args.length != 4 || args[0].equals("${arg0}") || args[1].equals("${arg1}") || args[2].equals("${arg2}") || args[3].equals("${arg3}")){
			System.err.println("Error: Incorrect number of arguments. Program accepts 5 arguments.");
			System.exit(1);
		}

        //Checking the mode
        if(!args[0].equals("deserser")){
            MyLogger.writeMessage("Command Line Argument Mode is incorrect", MyLogger.DebugLevel.ERROR);
            throw new IllegalArgumentException("Mode is not deserser");

        }

        //Helper Driver ref
        HelperDriver helper = new HelperDriver();

        //Store the input/output file name
        String checkpointFile = args[1];
        String checkpointFileCheck = args[2];

        //Set the debug level
        int level = Integer.parseInt(args[3]);
        MyLogger.setDebugValue(level);

        //ProxyCreator ref
        ProxyCreator pc = new ProxyCreator();

        //create handler
        StoreRestoreHandler handler = new StoreRestoreHandler(checkpointFile, checkpointFileCheck);

        //Dynamic proxy
        StoreRestoreI cpointRef = helper.createProxy(handler, pc);

        //Deseralize
        ArrayList<SerializableObject> objects = helper.deserialize(handler, cpointRef);


        //Seralize
        helper.serialize(handler, cpointRef, objects);

        //Redo deseralize
        ArrayList<SerializableObject> retVal = helper.deserialize(handler, cpointRef);

        //To print all deseralized objects
        helper.print(objects);
	}
}
