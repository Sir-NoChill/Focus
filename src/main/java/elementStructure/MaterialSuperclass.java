package elementStructure;

import com.exceptions.LeafAddChildException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import elementStructure.iterator.NullIterator;
import elementStructure.material.Reading;

import java.util.Iterator;

//Material Factory Interface
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Reading.class, name = "reading")
})
public abstract class MaterialSuperclass extends ElementSuperclass implements Element {

    public MaterialSuperclass() {

    }

    //HEEEEK I need to test this
    @Override
    public void complete() {
        //getExpCounterInstance().add(this.expValue);
        this.previousProgress = progress;
        this.progress = 100;
        this.complete = true;
    }

    @Override
    public Iterator<Element> createIterator() {
        return new NullIterator();
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @JsonIgnore
    public boolean isLeaf() {
        return true;
    }

    @Override
    public void addChildren(Element element) throws LeafAddChildException {
        throw new LeafAddChildException();
    }

    //this too must be tested
    @Override
    public void unComplete() {
        if (previousProgress == 100.00) {
            this.complete = true;
        } else {
            this.progress = previousProgress;
            this.complete = false;
        }
    }

    @JsonIgnore
    @Override
    public int getRemainingExp() {
        return this.expValue;
    }

    @Override
    public int countChildren() {
        return 0;
    }



}
