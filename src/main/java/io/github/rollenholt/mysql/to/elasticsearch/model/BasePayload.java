package io.github.rollenholt.mysql.to.elasticsearch.model;

/**
 * @author rollenholt
 */
public class BasePayload {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BasePayload{" +
                "id=" + id +
                '}';
    }
}
