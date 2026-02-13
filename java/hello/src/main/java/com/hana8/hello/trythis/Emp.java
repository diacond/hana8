package com.hana8.hello.trythis;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

class Emp {
  String name;
  String dept;
  int score;

  Emp(String name, String dept, int score) {
    this.name = name;
    this.dept = dept;
    this.score = score;
  }

  public String getName() { return name; }
  public String getDept() { return dept; }
  public int getScore() { return score; }

  public static void main(String[] args) {
    List<Emp> emps = Arrays.asList(
        new Emp("Hong", "Sales", 85),
        new Emp("Kim", "Sales", 95),
        new Emp("Choi", "HR", 55),
        new Emp("Nam", "HR", 75),
        new Emp("Lee", "IT", 82),
        new Emp("Park", "IT", 92),
        new Emp("Ahn", "Sales", 95)
    );

    List<Emp> result = emps.stream()
        .filter(e -> e.score >= 70)
        .toList();
    System.out.println("result = " + result.stream().map(Emp::getScore).toList()); // 여기서 점수만 따로 출력하는 방법?

    Map<String, List<String>> deptmap = result.stream().collect(Collectors.groupingBy(Emp::getDept, TreeMap::new, Collectors.mapping(Emp::getName, Collectors.toList())
    ));
    System.out.println("deptmap = " + deptmap);

    Map<String, String> maxscore = emps.stream()
        .collect(Collectors.groupingBy(
            Emp::getDept,
            Collectors.collectingAndThen(
                Collectors.maxBy(
                    Comparator.comparingInt(Emp::getScore)
                        .thenComparing(Comparator.comparing(Emp::getName).reversed())
                ),
                opt -> opt.map(e -> e.getName() + "(" + e.getScore() + ")").orElse("")
            )
        ));
    maxscore.forEach((dept, info) -> {
      System.out.println(dept + ":" + info);
    });

    System.out.println("=======================================================");

    Map<String, String> maxreverse = emps.stream()
        .collect(Collectors.groupingBy(
            Emp::getDept,
            () -> new TreeMap<>(Comparator.reverseOrder()),
            Collectors.collectingAndThen(
                Collectors.maxBy(
                    Comparator.comparingInt(Emp::getScore)
                        .thenComparing(Comparator.comparing(Emp::getName).reversed())
                ),
                opt -> opt.map(e -> e.getName() + "(" + e.getScore() + ")").orElse("")
            )
        ));
    maxreverse.forEach((dept, info) -> {
      System.out.println(dept + ":" + info);
    });
  }
}
