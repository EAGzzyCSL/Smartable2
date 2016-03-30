package entry;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 */
public abstract class Entry {
    protected String name;
    private int id;

    public Entry(String name) {
        this.name = name;
    }
    public Entry(){

    }

    public String getName() {
        return this.name;
    }

}
