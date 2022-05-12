package com.adel.h.kalenderapi.feiertag;

import com.adel.h.kalenderapi.region.Region;
import com.adel.h.kalenderapi.region.RegionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {
    @Autowired
    private HolidayService HolidayService;

    @Autowired
    private RegionRepository regionRepository;

    @RequestMapping("/all")
    public List<Holiday> hello(){
        LocalDate date = LocalDate.now();
        List<Holiday> h = HolidayService.getHolidayYear(date.getYear());
        if (h.size() == 0){
            return HolidayService.determineHolidays(date.getYear());
        }
        return h;
    }
    @GetMapping("/{date}")
    @ResponseBody
    public String getFeiertagByDate(@PathVariable(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return date.toString();
    }

    @GetMapping("/year/{year}")
    @ResponseBody
    public List<Holiday> getFeiertageByYear(@PathVariable(name="year") int year){
        return HolidayService.getHolidayYear(year);
    }

    @RequestMapping("/feiertag/post")
    public Holiday feiertag(){
        Region rg = regionRepository.findRegionByName("Deutschland");
        List<Region> all = regionRepository.findRegionsByParent(rg);
        Set<Region> regions = new HashSet<>();
        regions.addAll(all);
        regions.add(rg);
        Holiday ft = new Holiday();
        ft.setDate(LocalDate.now());
        ft.setName("NEW");
        ft.setRegions(regions);

        return ft;
    }
    @RequestMapping("/easter/{year}")
    @ResponseBody
    public LocalDate getEAster(@PathVariable(name = "year") int year){
        return HolidayService.calculateEasterDate(year);
    }
    @GetMapping("/neujahr")
    @ResponseBody
    public List<Holiday> getNeuJahr(){
        final int year = 2022;
        return HolidayService.determineHolidays(year);
    }
}