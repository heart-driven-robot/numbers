package com.kickroot.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {



  @ParameterizedTest
  @CsvSource({ "0, Zero", "13, Thirteen", "85, Eighty five", "5237, Five thousand two hundred thirty seven" })
  void testProvidedCases() {

  }
}