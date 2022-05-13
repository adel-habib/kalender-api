package com.adel.h.kalenderapi.feiertag;

import com.adel.h.kalenderapi.region.Region;
import com.adel.h.kalenderapi.region.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired RegionRepository regionRepository;

    public Optional<Holiday> getHolidayByDate(LocalDate date){
        List<Holiday> ft = holidayRepository.findHolidayByDate(date);
        if(ft.size()> 0 ){
            return Optional.of(ft.get(0));
        }else {
            return Optional.empty();
        }
    }
    public List<Holiday> getAllHolidays(){
        return holidayRepository.findAll();
    }
    public List<Holiday> getHolidayYear(int year){
        LocalDate start = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(year, Month.DECEMBER, 31);
        return holidayRepository.findHolidayByDateBetween(start,end);
    }
    public void saveFeiertag(Holiday ft){
        holidayRepository.save(ft);
    }
    public LocalDate calculateEasterDate(int year) {
        final int a = year % 19;
        final int b = year % 4;
        final int c = year % 7;
        final int k = year / 100;
        final int p = k / 3;
        final int q = k / 4;
        final int m = (15 + k - p - q) % 30;
        final int d = (19 * a + m) % 30;
        final int n = (4 + k - q) % 7;
        final int e = (2 * b + 4 * c + 6 * d + n) % 7;
        final int easter = (22 + d + e);
        Month month = easter > 31 ? Month.APRIL : Month.MARCH;
        int day = easter > 31 ? easter - 31 : easter;
        return LocalDate.of(year, month, day);
    }

    public void initHolidays(LocalDate current){
        LocalDate future = current.plusYears(10);
        LocalDate past = current.minusYears(10);
        holidayRepository.deleteAll();
        List<Holiday> calculatedHolidays = new ArrayList<>();
        for (int i= past.getYear(); i< future.getYear(); i++)
            calculatedHolidays.addAll(determineHolidays(i));
        calculatedHolidays.forEach( (holiday -> holidayRepository.save(holiday)));
    }
    public List<Holiday> determineHolidays(int year){

        final String DE = "Deutschland";
        final String BW = "Baden-Württemberg";
        final String BY = "Bayern";
        final String ST = "Sachsen-Anhalt";
        final String BB = "Brandenburg";
        final String HE = "Hessen";
        final String NW = "Nordrhein-Westfalen";
        final String RP = "Rheinland-Pfalz";
        final String SL = "Saarland";
        final String BE = "Berlin";
        final String HB = "Bremen";
        final String HH = "Hamburg";
        final String SN = "Sachsen";
        final String TH = "Thüringen";
        final String SH = "Schleswig-Holstein";
        final String MV = "Mecklenburg-Vorpommern";
        final String NV = "Nordrhein-Westfalen";
        final String NI = "Niedersachsen";

        LocalDate NOV_23 = LocalDate.of(year, Month.NOVEMBER, 23);
        // Fixed Holidays
       final LocalDate NEUJAHR = LocalDate.of(year, Month.JANUARY, 1);
       final LocalDate HEILIGE_DER_DREI_KOENIGE = LocalDate.of(year, Month.JANUARY, 6);
       final LocalDate FRAUEN_TAG = LocalDate.of(year, Month.MARCH, 8);
       final LocalDate TAG_DER_ARBEIT = LocalDate.of(year, Month.MAY, 1);
       final LocalDate MARIA_HIMMELFAHRT = LocalDate.of(year, Month.AUGUST, 15);
       final LocalDate WELT_KINDER_TAG = LocalDate.of(year, Month.SEPTEMBER, 20);
       final LocalDate TAG_DER_DEUTSCHEN_EINHEIT = LocalDate.of(year, Month.OCTOBER, 3);
       final LocalDate REFORMATIONSTAG = LocalDate.of(year, Month.OCTOBER, 31);
       final LocalDate ALLERHEILIGEN = LocalDate.of(year, Month.NOVEMBER, 1);
       final LocalDate ERSTER_WEIHNACHTSTAG = LocalDate.of(year, Month.DECEMBER, 25);
       final LocalDate ZWEITER_WEIHNACHTSTAG = ERSTER_WEIHNACHTSTAG.plusDays(1);
       // Easter-based Holidays
       final LocalDate OSTER_SONNTAG = calculateEasterDate(year);
       final LocalDate OSTER_MONTAG = OSTER_SONNTAG.plusDays(1);
       final LocalDate KAR_FREITAG = OSTER_SONNTAG.minusDays(2);
       final LocalDate CHRISTI_HIMMEL_FAHRT = OSTER_SONNTAG.plusDays(39);
       final LocalDate PFINGST_SONNTAG = OSTER_SONNTAG.plusDays(49);
       final LocalDate PFINGST_MONTAG = PFINGST_SONNTAG.plusDays(1);
       final LocalDate FRONLEICHNAM = OSTER_SONNTAG.plusDays(60);
       final LocalDate BUSS_UND_BETTAG = NOV_23.with(TemporalAdjusters.previous(DayOfWeek.WEDNESDAY));

       List<LocalDate> federalHolidaysDates = new ArrayList<>(
               Arrays.asList(
                       NEUJAHR,
                       KAR_FREITAG,
                       OSTER_MONTAG,
                       CHRISTI_HIMMEL_FAHRT,
                       PFINGST_MONTAG,TAG_DER_ARBEIT,
                       TAG_DER_DEUTSCHEN_EINHEIT,
                       ERSTER_WEIHNACHTSTAG,
                       ZWEITER_WEIHNACHTSTAG
               )
       );

        List<String> federalHolidaysNames = new ArrayList<>(
                Arrays.asList(
                        "Neujahr",
                        "Karfreitag",
                        "Ostermontag",
                        "Christi Himmelfahrt",
                        "Pfingstmontag",
                        "1. Mai",
                        "Tag der Deutschen Einheit",
                        "erster Weihnachtstag",
                        "zweiter Weihnachtstag"
                )
        );

        List<Holiday> holidays = new ArrayList<>();

        Region GERMANY = regionRepository.findRegionByName("Deutschland");
        Set<Region> federalStates = new HashSet<>();
        federalStates.addAll(regionRepository.findRegionsByParent(GERMANY));
        federalStates.add(GERMANY);

        int[] idx = { 0 };
        federalHolidaysDates.forEach( (date) -> holidays.add(new Holiday(federalHolidaysNames.get(idx[0]++),date,federalStates)));
        return holidays;
    }

}
