package elementStructure;

import com.exceptions.LeafAddChildException;
import elementStructure.iterator.ElementIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

//Component Factory Interface
public abstract class TaskSuperclass extends ElementSuperclass implements Element {
   protected Collection<Element> children;

   protected TaskSuperclass() {
       super();
       this.children = new ArrayList<>();
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

    /* Lists the child elements of the Task in array form
   *  Primarily for use in GUI creation with the ElementTreeItem class
   *
   * @return the children of this as an array
    */
   @Override
   public Element[] listMaterials() {
       int n = children.size();
       Element[] elements = new Element[n];

       int i = 0;
       while (iterator.hasNext()) {
           Element element = iterator.next();
           elements[i] = element;
           i++;
       }
       return elements;
   }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public void addChildren(Element element) throws LeafAddChildException {
       this.children.add(element);
    }

    public Collection<Element> getChildren() {
       return this.children;
    }
}
