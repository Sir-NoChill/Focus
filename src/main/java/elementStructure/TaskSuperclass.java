package elementStructure;

import com.exceptions.LeafAddChildException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import elementStructure.iterator.ElementIterator;
import elementStructure.tasks.Project;
import elementStructure.tasks.SuperList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

//Component Factory Interface
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Project.class, name = "project"),
        @JsonSubTypes.Type(value = SuperList.class, name = "superList")
})
public abstract class TaskSuperclass extends ElementSuperclass implements Element {
   protected Collection<Element> children;
   protected boolean manualProgressCalculation;

   protected TaskSuperclass() {
       super();
       this.children = new ArrayList<>();
       this.manualProgressCalculation = false;
   }

    @Override
    public void complete() {
       //getExpCounterInstance().add(this.expValue);
       for (Element child : this.children) {
           child.complete();
       }
       this.complete = true;
   }

   @Override
    public Iterator<Element> createIterator() {
       if (iterator == null || !iterator.hasNext()) {
           iterator = new ElementIterator(children.iterator());
       }
       return iterator;
   }

   //CUrrently has no use
    /* Lists the child elements of the Task in array form
   *  Primarily for use in GUI creation with the ElementTreeItem class
   *
   * @return the children of this as an array
    */
   @Override
   public Element[] listMaterials() {
       int n = children.size();
       Element[] elements = new Element[n];

       iterator = this.createIterator();

       int i = 0;
       while (iterator.hasNext()) {
           Element element = iterator.next();
           elements[i] = element;
           i++;
       }
       return elements;
   }

   @JsonIgnore
    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public void addChildren(Element element) throws LeafAddChildException {
       this.children.add(element);
    }

    public Collection<Element> getChildren() {
       return this.children;
    }

    @Override
    public boolean isComplete() {
       this.progress = previousProgress;
       boolean subTasksComplete = true;
        for (Element child : this.children) {
            if (child.isComplete()) {
                //pass
            } else {
                subTasksComplete = false;
            }
        }
       if (subTasksComplete) {
           return true;
       } else {
           return false;
        }
    }

    @Override
    public double getProgress() {
       if (!manualProgressCalculation) {
           Iterator<Element> elementIterator = this.createIterator();

           int i = 0;
           double progress = 0;
           while (elementIterator.hasNext()) {
               i++;
               progress += elementIterator.next().getProgress();
           }

           return progress / i;
       } else {
           return this.progress;
       }
    }

    @Override
    public void unComplete() {
       this.complete = false;
       this.progress = previousProgress;
        for (Element child : this.children) {
            child.unComplete();
        }
    }

    @JsonIgnore
    @Override
    public int getRemainingExp() {
       int exp = 0;

       exp += this.expValue;

        for (Element child : this.children) {
            exp += child.getRemainingExp();
        }

        return exp;
    }

    @Override
    public int countChildren() {
        Iterator<Element> elementIterator = this.createIterator();
        int i = 0;
        while (elementIterator.hasNext()) {
            i ++;
            elementIterator.next();
        }

        return i;
    }
}
