package genericcheckpointing.xmlStoreRestore;

import genericcheckpointing.util.FileProcessor;
import genericcheckpointing.util.SerializableObject;
import genericcheckpointing.util.MyLogger;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;



public class StoreRestoreHandler implements InvocationHandler{

    private FileProcessor fpRead;
    private FileProcessor fpWrite;
    private String writeFileName;
    private StrategyI des;
    private StrategyI ser;


    /**
    Constructor
    */
    public StoreRestoreHandler(String checkpointFileIn, String checkpointFileCheckIn){
        MyLogger.writeMessage("StoreRestoreHandler constructor called", MyLogger.DebugLevel.CONSTRUCTOR);

        this.writeFileName = checkpointFileCheckIn;
        this.fpRead = new FileProcessor(checkpointFileIn);
        this.fpWrite = new FileProcessor(writeFileName);
        des = new XMLDeserializationStrategy();
        ser = new XMLSerializationStrategy();
    }

    /**
    Overriden invoke method
    @param Object dynamic Proxy
    @param m Method that will be invoked
    @param args the arguments for the method invoked
    @return Object
    */
    @Override
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable{
        String methodCalled = m.getName();

        //Applying the stragey
        //deserialize
        if(methodCalled.equals("readObj")){
            if(((String) args[0]).equals("XML")){
                MyLogger.writeMessage("{INVOKE} ReadObj called", MyLogger.DebugLevel.INVOKE);

                SerializableObject ret = null;
                ret = des.processInput(ret, fpRead);
                return ret;
            }
        }else if(methodCalled.equals("writeObj")){
            if(((String) args[1]).equals("XML")){
                MyLogger.writeMessage("{INVOKE} WriteObj called", MyLogger.DebugLevel.INVOKE);

                SerializableObject obj = (SerializableObject) args[0];
                ser.processInput(obj, fpWrite);
            }
        }

        return null;
    }


    /**
    This method opens the file for reading
    */
    public void openFileForReading(){
        fpRead.open();
    }


    /**
    This method closes the file that was used for reading
    */
    public void closeFileForReading(){
        fpRead.close();
    }

    public void openFileForWriting(){
        fpWrite.openWriter();
    }

    public void closeFileForWriting(){
        fpWrite.closeWriter();
    }

    public boolean endFile(){
        return fpRead.EOF();
    }





}
