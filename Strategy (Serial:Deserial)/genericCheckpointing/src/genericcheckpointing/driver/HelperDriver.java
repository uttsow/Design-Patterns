package genericcheckpointing.driver;

import genericcheckpointing.util.FileProcessor;
import genericcheckpointing.util.MyLogger;
import genericcheckpointing.util.ProxyCreator;
import genericcheckpointing.util.MyAllTypesFirst;
import genericcheckpointing.util.MyAllTypesSecond;
import genericcheckpointing.util.MySpecialTypes;
import genericcheckpointing.util.SerializableObject;
import genericcheckpointing.xmlStoreRestore.StoreRestoreHandler;
import genericcheckpointing.server.StoreRestoreI;
import genericcheckpointing.server.StoreI;
import genericcheckpointing.server.RestoreI;
import java.util.ArrayList;


public class HelperDriver{

    /**
    This method creates the dynamic proxy given a handler and ProxyCreator ref
    @param handler
    @param pcIn ProxyCreator reference
    @return proxy
    */
    public StoreRestoreI createProxy(StoreRestoreHandler handlerIn, ProxyCreator pcIn){
        MyLogger.writeMessage("{HelperDriver} createProxy called. Dyanmic proxy will be returned", MyLogger.DebugLevel.METHOD);

        StoreRestoreI cpointRef = (StoreRestoreI) pcIn.createProxy(
                                        new Class[]{
                                            StoreI.class, RestoreI.class
                                        },
                                        handlerIn
        );

        return cpointRef;
    }


    /**
    This method performs deserialization
    @param handler
    @param cpointRef
    */
    public ArrayList<SerializableObject> deserialize(StoreRestoreHandler handlerIn, StoreRestoreI cpointRefIn){
        MyLogger.writeMessage("{HelperDriver} deserialize called", MyLogger.DebugLevel.METHOD);

        //Deseralize
        handlerIn.openFileForReading();
        ArrayList<SerializableObject> objects = new ArrayList<>();
        SerializableObject myRecordRet;
        while(!handlerIn.endFile()){
            myRecordRet = ((RestoreI) cpointRefIn).readObj("XML");
            objects.add(myRecordRet);
        }

        // System.out.println(objects.size());

        handlerIn.closeFileForReading();

        return objects;
    }

    /**
    This method performs serialization
    @param handler
    @param cpointRef
    @param objIn arraylist
    */
    public void serialize(StoreRestoreHandler handlerIn, StoreRestoreI cpointRefIn, ArrayList<SerializableObject> objIn){

        MyLogger.writeMessage("{HelperDriver} serialize called", MyLogger.DebugLevel.METHOD);

        handlerIn.openFileForWriting();

        for(int i=0; i<objIn.size(); i++){
            SerializableObject myRecordRet = objIn.get(i);
            if(myRecordRet instanceof MyAllTypesFirst){
                ((StoreI) cpointRefIn).writeObj((MyAllTypesFirst)myRecordRet, "XML");
            }else if(myRecordRet instanceof MyAllTypesSecond){
                ((StoreI) cpointRefIn).writeObj((MyAllTypesSecond)myRecordRet, "XML");
            }else if(myRecordRet instanceof MySpecialTypes){
                ((StoreI) cpointRefIn).writeObj((MySpecialTypes)myRecordRet, "XML");
            }
        }

        handlerIn.closeFileForWriting();

    }

    /**
    This methods prints all the deserilized objects to STDOUT
    @param ObjIn arraylist
    */
    public void print(ArrayList<SerializableObject> objIn){
        if(MyLogger.getLevel() == MyLogger.DebugLevel.PRINT){

            for(int i =0; i < objIn.size();i++){
                System.out.println(objIn.get(i).toString());
            }

        }
    }

}
