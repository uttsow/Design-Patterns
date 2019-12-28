package troubleshootsearch.visitable;

import java.util.ArrayList;

import troubleshootsearch.util.FileProcessor;
import troubleshootsearch.util.MyLogger;
import troubleshootsearch.visitor.VisitorI;
import troubleshootsearch.visitable.Visitable;

public class MyArrayList implements Visitable{

    private ArrayList<String> sentence;
    private FileProcessor fp;


    /**
    Constructor
    */
    public MyArrayList(FileProcessor fpIn){
        MyLogger.writeMessage("MyArrayList constructor called", MyLogger.DebugLevel.CONSTRUCTOR);

        this.sentence = new ArrayList<>();
        this.fp = fpIn;
        fp.readFileSize();
    }

    /**
    This method returns the arraylist size;
    @return size (int);
    */
    public int getListSize(){
        MyLogger.writeMessage("getListSize() method from MyArrayList called", MyLogger.DebugLevel.METHOD);
        return sentence.size();
    }

    /**
    Method for adding to arraylist
    @param String to add
    */
    public void addToList(String sentenceIn){
        MyLogger.writeMessage("addToList() method from MyArrayList called", MyLogger.DebugLevel.METHOD);
        sentence.add(sentenceIn);
        // System.out.println(sentenceIn);
    }

    /**
    This method returns the arraylist containing all sentences
    @return list of sentences 
    */
    public ArrayList<String> getSentencesList(){
        return sentence;
    }

    /**
    This method returns the file processor
    @return fileprocessor fp
    */
    public FileProcessor getFileProcessor(){
        MyLogger.writeMessage("getFileProcessor() method from MyArrayList called", MyLogger.DebugLevel.METHOD);
        return fp;
    }

    /**
    This method sets the current file processor
    @param fileProcessor
    */
    public void setFileProcessor(FileProcessor fpIn){
        MyLogger.writeMessage("setFileProcessor() method from MyArrayList called", MyLogger.DebugLevel.METHOD);
        this.fp = fpIn;
        fp.readFileSize();
    }

    /**
    Method for accepting the Vistor and calling visit
    @param Visitor
    */
    public void accept(VisitorI visitorIn){
        MyLogger.writeMessage("accept(VisitorI) method from MyArrayList called", MyLogger.DebugLevel.ACCEPT);

        visitorIn.visit(this);

    }

    /**
    This method prints all the sentences stored in this object
    */
    public void print(){
        MyLogger.writeMessage("print() method from MyArrayList called", MyLogger.DebugLevel.METHOD);
        for(int i =0; i < sentence.size(); i++){
            System.out.println(sentence.get(i));
        }
    }


    @Override
    public String toString(){
        return "";
    }
}
