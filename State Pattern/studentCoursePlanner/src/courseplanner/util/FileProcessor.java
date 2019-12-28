package courseplanner.util;

import courseplanner.debug.Debug;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileProcessor{

    private FileReader file;
    private BufferedReader buffReader;
    private String fileName;


    public FileProcessor(String fileIn){
        this.fileName = fileIn;
        createReader(fileName);
    }


    /*
    This method tries to create a file reader with the filename input.
    Throws an error if file was not found
    @param File name
    */

    public final void createReader(String fileIn){
        try{
            Debug.print("Creating file readers for " + fileIn);
            file = new FileReader(fileIn);
            buffReader = new BufferedReader(file);
        }catch(FileNotFoundException e){
            Debug.error("!!ERROR!!The file: " + this.fileName + " was not found!");
            e.printStackTrace();
            System.exit(1);
        }
    }


    /*
    This method will read the file line by line, remove special symbols
    and return an arraylist of the file contents
    @return ArrayList<String>
    */
    public String readFile(){
        Debug.print("Reading files and creating array for " + this.fileName);
        String retVal = null;
        String line = "";

        try {

            while((line = buffReader.readLine()) != null){
                retVal = new String(line);
            }

            if(retVal.length() == 0 || retVal == null){
                throw new IOException("File was empty! Give valid input");
            }
        } catch(IOException e) {
            Debug.error("Couldn't read file: " + this.fileName);
            e.printStackTrace();
            System.exit(1);
        }finally{
            if(file != null){
                try{
                    file.close();
                }catch (IOException e){
                    Debug.error("Couldn't close FileReader");
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
        return retVal;
    }


    /* *
    toString overrider
    */
    @Override
	public String toString(){
		return fileName;
	}
}
