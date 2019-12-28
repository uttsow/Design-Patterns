package genericcheckpointing.xmlStoreRestore;

import genericcheckpointing.util.SerializableObject;
import genericcheckpointing.util.FileProcessor;
import java.util.Objects;

public interface StrategyI{
    SerializableObject processInput(SerializableObject objIn, FileProcessor fpIn);
}
