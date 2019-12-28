package genericcheckpointing.util;

import java.util.Objects;
import genericcheckpointing.util.MyAllTypesFirst;

public class MyAllTypesSecond extends MyAllTypesFirst{


    private double myDoubleT;
    private float myFloatT;
    private short myShortT;
    private char myCharT;
    private double myOtherDoubleT;

    /**
    Empty constructor with default values
    */
    public MyAllTypesSecond(){
        MyLogger.writeMessage("MyAllTypesSecond empty constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
        this.myDoubleT = -99999;
        this.myOtherDoubleT = -99999;
        this.myFloatT = -99999;
        this.myShortT = -999;
        this.myCharT = '\u0000';
    }


    /**
    Setter for myDoubleT
    @param double
    */
    public void setMyDoubleT(double dIn){
        this.myDoubleT = dIn;
    }

    /**
    Getter for myDoubleT
    @return double
    */
    public double getMyDoubleT(){
        return myDoubleT;
    }

    /**
    Setter for myOtherDoubleT
    @param double
    */
    public void setMyOtherDoubleT(double dIn){
        this.myOtherDoubleT = dIn;
    }

    /**
    Getter for myOtherDoubleT
    @return double
    */
    public double getMyOtherDoubleT(){
        return myOtherDoubleT;
    }

    /**
    Setter for myFloatT
    @param float
    */
    public void setMyFloatT(float fIn){
        this.myFloatT = fIn;
    }

    /**
    Getter for myFloatT
    @return float
    */
    public float getMyFloatT(){
        return myFloatT;
    }

    /**
    setter for myShortT
    @param short
    */
    public void setMyShortT(short sIn){
        this.myShortT = sIn;
    }

    /**
    getter for myShortT
    @param short
    */
    public short getMyShortT(){
        return myShortT;
    }

    /**
    setter for myCharT
    @param char
    */
    public void setMyCharT(char cIn){
        this.myCharT = cIn;
    }

    /**
    getter for myCharT
    @return char
    */
    public char getMyCharT(){
        return myCharT;
    }

    /**
    This method overrides the deafult equals method to compare the two Objects
    @param objIn Object to compare
    @return true or false
    */
    @Override
    public boolean equals(Object objIn){
        MyLogger.writeMessage("MyAllTypesSecond equals method called", MyLogger.DebugLevel.METHOD);

        if(objIn == this){
            return true;
        }

        if(myDoubleT == ((MyAllTypesSecond) objIn).getMyDoubleT() &&
            myOtherDoubleT == ((MyAllTypesSecond) objIn).getMyOtherDoubleT() &&
            myFloatT == ((MyAllTypesSecond) objIn).getMyFloatT() &&
            myShortT == ((MyAllTypesSecond) objIn).getMyShortT() &&
            myCharT == ((MyAllTypesSecond) objIn).getMyCharT()){
                return true;
            }else{
                return false;
            }
    }


    /**
    This method overrides the default hashcode method
    @return hashcode int
    */
    @Override
    public int hashCode(){
        MyLogger.writeMessage("MyAllTypesSecond HashCode method called", MyLogger.DebugLevel.METHOD);
        return Objects.hash(myDoubleT, myOtherDoubleT, myFloatT, myShortT,
        myCharT);
    }

    /**
    This method overrides the deafult toString method
    @return new String
    */
    public String toString(){
        MyLogger.writeMessage("MyAllTypesSecond toString method called", MyLogger.DebugLevel.METHOD);
        StringBuilder retVal = new StringBuilder("***MyAllTypesSecond**\n");

        retVal.append("myDoubleT:   " + myDoubleT + "\n");
        retVal.append("myOtherDoubleT:   " + myOtherDoubleT + "\n");
        retVal.append("myFloatT:   " + myFloatT + "\n");
        retVal.append("myShortT:   " + myShortT + "\n");
        retVal.append("myCharT:   " + myCharT + "\n");


        return retVal.toString();
    }







}
