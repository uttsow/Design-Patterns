package genericcheckpointing.xmlStoreRestore;

import genericcheckpointing.util.MyLogger;

public class SerializeTypes{

    /**
    Constructor
    */
    public SerializeTypes(){
        MyLogger.writeMessage("SerializeTypes constructor called", MyLogger.DebugLevel.CONSTRUCTOR);
    }

    public String serializeFormatted(String fNameIn, String fTypeIn, String fValueIn){
        String r = "  <"+ fNameIn + " xsi:type=\"" + "xsd:" + fTypeIn +"\">" + fValueIn + "</" + fNameIn + ">";
        return r;
    }


    public String getHeader(){
        String r = "<DPSerialization>";
        return r;
    }


    public String getClassNameFormat(String classIn){
        String r = " <complexType xsi:type=\"" +classIn + "\">";
        return r;
    }

    public String getComplexFooter(){
        String r = " </complexType>";
        return r;
    }

    public String getHeaderFooter(){
        String r = "</DPSerialization>";
        return r;
    }



}
