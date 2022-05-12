package com.adel.h.kalenderapi.feiertag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Integer> {
    public List<Holiday> findHolidayByDate(LocalDate date);

    public List<Holiday> findHolidayByDateBetween(LocalDate start, LocalDate end);
}