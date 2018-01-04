package com.kickroot.numbers;

import java.util.Arrays;

public class Main {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Usage: java -jar numbers.jar <number> [number...]");
      return;
    }

    Arrays.asList(args).forEach(input -> {
      try {
        System.out.println(NumberParser.parse(input));
      } catch (IllegalArgumentException ex) {
        System.err.println("Error:  " + ex.getMessage());
      }
    });
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
