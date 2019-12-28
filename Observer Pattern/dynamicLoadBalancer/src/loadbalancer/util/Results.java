package loadbalancer.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import loadbalancer.debug.Debug;

public class Results implements FileDisplayInterface, StdoutDisplayInterface{

    private ArrayList<String> result;

    /**
    Constructor
    */
    public Results(ArrayList<String> outputIn){
        this.result = outputIn;
    }

    /**
    This method writes out output to standard output
    */
    public void writeToDisplay(){
        for(int i = 0; i < result.size(); i++){
            System.out.println(result.get(i));
        }
    }


    /**
    This method writes to a text file
    @param fileNameIn name of the file given from the arguements
    */
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
            for(int i =0; i < result.size(); i ++){
                writer.write(result.get(i));
                writer.write("\n");
            }
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
