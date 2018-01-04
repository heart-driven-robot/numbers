package com.kickroot.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TripletTest {


  @ParameterizedTest
  @CsvSource({
      "13, thirteen",
      "013, 'thirteen'",
      "085, 'eighty five'",
      "12, twelve",
      "5, five",
      "005, five",
      "123, 'one hundred twenty three'", "456, 'four hundred fifty six'",
      "321, 'three hundred twenty one'",
      "301, 'three hundred one'",
      "300, 'three hundred'",
      "310, 'three hundred ten'",
      "313, 'three hundred thirteen'",
      "380, 'three hundred eighty'",
      "31, 'thirty one'",
      "30, 'thirty'"
  })
  void testProvidedCases(String input, String expected) {
    assertEquals(expected, new Triplet(input).toString());
  }
}