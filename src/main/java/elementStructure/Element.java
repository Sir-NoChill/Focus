package elementStructure;

import com.exceptions.LeafAddChildException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Iterator;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MaterialSuperclass.class, name = "materialSuperclass"),
        @JsonSubTypes.Type(value = TaskSuperclass.class, name = "taskSuperclass")
})
public interface Element {

    Element[] listMaterials();

    void complete();

    Iterator<Element> createIterator();

    String getTitle();

    String getDescription();

    @JsonIgnore
    boolean isLeaf();

    void addChildren(Element element) throws LeafAddChildException;

    void setTitle(String text);
    double getProgress();
    double getTime();
    boolean isComplete();
    void setProgress(double progress);
    void unComplete();

    @JsonIgnore
    int getRemainingExp();
    int getExp();
    void setExp(int exp);
    void setDescription(String description);
    void setTime(double time);
    void setParent(Element parent) throws LeafAddChildException;
    int countChildren();
}
