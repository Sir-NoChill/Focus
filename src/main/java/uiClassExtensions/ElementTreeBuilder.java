package uiClassExtensions;

import elementStructure.Element;
import javafx.scene.control.TreeItem;

public class ElementTreeBuilder {

    public static TreeItem<Element> buildTreeElement(Element element) {
        Class elementType = element.getClass();

        switch (elementType.toString()) {
            case "class elementStructure.tasks.Project" :
                return buildProject(element);
            default:
                return null;
        }
    }

    private static TreeItem<Element> buildProject(Element element) {
        return null; // ElementTreeItem.createNode(element);
    }
}
