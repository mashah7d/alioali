package ir.corestockexchanging.model.domain;

/**
 * Created by Ali on 03/29/2016.
 */
public abstract class Entity {
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
