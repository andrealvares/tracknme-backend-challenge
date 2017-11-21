package br.com.tracknme.model;

import java.io.Serializable;

/**
 * Created by Cleberson on 19/11/2017.
 */
public class TestEntity implements Serializable {
    private long id;
    private String value;

    public TestEntity() {
    }

    public TestEntity(long id, String value) {
        this.id = id;
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
