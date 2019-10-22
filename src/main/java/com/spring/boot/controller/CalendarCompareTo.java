package com.spring.boot.controller;

import java.util.Calendar;
import java.util.Comparator;

public class CalendarCompareTo implements Comparator<Calendar> { 
    public int compare(Calendar c1, Calendar c2) { 
    if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) 
     return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR); 
    if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) 
     return c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH); 
    return c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH); 
    } 
} 