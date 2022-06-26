package elementStructure;

import com.exceptions.LeafAddChildException;
import elementStructure.iterator.NullIterator;

import java.util.Iterator;

//Material Factory Interface
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

    public boolean isLeaf() {
        return false;
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

    @Override
    public int getRemainingExp() {
        return this.expValue;
    }

}
