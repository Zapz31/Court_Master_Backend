package com.swp391.Court_Master.Utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static int convertTimeFormatToMinutes(String timeFormatString){
        String[] parts = timeFormatString.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    public static String convertMinutestoTimeFormat(int TotalMinutes){
        int hours = TotalMinutes/60;
        int minutes = TotalMinutes % 60;
        String formattedTime = String.format("%02d:%02d", hours, minutes);
        return formattedTime;
    }

    public static double getTimeDiffMidnight(LocalTime firsTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String firstTimeString = firsTime.format(formatter);
        String lastTimeString = "24:00";

        int firstTimeMinute = convertTimeFormatToMinutes(firstTimeString);
        int lastTimeMinute = convertTimeFormatToMinutes(lastTimeString);
        int minuteDiff = lastTimeMinute - firstTimeMinute;
        double hourDiff = (minuteDiff*1.0)/60;
        return hourDiff;
    }

    public static String getTimeDiffMidNightString(LocalTime startTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String firstTimeString = startTime.format(formatter);
        String lastTimeString = "24:00";
        int firstTimeMinute = convertTimeFormatToMinutes(firstTimeString);
        int lastTimeMinute = convertTimeFormatToMinutes(lastTimeString);
        int minuteDiff = lastTimeMinute - firstTimeMinute;
        return convertMinutestoTimeFormat(minuteDiff);

    }

    public static String convertLocalDatetoString(LocalDate inputDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return inputDate.format(formatter);
    }

    public static String convertLocalTimeToString(LocalTime inputTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return inputTime.format(formatter);
    }
}
