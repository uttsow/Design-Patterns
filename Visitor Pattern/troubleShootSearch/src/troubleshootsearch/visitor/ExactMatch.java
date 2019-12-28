package troubleshootsearch.visitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import troubleshootsearch.util.FileProcessor;
import troubleshootsearch.util.MyLogger;
import troubleshootsearch.visitable.MyArrayList;
import troubleshootsearch.visitor.VisitorI;
import troubleshootsearch.visitable.MyTree;



public class ExactMatch implements VisitorI{

    //In order to keep the correct order of inputs
    private LinkedHashMap<String, ArrayList<String>> result;
    private ArrayList<String> stringList;

    /**
    Constructor
    */
    public ExactMatch(){
        MyLogger.writeMessage("ExactMatch constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
        this.stringList = new ArrayList<>();
        this.result = new LinkedHashMap<>();

    }

    /**
    This method returns the hashmap containing the results
    @return result hashmap
    */
    public LinkedHashMap<String, ArrayList<String>> getExactMatchResult(){
        MyLogger.writeMessage("getExactMatchResult() from ExactMatch called", MyLogger.DebugLevel.METHOD);
        return result;
    }

    /**
    This method visits the element MyArrayList
    @param list element
    */
    public void visit(MyArrayList listIn){
        MyLogger.writeMessage("visit(MyArrayList) from ExactMatch called", MyLogger.DebugLevel.VISIT);

        int fileSize = listIn.getFileProcessor().getNumLines();

        if(fileSize == 0){
            MyLogger.writeMessage("visit(MyArrayList) userInput file is empty <ExactMatch>", MyLogger.DebugLevel.ERROR);
            throw new IllegalArgumentException();
        }

        ArrayList<String> allSentences = listIn.getSentencesList();
        String noMatch = "No exact match";

        //Main algorithm
        for(int i =0; i < fileSize; i++){
            //Ignores caps and sets it to lowercase
            String key = listIn.getFileProcessor().readLineSpecific(i);
            stringList = new ArrayList<>();
            if(key !=null){
                key = key.toLowerCase();
                try{
                    for(int j =0; j < allSentences.size(); j++){
                        String value = allSentences.get(j).toLowerCase();
                        if(value.contains(key)){
                            stringList.add(value);
                            result.put(key, stringList);

                            //Use my logger to print matches
                            MyLogger.writeMessage(("<ExactMatch> " + "Key: " + key + " :: Value: " + value), MyLogger.DebugLevel.FILE_PROCESSOR);

                        }else{
                            if(result.containsKey(key)){
                                continue;
                            }else{
                                ArrayList<String> no = new ArrayList<>();
                                no.add(noMatch);
                                result.put(key, no);
                            }

                        }
                    }
                }catch(NullPointerException e){
                    MyLogger.writeMessage("visit(MyArrayList) NullPointerExcepton when trying to do matching <ExactMatch>", MyLogger.DebugLevel.ERROR);
                    e.printStackTrace();
                }
            }
        }


        // result.entrySet().forEach(entry->{
        //     System.out.println(entry.getKey() + " :: " + entry.getValue());
        // });

    }

    /**
    Visits tree. Empty because this visitor doesnt use MyTree element
    @param tree
    */
    public void visit(MyTree treeIn){
        MyLogger.writeMessage("visit(MyTree) from ExactMatch called", MyLogger.DebugLevel.VISIT);
    }


}
