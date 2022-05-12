package com.adel.h.kalenderapi.feiertag;

import com.adel.h.kalenderapi.region.Region;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "feiertag")
public class Holiday {

    public Holiday(){}

    public Holiday(String name, LocalDate date, Set<Region> regions){
        this.name = name;
        this.date = date;
        this.regions = regions;
    }
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String name;
    private LocalDate date;

    @ManyToMany
    @JoinTable(
            name = "holiday_regions",
            joinColumns = @JoinColumn(name = "holiday_id"),
            inverseJoinColumns = @JoinColumn(name = "region_id")
    )
    private Set<Region> regions = new HashSet<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate datum) {
        this.date = datum;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }
}