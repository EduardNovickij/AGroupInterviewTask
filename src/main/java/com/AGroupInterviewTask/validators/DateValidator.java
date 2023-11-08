package com.AGroupInterviewTask.validators;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public record DateValidator() {
    private static boolean isDateFormatValid(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT);
            formatter.parse(date);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }

    public static void checkDateFormat(String date) throws Exception {
        String errorMessageStart = "Incorrect input: \n";
        String errorMessageEnd = "";

        if(date == null) throw new Exception(errorMessageStart + "Date can't be null.\n");
        if(date.length() == 0) errorMessageEnd += "Date can't be empty.\n";
        if(!isDateFormatValid(date)) errorMessageEnd += "Date must be in YYYY-MM-DD format.\n";

        if(!errorMessageEnd.equals("")) throw new Exception(errorMessageStart + errorMessageEnd);
    }
}
