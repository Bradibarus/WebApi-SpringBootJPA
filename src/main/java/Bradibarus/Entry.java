package Bradibarus;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class Entry {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private Date date;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "entry_fk")
    private List<Data> data;

    public Entry(Date date, List<Data> data)  {
        this.date = date;
        this.data = data;
    }

    public Entry() {
    }

    public Date getDate() {
        return date;
    }

    public List<Data> getData() {
        return data;
    }

    public long getId() {
        return id;
    }
}
