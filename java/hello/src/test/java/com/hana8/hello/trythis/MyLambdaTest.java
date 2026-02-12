package com.hana8.hello.trythis;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static sun.awt.image.MultiResolutionCachedImage.map;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MyLambdaTest {

  private final List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

  @Test
  void filter() {
    List<Integer> list = numbers.stream().filter((i -> i % 2 == 0)).toList();
    assertThat(list).isEqualTo(List.of(2,4,6,8));

    List<Integer> evens = filter(numbers, value -> value % 2 == 0).toList();
    // [2,4,6,8]
    assertThat(evens).isEqualTo(list);
  }

   @Test
  void mapTest(){
    List<Integer> squares = map(numbers, value-> value * value);
    assertThat(squares).isEqualTo(numbers.stream().map(i -> i + 1).toList());
   }

   @Test
  void findTest(){
    Integer bigger3 = find(numbers, value->value > 3);
    assertThat(bigger3).isEqualTo();
   }

   void ReduceTest(){
    int sum1 = reducer(numbers, 100, Integer::sum);
    int sum2 = reducer(numbers,0, (a, b) -> a * b);
    int sum3 = reducer(numbers,0, (a, b) -> a * b);
    int sum4 = reducer(numbers,0, (a, b) -> a * b);

   }
}
