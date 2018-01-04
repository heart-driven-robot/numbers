package com.kickroot.numbers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NumberParserTest {

  @ParameterizedTest
  @CsvSource({ "0, Zero", "13, Thirteen", "85, Eighty five", "5237, Five thousand two hundred thirty seven" })
  void testProvidedCases(String input, String expected) {
    assertEquals(expected, NumberParser.parse(input));
  }

  @ParameterizedTest
  @ValueSource(strings = { "30a", "", "3-2" })
  void testInvalid(String input) {
    assertThrows(IllegalArgumentException.class,() -> NumberParser.parse(input));
  }


  @Test
  void testNull() {
    assertThrows(IllegalArgumentException.class,() -> NumberParser.parse(null));
  }

  @Test
  void testTooLarge() {
    assertThrows(IllegalArgumentException.class,() -> {
      Long number = Integer.MAX_VALUE + 1L;
      NumberParser.parse(number.toString());

    });
  }

  @Test
  void testTooSmall() {
    assertThrows(IllegalArgumentException.class,() -> {
      Long number = Integer.MIN_VALUE - 1L;
      NumberParser.parse(number.toString());

    });
  }

  @ParameterizedTest
  @CsvSource({
      "-0, 'Minus zero'",
      " - 13, 'Minus thirteen'",
      "-00, 'Minus zero'",
  })
  void testEdgeCases(String input, String expected) {
    assertEquals(expected, NumberParser.parse(input));
  }

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
      "'1,234,567,890', 'One billion two hundred thirty four million five hundred sixty seven thousand eight hundred ninety'",
      "'234,567,890', 'Two hundred thirty four million five hundred sixty seven thousand eight hundred ninety'",
      "'7,000', 'Seven thousand'",
      "'7,001', 'Seven thousand one'",
      "'27,001', 'Twenty seven thousand one'",
      "'1,000,027,001', 'One billion twenty seven thousand one'",
  })

  void test(String input, String expected) {
    assertEquals(expected, NumberParser.parse(input));
  }
}