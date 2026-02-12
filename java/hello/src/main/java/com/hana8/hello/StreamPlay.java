package com.hana8.hello;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamPlay {

  public static void main(String[] args) {
    List<String> list = Arrays.asList("JS", "TS", "Java", "JS");
    Stream<String> stream = list.stream(); // 코스요리인건 맞는데, 무슨 요리인지는 String, Integer 등등
    // 근데 하나만 리턴하는게 아니라, List로 여러 개가 리턴될 수도 있다.
    List<String> list1 = list.stream().toList();
    list.stream().collect(Collectors.toList());

    String collect = list.stream().collect(Collectors.joining(", "));
    String collect2 = String.join(", ", list);
    System.out.println("collect2 = " + collect2);
    list.forEach(System.out::print);
    list.stream().forEach(System.out::println);

    System.out.println("collect(Collectors.groupingBy(String::length) = " + list.stream()
        .collect(Collectors.groupingBy(String::length)));

    long count = list.stream().map(String::length).count();
    System.out.println("count = " + count);
    Stream<Integer> stream1 = list.stream().map(String::length);
    IntStream intStream = list.stream().mapToInt(String::length);

    Map<String, Integer> map1 = list.stream().collect(Collectors.toMap(s -> s, String::length));

    System.out.println("map1 = " + map1);

    Collections.swap(list, 1, 2); // 캐시를 했는데 자주 쓰는 건 위로 올려야함. 그럴 때 써주면 된다.

    Function<String, Integer> length = s -> s.length();
    Function<String, Integer> length2 = String::length; // 얘랑 윗줄이랑 똑같다....

    Consumer<String> print = System.out::println;


  }

}
