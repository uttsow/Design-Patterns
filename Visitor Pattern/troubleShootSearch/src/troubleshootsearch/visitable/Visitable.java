package troubleshootsearch.visitable;

import troubleshootsearch.visitor.VisitorI;

public interface Visitable{
    void accept(VisitorI vistorIn);
}
