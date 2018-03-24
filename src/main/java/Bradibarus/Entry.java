package Bradibarus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@XmlRootElement
public class Entry {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column
    @JsonProperty("Date")
    private Date date;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "entry_fk")
    @JsonProperty("Data")
    private List<Data> data;

    public Entry() {}

    public Date getDate() {
        return date;
    }

    public List<Data> getData() {
        return data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
