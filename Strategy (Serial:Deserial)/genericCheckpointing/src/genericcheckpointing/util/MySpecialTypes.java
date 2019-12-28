package genericcheckpointing.util;

import java.util.Objects;
import genericcheckpointing.util.SerializableObject;

public class MySpecialTypes extends SerializableObject{


    private int myInt1;
    private int myInt2;
    private String myString1;
    private String myString2;
    private double myDoubleT1;
    private double myDoubleT2;


    /**
    Empty constructor with default values
    */
    public MySpecialTypes(){
        MyLogger.writeMessage("MySpecialTypes empty constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
        this.myInt1 = -99999;
        this.myInt2 = -99999;
        this.myString1 = "";
        this.myString2 = "";
        this.myDoubleT1 = -99999;
        this.myDoubleT2 = -99999;
    }

    /**
    Setter for myInt1
    @param int
    */
    public void setMyInt1(int iIn){
        this.myInt1 = iIn;
    }

    /**
    Getter for myInt1
    @return int
    */
    public int getMyInt1(){
        return myInt1;
    }

    /**
    Setter for myInt2
    @param int
    */
    public void setMyInt2(int iIn){
        this.myInt2 = iIn;
    }

    /**
    Getter for myInt2
    @return int
    */
    public int getMyInt2(){
        return myInt2;
    }

    /**
    Setter for myString1
    @param string
    */
    public void setMyString1(String sIn){
        this.myString1 = sIn;
    }

    /**
    getter for myString1
    @return string
    */
    public String getMyString1(){
        return myString1;
    }

    /**
    setter for myString2
    @param string
    */
    public void setMyString2(String sIn){
        this.myString2 = sIn;
    }

    /**
    Getter for myString2
    @return string
    */
    public String getMyString2(){
        return myString2;
    }

    /**
    Setter for myDoubleT1
    @param double
    */
    public void setMyDoubleT1(double dIn){
        this.myDoubleT1 = dIn;
    }

    /**
    Getter for myDoubleT1
    @return double
    */
    public double getMyDoubleT1(){
        return myDoubleT1;
    }

    /**
    Setter for myDoubleT2
    @param double
    */
    public void setMyDoubleT2(double dIn){
        this.myDoubleT2 = dIn;
    }

    /**
    Getter for myDoubleT2
    @return double
    */
    public double getMyDoubleT2(){
        return myDoubleT2;
    }


    /**
    This method overrides the deafult equals method to compare the two Objects
    @param objIn Object to compare
    @return true or false
    */

    @Override
    public boolean equals(Object objIn){
        MyLogger.writeMessage("MySpecialTypes equals method called", MyLogger.DebugLevel.METHOD);

        if(objIn == this){
            return true;
        }

        if(myInt1 == ((MySpecialTypes) objIn).getMyInt1() &&
            myInt2 == ((MySpecialTypes) objIn).getMyInt2() &&
            myString1.equals(((MySpecialTypes) objIn).getMyString1()) &&
            myString2.equals(((MySpecialTypes) objIn).getMyString2()) &&
            myDoubleT1 == ((MySpecialTypes) objIn).getMyDoubleT1() &&
            myDoubleT2 == ((MySpecialTypes) objIn).getMyDoubleT2()){
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
        MyLogger.writeMessage("MySpecialTypes HashCode method called", MyLogger.DebugLevel.METHOD);
        return Objects.hash(myInt1, myInt2, myString1, myString2,
        myDoubleT1, myDoubleT2);
    }

    /**
    This method overrides the deafult toString method
    @return new String
    */
    public String toString(){
        MyLogger.writeMessage("MySpecialTypes toString method called", MyLogger.DebugLevel.METHOD);
        StringBuilder retVal = new StringBuilder("***MySpecialTypes**\n");

        retVal.append("myInt1:   " + myInt1 + "\n");
        retVal.append("myInt2:   " + myInt2 + "\n");
        retVal.append("myString1:   " + myString1 + "\n");
        retVal.append("myString2:   " + myString2 + "\n");
        retVal.append("myDoubleT1:   " + myDoubleT1 + "\n");
        retVal.append("myDoubleT2" + myDoubleT2 + "\n");

        return retVal.toString();
    }
}
