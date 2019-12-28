package genericcheckpointing.util;

import genericcheckpointing.debug.Debug;
import genericcheckpointing.util.MyLogger;
import genericcheckpointing.util.MyLogger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.stream.Stream;

import genericcheckpointing.util.MyLogger;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import java.io.FileNotFoundException;
import java.nio.file.Path;



public class FileProcessor{

    private String fileName;
    private int numLines;
    private FileReader reader;
    private BufferedReader buffReader;
    private FileWriter writer;
    private BufferedWriter buffWriter;

    /**
    Constructor
    */
    public FileProcessor(String fileIn){
        MyLogger.writeMessage("FileProcessor constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
        this.fileName = fileIn;
        this.numLines = 0;
    }

    /**
    This method opens the files for reading
    */
    public void open(){
        try{
            reader = new FileReader(fileName);
            buffReader = new BufferedReader(reader);
        }catch(FileNotFoundException e){
            MyLogger.writeMessage("{FileProcessor} File was not found " + fileName, MyLogger.DebugLevel.ERROR);
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void openWriter(){
        try{
            writer = new FileWriter(fileName, false);
            buffWriter = new BufferedWriter(writer);
        }catch(IOException e){
            MyLogger.writeMessage("{FileProcessor} File couldnt be created to write to-- " + fileName, MyLogger.DebugLevel.ERROR);
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
    This method reads the file line by line
    */
    public String read(){
        String line = null;

        try{
            line = buffReader.readLine();
        }catch(IOException e){
            MyLogger.writeMessage("{FileProcessor} could not read line", MyLogger.DebugLevel.ERROR);
            e.printStackTrace();
            System.exit(1);
        }
        return line;
    }

    public void write(String lineIn){
        try{
            buffWriter.write(lineIn +"\n");
        }catch(IOException e){
            MyLogger.writeMessage("{FileProcessor} could not write to file: " + fileName, MyLogger.DebugLevel.ERROR);
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
    close file
    */
    public void close(){
        try{

            buffReader.close();
        }catch(IOException e){
            MyLogger.writeMessage("{FileProcessor} could not close file: " + fileName, MyLogger.DebugLevel.ERROR);
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void closeWriter(){
        try{
            buffWriter.flush();
            buffWriter.close();
        }catch(IOException e){
            MyLogger.writeMessage("{FileProcessor} could not close file: " + fileName, MyLogger.DebugLevel.ERROR);
            e.printStackTrace();
            System.exit(1);
        }
    }

    public boolean EOF(){
        try{
            if(!buffReader.ready()){
                return true;
            }

        }catch(IOException e){
            MyLogger.writeMessage("{FileProcessor} could not determine if end of file: " + fileName, MyLogger.DebugLevel.ERROR);
            e.printStackTrace();
            System.exit(1);
        }
        return false;
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
