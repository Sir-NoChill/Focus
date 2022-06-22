package elementStructure.material;

import elementStructure.Element;
import elementStructure.MaterialSuperclass;

public class Reading extends MaterialSuperclass {
    private int length;
    private LengthType lengthType;

    @Override
    public Element[] listMaterials() {
        return null;
    }

    private enum LengthType {CHAPTER,PAGE,WORD}

    public Reading() {
        super();
        this.length = 1;
        this.lengthType = LengthType.CHAPTER;
        this.title = "empty";
    }

    @Override
    public String toString() {
        if (this.title == null) {
            title = "";
        }
        return "Reading: " + this.title;
    }
}
