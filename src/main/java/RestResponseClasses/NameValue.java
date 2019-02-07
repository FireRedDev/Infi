package RestResponseClasses;

/**
 * Has the Property Name and Value, basically a NameValuePair, used for Rest
 *
 * 
 */
public class NameValue {

    private String name;
    private double value;

    /**
     * Constructor
     * @param name
     * @param value
     */
    public NameValue(String name, double value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Default Constructor
     */
    public NameValue() {
    }

    /**
     * Getter
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Setter
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter
     * @return
     */
    public double getValue() {
        return value;
    }

    /**
     * Setter
     * @param value
     */
    public void setValue(double value) {
        this.value = value;
    }

}
