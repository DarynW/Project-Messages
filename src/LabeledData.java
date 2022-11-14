/**
 * Used by the statistics class to handle
 * Labeled data
 *
 * @author Andy Niu, lab sec 13
 * @version 11/13/2022
 */
public class LabeledData {
    private String label;
    private int data;

    public LabeledData(String label, int data) {
        this.label = label;
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
