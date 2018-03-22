package Bradibarus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @JsonIgnore
    private int id;

    @Column
    private String name;

    @Column
    private int value;

    public Data( String name, int value) {
        this.name = name;
        this.value = value;
    }

    public Data() {
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
