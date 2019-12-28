package troubleshootsearch.visitable;

import java.util.ArrayList;
import troubleshootsearch.util.FileProcessor;
import troubleshootsearch.util.MyLogger;
import troubleshootsearch.visitable.Node;
import troubleshootsearch.visitable.Visitable;
import troubleshootsearch.visitor.VisitorI;
import troubleshootsearch.util.MyLogger;
import troubleshootsearch.visitable.Visitable;




public class MyTree implements Visitable{

    private Node root;
    private FileProcessor fp;
    private Node retVal;
    /**
    Constructor
    */
    public MyTree(FileProcessor fpIn){
        MyLogger.writeMessage("MyTree constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
        this.root = null;
        this.retVal = null;
        this.fp = fpIn;
        fp.readFileSize();

    }

    /**
    This method returns the file processor
    @return fileprocessor fp
    */
    public FileProcessor getFileProcessor(){
        MyLogger.writeMessage("getFileProcessor() method from MyTree called", MyLogger.DebugLevel.METHOD);
        return fp;
    }

    /**
    This method sets the current file processor
    @param fileProcessor
    */
    public void setFileProcessor(FileProcessor fpIn){
        MyLogger.writeMessage("setFileProcessor() method from MyTree called", MyLogger.DebugLevel.METHOD);
        this.fp = fpIn;
        fp.readFileSize();
    }


    /**
    This method gets the root
    @return root node
    */
    public Node getRoot(){
        MyLogger.writeMessage("getRoot() method from MyTree called", MyLogger.DebugLevel.METHOD);
        return root;
    }


    /**
    This method calls an internal insertNode method
    CITATION
    @param word
    @param line number
    */
    public void insert(String wordIn, int lineNumIn){
        MyLogger.writeMessage("insert(String,int) method from MyTree called", MyLogger.DebugLevel.METHOD);
        root = insertNode(root, wordIn, lineNumIn);
    }


    /**
    this method insert nodes to the tree
    CITATION
    @param node
    @param word
    @param line number
    @return node
    */
    private Node insertNode(Node nIn, String wordIn, int lineNumberIn){
        if(nIn == null){
            nIn = new Node(wordIn);
            nIn.addToLineNumber(lineNumberIn);
        }else if(wordIn.compareTo(nIn.getWord()) < 0){
            nIn.left = insertNode(nIn.left, wordIn, lineNumberIn);
        }else if(wordIn.compareTo(nIn.getWord()) > 0){
            nIn.right = insertNode(nIn.right, wordIn, lineNumberIn);
        }else if(wordIn.equals(nIn.getWord())){
            nIn.addToLineNumber(lineNumberIn);
        }
        return nIn;
    }

    /**
    This method finds the node for the word given
    @param word
    @return found node
    */
    public Node find(String words){
        MyLogger.writeMessage("find(String) from MyTree called", MyLogger.DebugLevel.METHOD);
        retVal = new Node(words);
        findNode(root, words);
        return retVal;
    }

    /**
    this method traverses the tree inorder and finds the node for the given word
    @param node
    @param word
    */
    private void findNode(Node rIn, String wIn){
        MyLogger.writeMessage("print() method from MyTree called", MyLogger.DebugLevel.METHOD);
        if(rIn == null){
            return;
        }

        findNode(rIn.left, wIn);
        if(rIn.getWord().toLowerCase().indexOf(wIn) != -1){
            if(rIn.getWord().length() > wIn.length()){
                for(Integer i : rIn.getLineNumbersFoundIn()){
                    retVal.addToLineNumber(i);
                }
            }
        }
        findNode(rIn.right, wIn);
    }

    /**
    This method prints the tree inOrder
    */
    public void print(){
        MyLogger.writeMessage("print() method from MyTree called", MyLogger.DebugLevel.METHOD);
        printInOrder(root);
    }

    /**
    This method is the algorithm for printing in order
    @param node to start from (root)
    */
    private void printInOrder(Node r){
        MyLogger.writeMessage("printInOrder() method from MyTree called", MyLogger.DebugLevel.METHOD);
        if(r == null){
            return;
        }
        printInOrder(r.left);
        System.out.println(r.getWord());
        for(Integer i : r.getLineNumbersFoundIn()){
            System.out.println(i);
        }
        printInOrder(r.right);

    }

    /**
    Accepts visitor
    @param visitor
    */
    public void accept(VisitorI visitorIn){
        MyLogger.writeMessage("accept(VisitorI) method from MyTree called", MyLogger.DebugLevel.ACCEPT);
        visitorIn.visit(this);
    }



}
