package troubleshootsearch.debug;

public final class Debug{
    /**
    This method prints all successfull messages
    */
    public static void print(String messageIn){
        System.out.println("[DEBUG]" + messageIn);
    }

    /**
    This method prints errors and exits the program
    */
    public static void error(String messageIn){
        System.err.println("[ERROR]" + messageIn);
    }
}
