package courseplanner.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import courseplanner.debug.Debug;

public class Results implements FileDisplayInterface, StdoutDisplayInterface{

    private String result;

    /**
    Constructor
    */
    public Results(String outputIn){
        this.result = outputIn;
    }

    /**
    This method writes out output to standard output
    */
    public void writeToDisplay(){
        System.out.println(result);
    }

    public void writeToFile(String fileNameIn){
        String fileName = fileNameIn;
        Debug.print("ATTEMPTING TO WRITE TO FILE!");

        FileWriter file = null;
		BufferedWriter writer = null;

		try{
			file = new FileWriter(fileName);
			writer = new BufferedWriter(file);
		}catch(IOException e){
            Debug.error("!!ERROR!! Couldn't create file writer");
			e.printStackTrace();
		}

        try{
            writer.write(result);
        }catch(IOException e){
            e.printStackTrace();
            Debug.error("!!ERROR!! Couldnt write to file!");
        }finally{
            if(writer!= null){
                try{
                    writer.flush();
                    writer.close();

                }catch(IOException e){
                    Debug.error("!!ERROR!! Couldn't close buffered writer!");
                    e.printStackTrace();
                }
            }
        }


        Debug.print("SUCCESSFULLY WROTE TO FILE: " + fileName);
    }


}
