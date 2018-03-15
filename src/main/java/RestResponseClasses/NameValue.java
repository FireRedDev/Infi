package RestResponseClasses;

/**
 * Contains an identifier and a value; For Rest;
 *
 * @author Christopher G
 */
public class NameValue {

    private String name;
    private double value;

    /**
     *
     * @param name
     * @param value
     */
    public NameValue(String name, double value) {
        this.name = name;
        this.value = value;
    }

    /**
     *
     */
    public NameValue() {
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public double getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(double value) {
        this.value = value;
    }

}
