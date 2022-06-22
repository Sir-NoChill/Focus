package uiClassExtensions;

import elementStructure.Element;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

public class ElementTreeItem extends TreeItem<String> {

    private boolean isLeaf;
    private boolean isFirstTimeChildren;
    private boolean isFirstTimeLeaf;

    private Element concreteValue;

    /* CONSTRUCTORS */

    public ElementTreeItem() {
        super();
        this.concreteValue = null;
    }

    public ElementTreeItem(Element element) {
        super(element.getTitle());
        this.concreteValue = element;
    }

    public ElementTreeItem(Element element, Node graphic) {
        super(element.getTitle(),graphic);
        this.concreteValue = element;
    }




}
