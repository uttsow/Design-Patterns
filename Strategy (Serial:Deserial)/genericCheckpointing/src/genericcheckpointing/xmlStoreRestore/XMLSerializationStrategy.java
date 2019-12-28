package genericcheckpointing.xmlStoreRestore;

import genericcheckpointing.util.FileProcessor;
import genericcheckpointing.util.MyLogger;
import genericcheckpointing.util.SerializableObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import genericcheckpointing.util.MyLogger;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


public class XMLSerializationStrategy implements StrategyI{

    private SerializeTypes sTypes;
    /**
    Default constructor
    */
    public XMLSerializationStrategy(){
        MyLogger.writeMessage("{XMLSerializationStrategy} empty constructor called", MyLogger.DebugLevel.CONSTRUCTOR);

        this.sTypes = new SerializeTypes();

    }

    public SerializableObject processInput(SerializableObject objIn, FileProcessor fpIn){
        //Write header tag
        fpIn.write(sTypes.getHeader());

        Class<?> cls = objIn.getClass();
        String className = cls.getName();
        String classNameWrite = sTypes.getClassNameFormat(className);

        //Write class name format
        fpIn.write(classNameWrite);

        //Following powerpoint and online tutorials
        try{
            Field fieldList[] = cls.getDeclaredFields();
            for(int i =0; i <fieldList.length;i++){
                Field fld = fieldList[i];

                //Field type
                String type = fld.getType().toString().toLowerCase();

                //Get field name
                String fieldName = fld.getName();

                //Create string for getter
                String getter = "get" + captalizeLetter(fieldName);
                // System.out.println(getter);


                //Get the method now
                Method m = cls.getMethod(getter);

                //get the value
                String value = m.invoke(objIn).toString();
                // System.out.println(value);

                String parse = "";

                switch(type){

                    case "int":
                        //Uninitalized
                        if(Integer.parseInt(value) == -99999) break;

                        parse = sTypes.serializeFormatted(fieldName, type, value);
                        fpIn.write(parse);
                        break;
                    case "long":
                        if(Long.parseLong(value) == -99999) break;

                        parse = sTypes.serializeFormatted(fieldName, type, value);
                        fpIn.write(parse);
                        break;
                    case "class java.lang.string":
                        if(value.equals("")) break;

                        parse = sTypes.serializeFormatted(fieldName, "string", value);
                        fpIn.write(parse);
                        break;
                    case "boolean":
                        parse = sTypes.serializeFormatted(fieldName, type, value);
                        fpIn.write(parse);
                        break;
                    case "double":
                        if(Double.parseDouble(value) == -99999.0) break;

                        parse = sTypes.serializeFormatted(fieldName, type, value);
                        fpIn.write(parse);
                        break;
                    case "float":
                        if(Float.parseFloat(value) == -99999.0) break;

                        parse = sTypes.serializeFormatted(fieldName, type, value);
                        fpIn.write(parse);
                        break;
                    case "short":
                        if(Short.parseShort(value) == -999) break;

                        parse = sTypes.serializeFormatted(fieldName, type, value);
                        fpIn.write(parse);
                        break;
                    case "char":
                        if(value.charAt(0) == '\u0000') break;

                        parse = sTypes.serializeFormatted(fieldName, type, value);
                        fpIn.write(parse);
                        break;
                    default:
                        MyLogger.writeMessage("{XMLSerializationStrategy} could not be parse value", MyLogger.DebugLevel.ERROR);
                        break;
                }

            }

            //Write the footers
            String cFooter = sTypes.getComplexFooter();
            String hFooter = sTypes.getHeaderFooter();

            fpIn.write(cFooter);
            fpIn.write(hFooter);

        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }



        return null;
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
