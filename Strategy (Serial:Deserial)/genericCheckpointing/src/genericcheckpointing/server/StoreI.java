package genericcheckpointing.server;

import genericcheckpointing.util.SerializableObject;
import genericcheckpointing.util.MyAllTypesFirst;
import genericcheckpointing.util.MyAllTypesSecond;
import genericcheckpointing.util.MySpecialTypes;

public interface StoreI extends StoreRestoreI {
    void writeObj(MyAllTypesFirst aRecordIn, String wireFormatIn);
    void writeObj(MySpecialTypes sRecordIn, String wireFormatIn);
}
