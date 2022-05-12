package com.adel.h.kalenderapi.region;

import com.adel.h.kalenderapi.feiertag.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/regions")
public class RegionController {
    @Autowired
    private RegionRepository regionRepository;

    @GetMapping("/subregions/{parent_region}")
    @ResponseBody
    public List<Region> getSubregions(@PathVariable(name = "parent_region") String parent_region){
        Region DE = regionRepository.findRegionByName(parent_region);
        List<Region> regions = regionRepository.findRegionsByParent(DE);
        return regions;
    }
    @GetMapping("/holidays")
    @ResponseBody List<Holiday> getHolidays(){
        Region sachsen = regionRepository.findRegionByName("Sachsen");
        Set<Holiday> holidays = sachsen.getHolidays();
        List<Holiday> h = holidays.stream().toList();
        return  h;
    }
}
