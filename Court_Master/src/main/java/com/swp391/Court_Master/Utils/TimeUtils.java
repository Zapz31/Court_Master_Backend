package com.swp391.Court_Master.Utils;

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
}
