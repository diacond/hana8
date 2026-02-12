package com.hana8.hello.trythis;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Lambda2 {
  public static void main(String[] args) {
    List<Integer> list = List.of(1, 10, 6, 3, 3, 5, 4, 2, 7, 7, 9, 8, 10);

    // 1. 짝수의 개수
    long count = list.stream().filter(i -> i % 2 == 0).count();
    System.out.println("짝수의 개수: " + count);

    // 2. 각 숫자를 제곱 (map 활용)
    System.out.println("각 숫자를 제곱:");
    list.stream().map(i -> i * i).forEach(System.out::println);

    // 3. 중복 제거 (distinct 활용)
    System.out.println("중복 제거:");
    list.stream().distinct().forEach(System.out::println);

    // 4. 기본 정렬 (sorted 활용)
    System.out.println("기본 정렬:");
    list.stream().sorted().forEach(System.out::println);

    // 5. 역순(내림차순) 정렬
    System.out.println("역순(내림차순) 정렬:");
    list.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);

    // 6. 처음 5개만 출력 (limit 활용)
    System.out.println("처음 5개만 출력:");
    list.stream().limit(5).forEach(System.out::println);

    // 7. 처음 5개 건너뛰고 출력 (skip 활용)
    System.out.println("처음 5개 건너뛰고 출력:");
    list.stream().skip(5).forEach(System.out::println);

    // 8. 값이 5보다 큰 것만 출력
    System.out.println("값이 5보다 큰 것만 출력:");
    list.stream().filter(i -> i > 5).forEach(System.out::println);

    // 9. 1~10의 합계 (reduce 활용)
    int sum = list.stream().distinct().reduce(0, Integer::sum);
    System.out.println("1~10의 합계: " + sum);

    // 10. random 5개의 평균
    System.out.println("random 5개의 평균:");
    new Random().ints(5, 1, 11) // 1~10 사이의 난수 5개 생성
        .average()
        .ifPresent(System.out::println);
  }
}
