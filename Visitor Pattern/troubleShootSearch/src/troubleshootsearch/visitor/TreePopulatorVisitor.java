package troubleshootsearch.visitor;

import troubleshootsearch.visitable.MyArrayList;
import troubleshootsearch.visitable.MyTree;
import troubleshootsearch.visitable.Node;
import troubleshootsearch.visitor.VisitorI;
import troubleshootsearch.util.MyLogger;
import troubleshootsearch.util.FileProcessor;



public class TreePopulatorVisitor implements VisitorI{

    /**
    Constructor
    */
    public TreePopulatorVisitor(){
        MyLogger.writeMessage("TreePopulatorVisitor constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
    }


    /**
    This method clears the string;
    CITATION in readme
    @param String to clear
    @return cleared string
    */
    private String clearString(String sIn){
        MyLogger.writeMessage("clearString(String) from TreePopulatorVisitor  called", MyLogger.DebugLevel.METHOD);
        if(sIn == null || sIn.equals("")){
            return null;
        }
        return sIn.replaceAll("[^A-Za-z0-9-]"," ");
    }


    /**
    This method visits the tree
    @param tree
    */
    public void visit(MyTree treeIn){
        MyLogger.writeMessage("visit(MyTree) from TreePopulatorVisitor called", MyLogger.DebugLevel.VISIT);

        int fileSize = treeIn.getFileProcessor().getNumLines();

        if(fileSize == 0){
            MyLogger.writeMessage("visit(MyTree) technicalInfo file is empty <TreePopulatorVisitor>", MyLogger.DebugLevel.ERROR);
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < fileSize; i++){
            String line = treeIn.getFileProcessor().readLineSpecific(i);
            line = clearString(line);

            String[] splitSentence = line.split(" ");
            for(int j =0; j < splitSentence.length; j++){

                String word = splitSentence[j];
                if(word.equals("")) continue;
                treeIn.insert(word, i+1);
            }

        }

        // treeIn.print();
        // System.out.println(treeIn.find("reinstall").getWordCount());

    }

    /**
    Visits tree. Empty because this visitor doesnt use MyArrayList element
    @param list
    */
    public void visit(MyArrayList listIn){

    }
}
