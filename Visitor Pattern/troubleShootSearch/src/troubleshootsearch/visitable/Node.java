package troubleshootsearch.visitable;


import java.util.Set;
import java.util.HashSet;
import troubleshootsearch.util.FileProcessor;
import troubleshootsearch.util.MyLogger;
import troubleshootsearch.visitable.Visitable;
import troubleshootsearch.visitor.VisitorI;

public class Node{

    private String word;

    //Will prevent duplicate line numbers if the word appear on the same line more than once
    private Set<Integer> lineNumbersFoundIn;

    //for nodes;
    Node left;
    Node right;
    /**
    Constructor
    */
    public Node(String wordIn){
        MyLogger.writeMessage("Node constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
        this.word = wordIn;
        this.lineNumbersFoundIn = new HashSet<>();

        this.left = null;
        this.right = null;
    }

    /**
    This a line number to the list
    @param line number
    */
    public void addToLineNumber(int lineNumIn){
        MyLogger.writeMessage("addToLineNumber(int) from Node  called", MyLogger.DebugLevel.METHOD);
        lineNumbersFoundIn.add(lineNumIn);
    }

    public String toString(){
        StringBuilder ret = new StringBuilder();
        for(Integer i : lineNumbersFoundIn){
            ret.append(i);
            ret.append(", ");
        }
        if(ret.length()!= 0){
            ret.setLength(ret.length() -2);
            return ret.toString();

        }
        return word;
    }

    /**
    This method returns the word the node contains
    @return word
    */
    public String getWord(){
        MyLogger.writeMessage("getWord() from Node called", MyLogger.DebugLevel.METHOD);
        return word;
    }

    /**
    This returns the number of times the word appears
    @return word count
    */
    public int getWordCount(){
        MyLogger.writeMessage("getWordCount() from Node  called", MyLogger.DebugLevel.METHOD);
        return lineNumbersFoundIn.size();
    }

    /**
    This method returns the hashset of line numbers the word was found in
    @return list of linenumbers
    */
    public Set<Integer> getLineNumbersFoundIn(){
        MyLogger.writeMessage("getLineNumbersFoundIn() from Node called", MyLogger.DebugLevel.METHOD);
        return lineNumbersFoundIn;
    }

    /**
    Sets the right node
    @param right node
    */
    public void setRNode(Node rNodeIn){
        MyLogger.writeMessage("setRNode(Node) from Node  called", MyLogger.DebugLevel.METHOD);
        right = rNodeIn;
    }

    /**
    Sets left node
    @param left node
    */
    public void setLNode(Node lNodeIn){
        MyLogger.writeMessage("setLNode(Node) from Node  called", MyLogger.DebugLevel.METHOD);
        left = lNodeIn;
    }

    /**
    Gets right node
    @return right node
    */
    public Node getRNode(){
        MyLogger.writeMessage("getRNode() from Node  called", MyLogger.DebugLevel.METHOD);
        return right;
    }

    /**
    Gets left node
    @return left node
    */
    public Node getLNode(){
        MyLogger.writeMessage("getLNode() from Node  called", MyLogger.DebugLevel.METHOD);
        return left;
    }
}
