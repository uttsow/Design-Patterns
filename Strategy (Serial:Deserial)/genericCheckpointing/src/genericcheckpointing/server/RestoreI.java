package genericcheckpointing.server;

import genericcheckpointing.util.SerializableObject;

public interface RestoreI extends StoreRestoreI {
    SerializableObject readObj(String input);
}
