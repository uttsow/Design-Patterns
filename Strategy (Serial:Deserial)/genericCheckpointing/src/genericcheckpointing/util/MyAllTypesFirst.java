package genericcheckpointing.util;

import genericcheckpointing.util.SerializableObject;
import java.util.Objects;

import java.util.Objects;


public class MyAllTypesFirst extends SerializableObject{

    private int myInt;
    private long myLong;
    private long myOtherLong;
    private String myString;
    private boolean myBool;
    private int myOtherInt;


    /**
    Empty constructor with default values
    */
    public MyAllTypesFirst(){
        MyLogger.writeMessage("MyAllTypesFirst empty constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
        this.myInt = -99999;
        this.myOtherInt = -99999;
        this.myLong = -99999;
        this.myOtherLong = -99999;
        this.myString = "";
        this.myBool = false;
    }


    /**
    Setter
    @param int
    */
    public void setMyInt(int iIn){
        this.myInt = iIn;
        // System.out.println(myInt);
    }

    /**
    Getter for int
    @return int
    */
    public int getMyInt(){
        return myInt;
    }

    /**
    Setter
    @param int
    */
    public void setMyOtherInt(int iIn){
        this.myOtherInt = iIn;
        // System.out.println(myOtherInt);
    }

    /**
    Getter for int
    @return int
    */
    public int getMyOtherInt(){
        return myOtherInt;
    }

    /**
    Setter
    @param long
    */
    public void setMyLong(long lIn){
        this.myLong = lIn;
        // System.out.println(myLong);
    }

    /**
    Getter for long
    @return long
    */
    public long getMyLong(){
        return myLong;
    }

    /**
    Setter
    @param long
    */
    public void setMyOtherLong(long lIn){
        this.myOtherLong = lIn;
    }

    /**
    Getter for long
    @return long
    */
    public long getMyOtherLong(){
        return myOtherLong;
    }


    /**
    Setter
    @param String
    */
    public void setMyString(String sIn){
        this.myString = sIn;
        // System.out.println(myString);
    }

    /**
    Getter
    @return string
    */
    public String getMyString(){
        return myString;
    }


    /**
    Setter
    @param bool
    */
    public void setMyBool(boolean bIn){
        this.myBool = bIn;
        // System.out.println(myBool);
    }

    /**
    Getter
    @return bool
    */
    public boolean getMyBool(){
        return myBool;
    }



    /**
    This method overrides the deafult equals method to compare the two Objects
    @param objIn Object to compare
    @return true or false
    */

    @Override
    public boolean equals(Object objIn){
        MyLogger.writeMessage("MyAllTypesFirst equals method called", MyLogger.DebugLevel.METHOD);

        if(myInt == ((MyAllTypesFirst) objIn).getMyInt() &&
            myOtherInt == ((MyAllTypesFirst) objIn).getMyOtherInt() &&
            myLong == ((MyAllTypesFirst) objIn).getMyLong() &&
            myOtherLong == ((MyAllTypesFirst) objIn).getMyOtherLong() &&
            myString.equals(((MyAllTypesFirst) objIn).getMyString()) &&
            myBool == ((MyAllTypesFirst) objIn).getMyBool()){
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
        MyLogger.writeMessage("MyAllTypesFirst HashCode method called", MyLogger.DebugLevel.METHOD);
        return Objects.hash(myInt, myOtherInt, myLong, myOtherLong,
        myString, myBool);
    }

    /**
    This method overrides the deafult toString method
    @return new String
    */
    public String toString(){
        MyLogger.writeMessage("MyAllTypesFirst toString method called", MyLogger.DebugLevel.METHOD);
        StringBuilder retVal = new StringBuilder("***MyAllTypesFirst**\n");

        retVal.append("myInt:   " + myInt + "\n");
        retVal.append("myOtherInt:   " + myOtherInt + "\n");
        retVal.append("myLong:   " + myLong + "\n");
        retVal.append("myOtherLong:   " + myOtherLong + "\n");
        retVal.append("myString:   " + myString + "\n");
        retVal.append("myBool:    " + String.valueOf(myBool) + "\n");

        return retVal.toString();
    }


}
