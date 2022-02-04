package database.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "items", schema = "public", catalog = "container2")
public class ItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "name", nullable = false, length = -1)
    private String name;
    @Basic
    @Column(name = "details", nullable = false, length = -1)
    private String details;
    @Basic
    @Column(name = "nr_accesses", nullable = false)
    private int nrAccesses;
    @Basic
    @Column(name = "last_accessed", nullable = false)
    private Time lastAccessed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getNrAccesses() {
        return nrAccesses;
    }

    public void setNrAccesses(int nrAccesses) {
        this.nrAccesses = nrAccesses;
    }

    public Time getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(Time lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemsEntity that = (ItemsEntity) o;
        return id == that.id && nrAccesses == that.nrAccesses && Objects.equals(name, that.name) && Objects.equals(details, that.details) && Objects.equals(lastAccessed, that.lastAccessed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, details, nrAccesses, lastAccessed);
    }
}
