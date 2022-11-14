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

    private String stringData;

    public LabeledData(String label, int data) {
        this.label = label;
        this.data = data;
        this.stringData = null;
    }

    public LabeledData(String label, String stringData) {
        this.label = label;
        data = 0;
        this.stringData = stringData;
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

    public String getStringData() {
        return stringData;
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
    }
}
