package com.system.vetcare.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import de.jollyday.ManagerParameter;
import de.jollyday.ManagerParameters;

@Configuration
public class HolidayConfig {

    @Value("${holiday.calendar}")
    private String holidayCalendar;

    @Bean
    public ManagerParameter managerParameter() {
        return ManagerParameters.create(HolidayCalendar.valueOf(holidayCalendar));
    }
    
    @Bean
    public HolidayManager holidayManager(ManagerParameter parameters) {
        return HolidayManager.getInstance(parameters);
    }

}