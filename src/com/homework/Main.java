package com.homework;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

  public static AtomicInteger beautyCountThree = new AtomicInteger();
  public static AtomicInteger beautyCountFour = new AtomicInteger();
  public static AtomicInteger beautyCountFive = new AtomicInteger();

  public static void main(String[] args) {
    Random random = new Random();
    String[] texts = new String[100_000];
    for (int i = 0; i < texts.length; i++) {
      texts[i] = generateText("abc", 3 + random.nextInt(3));
    }

    new Thread(() -> Arrays.stream(texts).forEach(text -> {
      if (isSame(text) && !isPalindrome(text)) {
        increaseCount(text);
      }
    })).start();

    new Thread(() -> Arrays.stream(texts).forEach(text -> {
      if (isPalindrome(text)) {
        increaseCount(text);
      }
    })).start();

    new Thread(() -> Arrays.stream(texts).forEach(text -> {
      if (isAscendingOrder(text)) {
        increaseCount(text);
      }
    })).start();

    System.out.printf("Красивых слов с длиной 3: %s шт%n", beautyCountThree);
    System.out.printf("Красивых слов с длиной 4: %s шт%n", beautyCountFour);
    System.out.printf("Красивых слов с длиной 5: %s шт%n", beautyCountFive);
  }

  public static String generateText(String letters, int length) {
    Random random = new Random();
    StringBuilder text = new StringBuilder();
    for (int i = 0; i < length; i++) {
      text.append(letters.charAt(random.nextInt(letters.length())));
    }
    return text.toString();
  }

  static void increaseCount(String text) {
      if (text.length() == 3) {
          beautyCountThree.getAndIncrement();
      }
      if (text.length() == 4) {
          beautyCountFour.getAndIncrement();
      }
      if (text.length() == 5) {
          beautyCountFive.getAndIncrement();
      }
  }

  static boolean isSame(String text) {
    return (!text.contains("b") && !text.contains("c"))
        || (!text.contains("a") && !text.contains("c"))
        || (!text.contains("b") && !text.contains("a"));
  }

  static boolean isPalindrome(String text) {
    int length = text.length();
    for (int i = 0; i < (length / 2); i++) {
      if (text.charAt(i) != text.charAt(length - i - 1)) {
        return false;
      }
    }
    return true;
  }

  static boolean isAscendingOrder(String text) {
    int length = text.length();
    for (int i = 0; i < (length / 2); i++) {
      if (text.charAt(i) != text.charAt(length - i - 1)) {
        return false;
      }
    }
    return true;
  }
}
