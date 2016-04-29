package entry;

import java.io.Serializable;

/**
 * Created by EAGzzyCSL on 2016/2/11.
 *
 */
//lily,略作修改:implements Serializable,为了能在页面间传递entry
public abstract class Entry implements Serializable {
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
