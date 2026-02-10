package com.hana8.hello.trythis;

import static org.junit.jupiter.api.Assertions.*;

public class PairTest {
  public static void main(String[] args) {
// 1. String과 Integer 쌍
    Pair<String, Integer> pair1 = new Pair<>("Age", 25);
    System.out.println(pair1.getFirst()); // "Age"
    System.out.println(pair1.getSecond()); // 25
// 2. 값 변경
    pair1.setFirst("Name");
    pair1.setSecond(30);
    System.out.println(pair1); // Pair{first=Name, second=30}
// 3. 두 값 교환
    Pair<Integer, String> pair2 = new Pair<>(100, "Score");
    Pair<String, Integer> swapped = pair2.swap();
    System.out.println(swapped); // Pair{first=Score, second=100}
// 4. 같은 타입 쌍
    Pair<Double, Double> pair3 = new Pair<>(3.14, 2.71);
    System.out.println(pair3.getFirst() + pair3.getSecond()); // 5.85
  }
}
