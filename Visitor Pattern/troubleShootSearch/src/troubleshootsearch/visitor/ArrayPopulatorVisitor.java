package troubleshootsearch.visitor;

import troubleshootsearch.visitable.MyArrayList;
import troubleshootsearch.visitable.MyTree;
import troubleshootsearch.visitor.VisitorI;
import troubleshootsearch.util.MyLogger;
import troubleshootsearch.util.FileProcessor;

public class ArrayPopulatorVisitor implements VisitorI{

    /**
    Default Constructor
    */
    public ArrayPopulatorVisitor(){
        MyLogger.writeMessage("ArrayPopulatorVisitor constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
    }



    /**
    This method visits the corresponding Element and populates it
    @param MyArrayList object
    */
    public void visit(MyArrayList listIn){
        MyLogger.writeMessage("visit(MyArrayList) from ArrayPopulatorVisitor called", MyLogger.DebugLevel.VISIT);

        int fileSize = listIn.getFileProcessor().getNumLines();

        if(fileSize == 0){
            MyLogger.writeMessage("visit(MyArrayList) technicalInfo file is empty <ArrayPopulatorVisitor>", MyLogger.DebugLevel.ERROR);
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < fileSize; i++){
            String sentence = listIn.getFileProcessor().readLineSpecific(i);
            if(sentence != null) listIn.addToList(sentence);
        }

        if(listIn.getListSize() == 0){
            MyLogger.writeMessage("visit(MyArrayList) technicalInfo file is empty", MyLogger.DebugLevel.ERROR);
        }



    }

    /**
    Visits tree. Empty because this visitor doesnt use MyTree element
    @param tree
    */
    public void visit(MyTree treeIn){
        MyLogger.writeMessage("visit(MyTree) from ArrayPopulatorVisitor called", MyLogger.DebugLevel.VISIT);
    }

}
