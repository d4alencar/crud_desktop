package com.d4alencar.crud.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {

  public int checkBookFields (String title, String author, String year) {

    Pattern stringPattern = Pattern.compile("[0-9!@#$%]");

    if(!title.equals("")) {
      Matcher matcher = stringPattern.matcher(title);
      boolean matchFound = matcher.find();
      
      if(!matchFound) {
        matcher = stringPattern.matcher(author);
        matchFound = matcher.find();
        if(!matchFound) {
          try {
            Integer.parseInt(year);
            return 0;
          } catch (NumberFormatException e) {
            return 3;
          }
        } else {
          return 2;
        }
      } else {
        return 2;
      }
    }
    return 1;
  }
}
