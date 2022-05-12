package com.adel.h.kalenderapi.region;

import com.adel.h.kalenderapi.region.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    public Region findRegionByName(String name);
    public List<Region> findRegionsByParent(Region parent);
}