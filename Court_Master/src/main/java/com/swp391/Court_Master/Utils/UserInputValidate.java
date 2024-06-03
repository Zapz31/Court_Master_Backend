package com.swp391.Court_Master.Utils;

import java.util.HashMap;

public class UserInputValidate {

    public String getString(String userInput, int min, int max, String pattern, HashMap<String, String> errorMap, String field, String label, String formatErrorMess){
        String resultString = userInput;
        if(resultString!=null){
            resultString.trim();
        }
        if(resultString == null || resultString.isEmpty()){
            resultString = "error";
            errorMap.put(label, field +" can not be empty");
        } else if(userInput.length() < min){
            resultString = "error";
            errorMap.put(label, field+"  must have at least "+min+" characters");
        } else if(userInput.length() > max){
            resultString = "error";
            errorMap.put(label, field + " must not exceed "+max+" characters");
        } else if(!resultString.matches(pattern)){
            resultString = "error";
            errorMap.put(label, formatErrorMess);
        }
        return resultString;
    }
}
