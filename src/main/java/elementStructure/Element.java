package elementStructure;

import com.exceptions.LeafAddChildException;

import java.util.Iterator;

public interface Element {

    Element[] listMaterials();

    void complete();

    Iterator<Element> createIterator();

    String getTitle();

    String getDescription();

    boolean isLeaf();

    void addChildren(Element element) throws LeafAddChildException;

    void setTitle(String text);
    double getProgress();
    double getTime();
    boolean isComplete();
    void setProgress(double progress);
}
