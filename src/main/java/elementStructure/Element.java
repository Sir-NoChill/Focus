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
    void unComplete();
    int getRemainingExp();
    int getThisTaskExp();
    void setExp(int exp);
    void setDescription(String description);
    void setTime(double time);
    void setParent(Element parent);
}
