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
import troubleshootsearch.visitor.VisitorI;


public class SemanticMatch implements VisitorI{

    private LinkedHashMap<String, ArrayList<String>> result;
    private HashMap<String, String> synonyms;
    private FileProcessor fp;
    private ArrayList<String> stringList;

    /**
    Constructor
    */
    public SemanticMatch(FileProcessor fpIn){
        MyLogger.writeMessage("SemanticMatch constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
        this.stringList = new ArrayList<>();
        this.fp = fpIn;
        this.result = new LinkedHashMap<>();
        this.synonyms = new HashMap<>();
        populateSynonyms();
    }

    /**
    Populates the synonyms hashmap
    */
    private final void populateSynonyms(){
        MyLogger.writeMessage("populateSynonyms from Semantic Match called", MyLogger.DebugLevel.METHOD);

        //algorithm
        fp.readFileSize();
        int size = fp.getNumLines();
        for(int i =0; i <size; i++){
            String cleared = clearString(fp.readLineSpecific(i));

            if(cleared !=null){
                String[] splitSentence = cleared.split(" ");
                String key = splitSentence[0];
                String value = splitSentence[1];

                //Since its bidirectional. I couldnt find a way to use BiMap from google guava since ant doesnt support dependencies
                synonyms.put(key,value);
                synonyms.put(value,key);
            }
        }

        // synonyms.entrySet().forEach(entry->{
        //     System.out.println(entry.getKey() + " " + entry.getValue());
        // });
    }

    /**
    This method returns the hashmap containing the results
    @return result hashmap
    */
    public LinkedHashMap<String, ArrayList<String>> getSemanticMatchResult(){
        MyLogger.writeMessage("getSemanticMatchResult() from SemanticMatch called", MyLogger.DebugLevel.METHOD);

        return result;
    }


    /**
    This method clears the string;
    CITATION in readme
    @param String to clear
    @return cleared string
    */
    private String clearString(String sIn){
        MyLogger.writeMessage("clearString(String) from Semantic Match called", MyLogger.DebugLevel.METHOD);
        if(sIn == null || sIn.equals("")){
            return null;
        }
        return sIn.replaceAll("[^A-Za-z0-9-]"," ");
    }

    /**
    This method visits the element MyArrayList
    @param list element
    */
    public void visit(MyArrayList listIn){
        MyLogger.writeMessage("visit(MyArrayList) from SemanticMatch called", MyLogger.DebugLevel.VISIT);

        int fileSize = listIn.getFileProcessor().getNumLines();
        if(fileSize == 0){
            MyLogger.writeMessage("visit(MyArrayList) userInput file is empty <SemanticMatch>", MyLogger.DebugLevel.ERROR);
            throw new IllegalArgumentException();
        }

        ArrayList<String> allSentences = listIn.getSentencesList();
        String noMatch = "No semantic match";

        //algorithm
        for(int i =0; i<fileSize; i++){
            String key = listIn.getFileProcessor().readLineSpecific(i);
            if(key!=null){
                key = key.toLowerCase();
                String[] splitSentence = key.split(" ");
                String lastWord = splitSentence[splitSentence.length-1];
                stringList = new ArrayList<>();
                try{
                    for(int j = 0; j < allSentences.size(); j++){
                        if(allSentences.get(j).equals("")) continue;
                        String value = allSentences.get(j).toLowerCase();

                        if(synonyms.containsKey(lastWord)){
                            String syno = synonyms.get(lastWord);
                            String synoKey = synonyms.get(syno);

                            //ExactMatch algorithm
                            if(value.contains(syno) || value.contains(synoKey)){
                                stringList.add(value);
                                result.put(key, stringList);
                                //Use my logger to print matches
                                MyLogger.writeMessage(("<SemanticMatch> " + "Key: " + key + " :: Value: " + value), MyLogger.DebugLevel.FILE_PROCESSOR);
                            }
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
                    MyLogger.writeMessage("visit(MyArrayList) NullPointerExcepton when trying to do matching <SemanticMatch>", MyLogger.DebugLevel.ERROR);
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
        MyLogger.writeMessage("visit(MyTree) from SemanticMatch called", MyLogger.DebugLevel.VISIT);
    }


}
