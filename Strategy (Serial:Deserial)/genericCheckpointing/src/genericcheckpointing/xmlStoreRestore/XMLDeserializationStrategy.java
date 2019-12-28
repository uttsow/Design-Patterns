package genericcheckpointing.xmlStoreRestore;

import genericcheckpointing.util.FileProcessor;
import genericcheckpointing.util.MyLogger;
import genericcheckpointing.util.SerializableObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;




public class XMLDeserializationStrategy implements StrategyI{

    /**
    Default constructor
    */
    public XMLDeserializationStrategy(){
        MyLogger.writeMessage("{XMLDeserializationStrategy} empty constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
    }


    /**
    This method processes the file and creates the SerializableObject
    @param SerializableObject null
    @param fpIn file processor to read the info
    @return SerializableObject
    */
    @Override
    public SerializableObject processInput(SerializableObject objIn, FileProcessor fpIn){
        MyLogger.writeMessage("{XMLDeserializationStrategy} processInput called", MyLogger.DebugLevel.METHOD);

        DeserializeTypes dTypes = new DeserializeTypes();
        //Using reflection to create class
        Class<?> cls = null;

        //This is the ret val
        SerializableObject obj = null;

        fpIn.read();

        //This wil contain the class name
        String line = fpIn.read();
        // System.out.println(line);


        //this clears the string. CITATION
        String result = clearString(line);
        String[] splitSentence = result.split("\\s+");
        // System.out.println(result);
        // System.out.println(Arrays.toString(splitSentence));

        String className = splitSentence[splitSentence.length-1];
        // System.out.println(className);

        //taken from powerpoint. Exception list found online
        try{
            cls = Class.forName(className);
            obj = (SerializableObject) cls.newInstance();
        }catch(ClassNotFoundException e){
            MyLogger.writeMessage("{XMLDeserializationStrategy} " + className +"was not foudn", MyLogger.DebugLevel.ERROR);
            e.printStackTrace();
            System.exit(1);
        }catch(InstantiationException e){
            MyLogger.writeMessage("{XMLDeserializationStrategy} " + className +"was could not be instantiated", MyLogger.DebugLevel.ERROR);
            e.printStackTrace();
            System.exit(1);
        }catch(IllegalAccessException e){
            MyLogger.writeMessage("{XMLDeserializationStrategy} " + className + "could not be accessed", MyLogger.DebugLevel.ERROR);
            e.printStackTrace();
            System.exit(1);
        }




        // Iterate through file and create the fields of the class. Creates one object at a time
        while(!(line =fpIn.read()).equals("<DPSerialization>")){
            //last complex type tag
            if(line.equals(" </complexType>")){
                continue;
            }
            if(line.equals("</DPSerialization>")){
                break;
            }

            //Clear string
            String cleared = clearString(line);
            String[] arr = cleared.split("\\s+");
            // System.out.println(Arrays.toString(arr));

            //Get method name
            String methodName = "set" + captalizeLetter(arr[1]);
            // System.out.println(methodName);

            //Get type
            String type = arr[5].toLowerCase();

            //Get value
            Object value = null;

            //Convert the types
            switch(type){
                case "int":
                    value = dTypes.deserInt(arr);
                    break;
                case "long":
                    value = dTypes.deserLong(arr);
                    break;
                case "string":
                    value = dTypes.deserString(arr);
                    break;
                case "boolean":
                    value = dTypes.deserBool(arr);
                    break;
                case "double":
                    value = dTypes.deserDouble(arr);
                    break;
                case "float":
                    value = dTypes.deserFloat(arr);
                    break;
                case "short":
                    value = dTypes.deserShort(arr);
                    break;
                case "char":
                    value = dTypes.deserChar(arr);
                    break;
                default:
                    MyLogger.writeMessage("{XMLDeserializationStrategy} could not be parse value", MyLogger.DebugLevel.ERROR);
                    break;
            }
            // System.out.println(value.toString());
            // System.out.println(methodName);
            Method[] m = cls.getDeclaredMethods();

            Method caller = null;

            try{
                for(int i =0; i < m.length; i++){
                    if(m[i].getName().equals(methodName)){
                        caller = m[i];
                        break;
                    }
                }

                caller.invoke(obj, value);

            }catch(IllegalArgumentException e){
                MyLogger.writeMessage("{XMLDeserializationStrategy} " + methodName + " didnt have correct argument", MyLogger.DebugLevel.ERROR);
                e.printStackTrace();
                System.exit(1);
            }catch(InvocationTargetException e){
                MyLogger.writeMessage("{XMLDeserializationStrategy} " + methodName + " couldnt be Invoked", MyLogger.DebugLevel.ERROR);
                e.printStackTrace();
                System.exit(1);
            }catch(IllegalAccessException e){
                MyLogger.writeMessage("{XMLDeserializationStrategy} " + methodName + " couldnt be accessed", MyLogger.DebugLevel.ERROR);
                e.printStackTrace();
                System.exit(1);
            }

        }

        return obj;
    }

    /**
    This method clears the special characters in the lines from the file
    @param clearIn string to clear
    @return cleared string
    */
    private String clearString(String clearIn){
        String line = clearIn.replaceAll("[^-._,A-Za-z0-9]"," ");
        return line;
    }

    /**
    This method captalizes the first letter of the XML method name.
    I messed up in when naming my methods so i did this work around
    @param lIn String to uppercase
    @param
    */
    private String captalizeLetter(String lIn){
        String cap = lIn.substring(0,1).toUpperCase() + lIn.substring(1);
        return cap;
    }
}
