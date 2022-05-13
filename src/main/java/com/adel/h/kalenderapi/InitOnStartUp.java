package com.adel.h.kalenderapi;

import com.adel.h.kalenderapi.feiertag.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.logging.Logger;

@Component
public class InitOnStartUp implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = Logger.getLogger(InitOnStartUp.class.getName());
    public static int counter;
    @Autowired
    private HolidayService holidayService;

    @EventListener
    @Override public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.info("Initialising Holidays");
        holidayService.initHolidays(LocalDate.now());
        counter++;
    }

}
