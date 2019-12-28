package troubleshootsearch.visitor;

import troubleshootsearch.visitable.MyArrayList;
import troubleshootsearch.visitable.MyTree;


public interface VisitorI{

    void visit(MyArrayList listIn);
    void visit(MyTree treeIn);
}
