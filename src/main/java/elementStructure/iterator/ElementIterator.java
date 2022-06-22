package elementStructure.iterator;

import elementStructure.Element;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

//Inspired by Headfirst Design Patterns 2020 2nd Ed
public class ElementIterator implements Iterator<Element> {
    Stack<Iterator<Element>> stack = new Stack<Iterator<Element>>();

    public ElementIterator(Iterator<Element> iterator) {
        stack.push(iterator);
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        if (stack.empty()) {
            return false;
        } else {
            Iterator<Element> iterator = stack.peek();
            if (!iterator.hasNext()) {
                stack.pop();
                return hasNext();
            } else {
                return true;
            }
        }
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Element next() {
        if (hasNext()) {
            Iterator<Element> iterator = stack.peek();
            Element element = iterator.next();
            stack.push(element.createIterator());
            return element;
        } else {
            return null;
        }
    }
}
