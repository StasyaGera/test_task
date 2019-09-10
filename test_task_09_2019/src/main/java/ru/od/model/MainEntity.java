package ru.od.model;

import org.hibernate.annotations.*;
import ru.od.view.SimpleView;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MainEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = SimpleView.pageSize)
    private List<SubEntity> subEntities = new ArrayList<>();

    public MainEntity() {
    }

    public MainEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubEntity> getSubEntities() {
        return subEntities;
    }

    public void setSubEntities(List<SubEntity> subEntities) {
        this.subEntities = subEntities;
    }
}
