package entry;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 */
public abstract class Entry {
    protected String name;
    protected int id;

    public Entry(String name) {
        this.name = name;
    }

    public Entry() {

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

}
