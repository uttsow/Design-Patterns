package genericcheckpointing.xmlStoreRestore;

import genericcheckpointing.util.MyLogger;

public class DeserializeTypes{

    /**
    Constructor
    */
    public DeserializeTypes(){
        MyLogger.writeMessage("DeserializeTypes constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
    }

    /**
    deserialize string value. Creates string from array
    @param string array
    @return string
    */
    public String deserString(String[] arrIn){
        StringBuilder s = new StringBuilder();

        for(int i = 6; i<arrIn.length-1; i++){
            s.append(arrIn[i]);
            s.append(" ");
        }

        return s.toString().trim();
    }


    public int deserInt(String[] arrIn){
        int ret = Integer.parseInt(arrIn[arrIn.length-2]);
        return ret;
    }

    public long deserLong(String[] arrIn){
        long ret = Long.parseLong(arrIn[arrIn.length-2]);
        return ret;
    }

    public boolean deserBool(String[] arrIn){
        boolean ret = new Boolean(arrIn[arrIn.length-2]);
        return ret;
    }

    public double deserDouble(String[] arrIn){
        double ret = Double.parseDouble(arrIn[arrIn.length-2]);
        return ret;
    }

    public float deserFloat(String[] arrIn){
        float ret = Float.parseFloat(arrIn[arrIn.length-2]);
        return ret;
    }

    public short deserShort(String[] arrIn){
        short ret = Short.parseShort(arrIn[arrIn.length-2]);
        return ret;
    }

    public char deserChar(String[] arrIn){
        char ret = new Character(arrIn[arrIn.length-2].charAt(0));
        return ret;
    }



}
