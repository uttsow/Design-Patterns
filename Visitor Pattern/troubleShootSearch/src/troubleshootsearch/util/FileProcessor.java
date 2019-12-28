package troubleshootsearch.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import troubleshootsearch.debug.Debug;
import troubleshootsearch.util.MyLogger;



public class FileProcessor{

    private String fileName;
    private int numLines;

    /**
    Constructor
    */
    public FileProcessor(String fileIn){
        MyLogger.writeMessage("FileProcessor constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
        this.fileName = fileIn;
        this.numLines = 0;
    }

    /**
    This method attempts to read the file size (CITATION!)
    */
    public final void readFileSize(){
        MyLogger.writeMessage("readFileSize() from FileProcessor  called", MyLogger.DebugLevel.METHOD);
        try{
            Path path = Paths.get(fileName);
            numLines = (int)Files.lines(path).count();
            // System.out.println(numLines);

        }catch(FileNotFoundException e){
            Debug.print("File was not found!");
            e.printStackTrace();
            System.exit(1);
        }catch(IOException e){
            Debug.print("Read file size didnt work");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
    This method returns the number of lines in the file
    @return number of lines
    */
    public int getNumLines(){
        MyLogger.writeMessage("getNumLines() from FileProcessor  called", MyLogger.DebugLevel.METHOD);
        return numLines;
    }


    /**
    This method reads file line at a specific number. Used for reading file line one by one (CITATION!);
    @param line location
    @return String of the file
    */
    public String readLineSpecific(int lineNumIn){
        MyLogger.writeMessage("readLineSpecific() from FileProcessor  called", MyLogger.DebugLevel.METHOD);

        String retVal = "";
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            retVal = lines.skip(lineNumIn).findFirst().get();
            if(!retVal.equals("")){
                return retVal;

            }
        }catch(IOException e){
            Debug.error("Something went wrong reading the file content");
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }


    /* *
    toString overrider
    */
    @Override
	public String toString(){
		return fileName;
	}
}
