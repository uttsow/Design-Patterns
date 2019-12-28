// FIXME: replace XYZ with the package name for the assignment
package troubleshootsearch.util;

public class MyLogger{

    // FIXME: Add more enum values as needed for the assignment
    public static enum DebugLevel {
        ERROR,
        FILE_PROCESSOR,
        CONSTRUCTOR,
        METHOD,
        ACCEPT,
        VISIT,
        NONE
    };

    private static DebugLevel debugLevel;



    // FIXME: Add switch cases for all the levels
    public static void setDebugValue (int levelIn) {
	switch (levelIn) {
    case 0: debugLevel = DebugLevel.ERROR; break;
    case 1: debugLevel = DebugLevel.FILE_PROCESSOR; break;
	case 2: debugLevel = DebugLevel.CONSTRUCTOR; break;
    case 3: debugLevel = DebugLevel.METHOD; break;
    case 4: debugLevel = DebugLevel.ACCEPT; break;
    case 5: debugLevel = DebugLevel.VISIT; break;
	default: debugLevel = DebugLevel.NONE; break;
	}
    }

    public static void setDebugValue (DebugLevel levelIn) {
	debugLevel = levelIn;
    }

    public static void writeMessage (String     message  ,
                                     DebugLevel levelIn ) {
	if (levelIn == debugLevel)
	    System.out.println(message);
    }

    public String toString() {
	return "The debug level has been set to the following " + debugLevel;
    }
}
