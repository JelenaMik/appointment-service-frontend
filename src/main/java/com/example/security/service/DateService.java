package com.example.security.service;

import java.util.List;

public interface DateService {

    List<Integer> getListOfDates(Integer week);

    String getMonth(Integer week);

    Integer getYear(Integer week);

    Integer getMonthNumber(Integer week);
}
