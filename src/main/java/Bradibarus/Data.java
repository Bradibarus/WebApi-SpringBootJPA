package Bradibarus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @JsonIgnore
    private int id;

    @Column
    @JsonProperty("Name")
    @NotNull
    private String name;

    @Column
    @JsonProperty("Value")
    @NotNull
    private int value;

    public Data() {}

    public Data(@NotNull String name, @NotNull int value) {
        this.name = name;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "{" +
                "Name=" + name +
                ", Value=" + value +
                "}";
    }
}
