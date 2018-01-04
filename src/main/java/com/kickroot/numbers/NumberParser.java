package com.kickroot.numbers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The meat of the number converter.  NumberParser is responsible for input validation, formatting, and handling
 * the edge cases the crop up.
 *
 * I've tried to make this class as forgiving on input as possible while still drawing the line in a place of sanity.
 * For example, the following will be accepted:
 *
 *  "1 . 000 . 000"
 *  "1.000.000"
 *  "--001.000.000" (the minus sign will only count once, they don't cancel each other out)
 *  "+1,000,000"
 *
 *  Note: All whitespace, periods, and commas are ignored, if you pass in a number of the form "100.00", it will parse the same
 *  as "10000".
 *
 *  This class is  designed to work with 32 bit signed integers (meaning that values must be in the range -2^31 to 2^31 - 1),
 *  but should be easily modifiable to support 64 bit ints.
 */
public final class NumberParser {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //
  // We use a list here instead of a straight array because it simplifies access. Instead
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

  protected static List<String> buildGroups(String input) {

    // Pad the input string to a multiple of 3 using leading zeros.  These get stripped
    // later but keeps the logic simpler.
    while (input.length() %3 != 0) {
      input = "0" + input;
    }

    // MSB (digits[0] contains the most significant byte)
    char[] digits = input.toCharArray();

    List<String> list = new ArrayList<>(input.length() / 3);
    String current = "";

    for (int i = 0 ; i < digits.length ; i++) {
      current += digits[i];

      if (i > 0 && (i+1) %3 == 0) {
        list.add(current);
        current = "";
      } else if (i == digits.length-1) {
        list.add(current);
      }
    }

    return list;
  }

  /**
   * The public entry point for number conversion.
   * @param number The unformatted, unvalidated number string to parse.
   * @return A well formatted textual representation of the input number.
   * @throws IllegalArgumentException For any null, extremely poorly formatted, or otherwise unparsable input.
   */
  public static String parse(String number) throws IllegalArgumentException {
    if (number == null) {
      throw new IllegalArgumentException("Number must not be null");
    } else if (number.isEmpty()) {
      throw new IllegalArgumentException("Number must not be blank");
    }

    //
    // Remove extraneous formatting and leading zeros
    //
    String formattedNumber = strip(number);

    //
    // Note and remove the leading minus sign(s), if any
    //
    final boolean minus = formattedNumber.startsWith("-");
    formattedNumber = formattedNumber.replaceAll("^-+", "");

    //
    // We strip again after the removal of the minus sign (think '-00)
    //
    formattedNumber = strip(formattedNumber);

    // Short circuit for zero
    if (formattedNumber.isEmpty() || formattedNumber.equals("0")) {
      if (minus) {
        return "Minus zero";
      } else {
        return "Zero";
      }
    }

    try {
      Integer.parseInt(formattedNumber);
    } catch (NumberFormatException ex) {
      throw new IllegalArgumentException(String.format("Cannot parse %s, invalid number", number));
    }

    //
    // Build out our list of triplets, starting with the most significant place.
    //
    List<String> arrays = buildGroups(formattedNumber);


    //
    // Assign each triplet to a place value (millions, thousands, etc)
    //
    // Place -> Digits
    Map<String, Triplet> map = new LinkedHashMap<>();
    int delta = GROUPS.size() - arrays.size() + 1;

    for (int i = 0 ; i < arrays.size() ; i++) {
      map.put(GROUPS.get(GROUPS.size() - i - delta), new Triplet(arrays.get(i)));
    }


    final StringBuilder result = new StringBuilder();

    //
    // Ignore non-value triplets, but otherwise append each triplet along with its place value.
    //
    map.entrySet().stream().filter(entry -> entry.getValue().hasValue()).forEach(entry ->
      result.append(entry.getValue()).append(" ").append(entry.getKey()).append(" ")
    );

    // Capitalize
    String str = (minus ? "minus " : "") + result.toString().trim();
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  

  private NumberParser() {}

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  //------------------------ Implements:

  //------------------------ Overrides:

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------

}
