package entry;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 */
public abstract class Entry {
    private String name;

    public Entry(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
