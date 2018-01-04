package com.kickroot.numbers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The Triplet class is responsible for parsing out the 3 digit triplets that are the basis for number groupings
 * (think thousands, millions, billions).
 *
 * Triplets are ignorant of which grouping they belong to and are only focused on parsing out the 1-3 digit number
 * passed to it.
 */
public class Triplet {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private static final Map<String, String> ONES = new HashMap<>();
  static {
    ONES.put("0", "");
    ONES.put("1", "one");
    ONES.put("2", "two");
    ONES.put("3", "three");
    ONES.put("4", "four");
    ONES.put("5", "five");
    ONES.put("6", "six");
    ONES.put("7", "seven");
    ONES.put("8", "eight");
    ONES.put("9", "nine");
  }

  private static final Map<String, String> TEENS = new HashMap<>();
  static {
    // I know, the first three aren't technically teens but they follow
    // the same set of rules.  If it looks like a duck and quacks like a duck...
    TEENS.put("10", "ten");
    TEENS.put("11", "eleven");
    TEENS.put("12", "twelve");
    TEENS.put("13", "thirteen");
    TEENS.put("14", "fourteen");
    TEENS.put("15", "fifteen");
    TEENS.put("16", "sixteen");
    TEENS.put("17", "seventeen");
    TEENS.put("18", "eighteen");
    TEENS.put("19", "nineteen");
  }

  private static final Map<String, String> TENS = new HashMap<>();
  static {
    TENS.put("0", "");
    // No 1-based tens, this is handled by the teens logic
    TENS.put("2", "twenty");
    TENS.put("3", "thirty");
    TENS.put("4", "forty");
    TENS.put("5", "fifty");
    TENS.put("6", "sixty");
    TENS.put("7", "seventy");
    TENS.put("8", "eighty");
    TENS.put("9", "ninety");
  }

  //
  // Just for clarity
  //
  private static final Map<String, String> HUNDREDS = ONES;

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private static String convert(String number) {

    number = number.trim().replaceAll("^0+", "");

    //
    // If the triplet consists strictly of zeros return an empty string, since omitting
    // it entirely is the desired behavior (think 3000, where the hundreds triplet has no textual component).
    // Because of this, our top level code must handle the case where an entire number parses to ''
    // and return 'zero' instead.
    //
    if (number.length() == 0) {
      return "";
    }

    Integer.parseInt(number);


    return parse(number.toCharArray()).trim();
  }


  /**
   * @param digits
   * @return
   */
  private static String parse(char[] digits) {

    String results = "";

    if (digits.length == 3) {
      results = HUNDREDS.get("" + digits[0]) + " hundred";
    }

    results += " " + parseTens(digits);
    return results;
  }

  private static String parseTens(char[] digits) {

    //
    // Chop off the hundreds if it exists
    //
    if (digits.length == 3) {
      digits = Arrays.copyOfRange(digits, 1, digits.length);
    }

    if (digits.length == 2) {

      if (digits[0] == '0') {
        return parseOnes(digits[1]);
      } else if (digits[0] == '1') {
        return TEENS.get("" + digits[0] + digits[1]);
      } else {
        return String.format("%s %s", TENS.get("" + digits[0]), ONES.get("" + digits[1]));
      }

    } else return parseOnes(digits[0]);
  }

  private static String parseOnes(char digit) {
    return ONES.get("" + digit);
  }

  //////////////////////////////// Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  private final String input;

  private final String results;

  /////////////////////////////// Constructors \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\  

  /**
   * Create and parse a new Triplet.
   * @param input The 1-3 digit number string to parse.  Triplets expect properly formatted input!
   */
  public Triplet(String input) {
    assert(input.length() > 0 && input.length() < 4);

    this.input = input;
    results = convert(input);
  }

  ////////////////////////////////// Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  /**
   * Determines whether this triplet has any non-zero value to it.  Triplets that
   * have zero value are usually ignored in the textual representation but are still
   * required for determining the placement of other groups.
   */
  public boolean hasValue() {
    return !input.equals("000");
  }

  //------------------------ Implements:

  //------------------------ Overrides: Object

  public String toString() {
    return results;
  }

  //---------------------------- Abstract Methods -----------------------------

  //---------------------------- Utility Methods ------------------------------

  //---------------------------- Property Methods -----------------------------

}
