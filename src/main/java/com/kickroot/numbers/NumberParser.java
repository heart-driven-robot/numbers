/*
 * Copyright (c) 2017 -  SourceClear Inc
 */

package com.kickroot.numbers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NumberParser {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //
  // We use a list here instead of a straight array because it simplifies access, instead
  // of OOB exception we get an empty value for groupings that have no title (hundreds).
  //
  // Hundreds don't get a grouping because their text is generated within the Triplet class.
  //
  private static final List<String> GROUPS = new ArrayList<>(3);
  static {
    GROUPS.add("");
    GROUPS.add("thousand");
    GROUPS.add("million");
    GROUPS.add("billion");
  }

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  protected static String strip(String input) {
    return input.trim().replaceAll("^0+", "").replaceAll("[\\s,\\\\.\\\\+]", "");
  }

  protected static List<char[]> buildGroups(String input) {

    // Pad the input string to a multiple of 3 using leading zeros.  These get stripped
    // later but keeps the logic simpler.
    while (input.length() %3 != 0) {
      input = "0" + input;
    }

    // MSB (digits[0] contains the most significant byte)
    char[] digits = input.toCharArray();

    List<char[]> list = new ArrayList<>(input.length() / 3);
    char[] currentArray = new char[3];
    for (int i = 0 ; i < digits.length ; i++) {
      int index = i % 3;
      currentArray[index] = digits[i];

      if (i > 0 && (i+1) %3 == 0) {
        list.add(currentArray);
        currentArray = new char[3];
      } else if (i == digits.length-1) {
        list.add(currentArray);
      }
    }

    return list;
  }

  public static String parse(String number) throws IllegalArgumentException {
    if (number == null) {
      throw new IllegalArgumentException("Number must not be null");
    }

    String formattedNumber = strip(number);

    try {
      Integer.parseInt(formattedNumber);
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException(String.format("Cannot parse %s, invalid number", number));
    }

    //
    // Build out our list of triplets, starting with the most significant place.
    //
    List<char[]> arrays = buildGroups(formattedNumber);


    //
    // Assign each triplet to a place value (millions, thousands, etc)
    //
    Map<String, char[]> map = new LinkedHashMap<>();
    int delta = GROUPS.size() - arrays.size() + 1;

    for (int i = 0 ; i < arrays.size() ; i++) {
      map.put(GROUPS.get(GROUPS.size() - i - delta), arrays.get(i));
    }


    final StringBuilder result = new StringBuilder();

    map.forEach((k, v) -> result.append(Triplet.parse(v)).append(" ").append(k).append(" "));

    return result.toString();
  }

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //------------------------ Implements:

  //------------------------ Overrides:

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------

}
