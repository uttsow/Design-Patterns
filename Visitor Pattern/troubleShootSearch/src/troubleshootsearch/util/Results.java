package troubleshootsearch.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import troubleshootsearch.debug.Debug;
import troubleshootsearch.util.MyLogger;
import troubleshootsearch.visitable.Node;
import troubleshootsearch.util.FileProcessor;



public class Results implements FileDisplayInterface, StdoutDisplayInterface{

    private ArrayList<String> result;
    private LinkedHashMap<String, ArrayList<String>> exact;
    private LinkedHashMap<String, ArrayList<Node>> naive;
    private LinkedHashMap<String, ArrayList<String>> stem;
    private FileProcessor fp;
    /**
    Constructor
    */
    public Results(FileProcessor fpIn, LinkedHashMap<String, ArrayList<String>> exactIn, LinkedHashMap<String,ArrayList<Node>> naiveIn, LinkedHashMap<String, ArrayList<String>> stemIn){
        this.result = new ArrayList<>();
        this.exact = exactIn;
        this.naive = naiveIn;
        this.stem = stemIn;
        this.fp = fpIn;
        fp.readFileSize();
    }

    /**
    This method puts all the information from the maps into a result array for easy printing
    */
    public void putToArray(){
        int exactSize = exact.size();
        if(naive.size() != stem.size() && stem.size() != exactSize){
            MyLogger.writeMessage("putToArray() from Resilts called. All data structure size dont match", MyLogger.DebugLevel.ERROR);
            throw new IllegalArgumentException();
        }
        int counter = 0;
        int size = fp.getNumLines();

        String word;
        String eMatch = "Exact Match";
        String nMatch = "Naive Stemming Match";
        String sMatch = "Semantic Match";
        String seperator = "--------------------";
        for(int i =0; i <size; i++){

            if(fp.readLineSpecific(i) == null){
                continue;
            }else{
                word = fp.readLineSpecific(i).toLowerCase();
                String in = "user input - " + word;
                result.add(in);

                result.add(eMatch);
                result.add(seperator);
                counter += 3;
                // System.out.println(word);
                if(exact.get(word) == null){
                    // System.out.println("made");
                    continue;
                }
                for(int j =0; j <exact.get(word).size(); j++){
                    String eMatchRes = exact.get(word).get(j);
                    if(!eMatchRes.equals("No exact match")){
                        result.add(j+1+ ". "+ eMatchRes);
                    }else{
                        result.add(eMatchRes);
                    }
                    if(result.get(counter-1).equals(eMatchRes)){
                        continue;
                    }else{
                        result.add("\n");
                    }
                    counter++;
                }
                // result.add("\n");

                result.add(nMatch);
                result.add(seperator);
                if(naive.get(word) == null) continue;
                for(int j =0; j < naive.get(word).size(); j++){
                    String nMatchRes = naive.get(word).get(j).toString();
                    int wordSize = naive.get(word).get(j).getWordCount();
                    if(!nMatchRes.equals("No naive stemming match")){
                        result.add("Word Count = " + wordSize);
                        result.add("Line Numbers = " + nMatchRes);
                    }else{
                        result.add(nMatchRes);
                    }
                    if(result.get(counter-1).equals(nMatchRes)){
                        continue;
                    }else{
                        result.add("\n");
                    }
                    counter++;
                }
                // result.add("\n");

                result.add(sMatch);
                result.add(seperator);
                if(stem.get(word) == null) continue;
                for(int j =0; j < stem.get(word).size(); j++){
                    String sMatchRes = stem.get(word).get(j);
                    if(!sMatchRes.equals("No semantic match")){
                        result.add(j+1+ ". "+ sMatchRes);
                    }else{
                        result.add(sMatchRes);
                    }
                    if(result.get(counter-1).equals(sMatchRes)){
                        continue;
                    }else{
                        result.add("\n");
                    }
                    counter++;
                }
                // result.add("\n");
            }
        }

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
