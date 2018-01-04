package com.kickroot.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class NumberParserTest {

  @ParameterizedTest
  @CsvSource({
      "'1,000,000', '1000000'",
      "'1.000.000', '1000000'",
      "'1 000 000', '1000000'",
      "'1 . 000 . 000', '1000000'",
      "'  1.000.000  ', '1000000'",
      "'001.000.000', '1000000'",
      "'+1,000,000', '1000000'",
      "'+1, 000, 000', '1000000'",
  })
  void testStripping(String input, String expected) {
    assertEquals(expected, NumberParser.strip(input));
  }

  @ParameterizedTest
  @CsvSource({
      "'1,234,567,890', 'one billion two hundred thirty four million five hundred sixty seven thousand eight hundred ninety'",
  })
  void testPlaces(String input, String expected) {
    assertEquals(expected, NumberParser.parse(input));
  }

}