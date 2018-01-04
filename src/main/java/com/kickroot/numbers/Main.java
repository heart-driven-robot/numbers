package com.kickroot.numbers;

public class Main {

  ///////////////////////////// Class Attributes \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  ////////////////////////////// Class Methods \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Usage: java -jar numbers.jar <number> [number...]");
      return;
    }

    for (int i = 0 ; i < args.length ; i++) {
      String input = args[i];

      try {
        System.out.println(NumberParser.parse(input));
      } catch (IllegalArgumentException ex) {
        System.err.println("Error:  " + ex.getMessage());
      }
    }
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
