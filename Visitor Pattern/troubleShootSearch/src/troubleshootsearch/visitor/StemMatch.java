package troubleshootsearch.visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import troubleshootsearch.util.FileProcessor;
import troubleshootsearch.util.MyLogger;
import troubleshootsearch.visitable.MyArrayList;
import troubleshootsearch.visitable.MyTree;
import troubleshootsearch.visitable.Node;
import troubleshootsearch.visitor.VisitorI;



public class StemMatch implements VisitorI{

    private LinkedHashMap<String,ArrayList<Node>> result;
    private ArrayList<Node> nodeList;
    /**
    Constructor
    */
    public StemMatch(){
        MyLogger.writeMessage("StemMatch constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
        this.nodeList = new ArrayList<>();
        this.result = new LinkedHashMap<>();
    }


    /**
    This method returns the hashmap containing the results
    @return result hashmap
    */
    public LinkedHashMap<String, ArrayList<Node>> getStemMatchResult(){
        MyLogger.writeMessage("getExactMatchResult() from ExactMatch called", MyLogger.DebugLevel.METHOD);
        return result;
    }


    /**
    This method clears the string;
    CITATION in readme
    @param String to clear
    @return cleared string
    */
    private String clearString(String sIn){
        MyLogger.writeMessage("clearString(String) from StemMatch  called", MyLogger.DebugLevel.METHOD);
        if(sIn == null || sIn.equals("")){
            return null;
        }
        return sIn.replaceAll("[^A-Za-z0-9-]"," ");
    }

    /**
    This method visits the tree element
    @param tree
    */
    public void visit(MyTree treeIn){
        MyLogger.writeMessage("visit(MyArrayList) from ArrayPopulatorVisitor called", MyLogger.DebugLevel.VISIT);


        int fileSize = treeIn.getFileProcessor().getNumLines();
        if(fileSize == 0){
            MyLogger.writeMessage("visit(MyArrayList) userInput file is empty <StemMatch>", MyLogger.DebugLevel.ERROR);
            throw new IllegalArgumentException();
        }

        String noMatch = "No naive stemming match";

        for(int i =0; i <fileSize; i++){
            String key = treeIn.getFileProcessor().readLineSpecific(i);
            if(key == null) continue;
            nodeList = new ArrayList<>();
            Node found = null;
            if(key!=null){
                key = key.toLowerCase();
                String[] splitSentence = key.split(" ");
                String firstWord = splitSentence[0];
                found = treeIn.find(firstWord);
                if(found.getWordCount() != 0){
                    // System.out.println(firstWord);
                    //Use my logger to print matches
                    MyLogger.writeMessage(("<StemMatch> " + "Key: " + key + " :: Word Count: " + found.toString()), MyLogger.DebugLevel.FILE_PROCESSOR);
                    nodeList.add(found);
                    result.put(key, nodeList);
                }else{
                    ArrayList<Node> none = new ArrayList<>();
                    none.add(new Node(noMatch));
                    result.put(key, none);

                }

            }

        }


            // ArrayList<Node> t = result.get("reinstall software");
            // System.out.println(t);

    }



    /**
    Visits tree. Empty because this visitor doesnt use MyArrayList element
    @param list
    */
    public void visit(MyArrayList listIn){
        MyLogger.writeMessage("visit(MyArrayList) from StemMatch called", MyLogger.DebugLevel.VISIT);
    }

}
