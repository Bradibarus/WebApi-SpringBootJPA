package Bradibarus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
public class Entry {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    @JsonIgnore
    private UUID id;

    //though UUID is unique itself, may be it is inefficient to use it as primary (and foreign) key ¯\_(ツ)_/¯
    //I considered using another smaller value for pk in Entry and fk in Data

    @Column
    @JsonProperty("Date")
    @NotNull
    private Date date;

    //if average data list size > 3, then two tables even with UUID as pk and fk are more efficient
    //then assigning each data row date and UUID

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "entry_fk")
    @JsonProperty("Data")
    @NotNull
    private List<Data> data;

    public Entry() {}

    public Date getDate() {
        return date;
    }

    public List<Data> getData() {
        return data;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
