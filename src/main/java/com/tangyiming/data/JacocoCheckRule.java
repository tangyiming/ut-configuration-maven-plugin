package com.tangyiming.data;

public class JacocoCheckRule {
    private String element;
    private String counter;
    private String value;
    private Float minimum;

    public JacocoCheckRule() {
    }

    public JacocoCheckRule(String element, String counter, String value, Float minimum) {
        this.element = element;
        this.counter = counter;
        this.value = value;
        this.minimum = minimum;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Float getMinimum() {
        return minimum;
    }

    public void setMinimum(Float minimum) {
        this.minimum = minimum;
    }

    @Override
    public String toString() {
        return "JacocoCheckRule{" +
                "element='" + element + '\'' +
                ", counter='" + counter + '\'' +
                ", value='" + value + '\'' +
                ", minimum=" + minimum +
                '}';
    }
}
