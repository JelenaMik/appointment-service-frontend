package com.example.security.service.impl;

import com.example.security.service.DateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class DateServiceImpl implements DateService {

    @Override
    public List<Integer> getListOfDates(Integer week){
        LocalDate monday = new LocalDate().withWeekOfWeekyear(week).withDayOfWeek(DateTimeConstants.MONDAY);

        List<LocalDate> currentWeek = new ArrayList<>();
        for(int i=0; i<7; i++){
            currentWeek.add(monday.plusDays(i));
        }

        return currentWeek.stream()
                .map(LocalDate::getDayOfMonth)
                .toList();

    }

    @Override
    public String getMonth(Integer week){
        return new LocalDate().withWeekOfWeekyear(week).withDayOfWeek(DateTimeConstants.MONDAY).monthOfYear().getAsText();
    }

    @Override
    public Integer getYear(Integer week){
        return new LocalDate().withWeekOfWeekyear(week).withDayOfWeek(DateTimeConstants.MONDAY).getYear();
    }
    @Override
    public Integer getMonthNumber(Integer week){
        return new LocalDate().withWeekOfWeekyear(week).withDayOfWeek(DateTimeConstants.MONDAY).getMonthOfYear();
    }
}
