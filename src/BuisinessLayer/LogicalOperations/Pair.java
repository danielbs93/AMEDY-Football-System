package BuisinessLayer.LogicalOperations;

public class Pair {

    //Fields
    private String key;
    private String value;


    public Pair() {
        this("", "");
    }

    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
