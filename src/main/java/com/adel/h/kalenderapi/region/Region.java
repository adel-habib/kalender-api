package com.adel.h.kalenderapi.region;

import com.adel.h.kalenderapi.feiertag.Holiday;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "region")
public class Region {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    private String short_name;

    @JsonIgnore
    @ManyToMany(mappedBy = "regions")
    private Set<Holiday> holidays = new HashSet<>();
   @ManyToOne
   @JoinColumn(name = "parent_id",referencedColumnName = "id")
   @Nullable
   @JsonIgnore
   private Region parent;
    public void setName(String name) {
        this.name = name;
    }

    public void setParent_id(Region region) {
        this.parent = region;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getName() {
        return name;
    }

    public Region getRegion() {
        return parent;
    }

    public String getShort_name() {
        return short_name;
    }

    public Set<Holiday> getHolidays() {
        return holidays;
    }
}