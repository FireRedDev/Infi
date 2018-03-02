package RestResponseClasses;

/**
 * C.G
 */
public class NameValue {

    private String name;
    private int value;

    /**
     *
     * @param name
     * @param value
     */
    public NameValue(String name, int value) {
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
    public int getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }

}
