package com.hana8.hello.trythis;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class DateTime {

  public static void main(String[] args) {
    LocalDate nowLd = LocalDate.now();
    LocalTime nowLt = LocalTime.now();
    System.out.println( nowLd + " " + nowLt);
    LocalDateTime now = LocalDateTime.now();
    System.out.println("now = " + now);
    LocalDate graduation = LocalDate.of(2026, 2, 20);
    System.out.println("graduation = " + graduation);
    
//    for(String zone : ZoneId.getAvailableZoneIds()){
//      ZoneId zoneId = ZoneId.of(zone);
//      if(zoneId.toString().contains("Europe/Rome"))
//        System.out.println(zoneId + " => " + zoneId.getRules());
//
//    }
    ZoneId newyork = ZoneId.of("America/New_York");
    System.out.println("newyork = " + newyork);
    LocalDateTime.now();
    ZonedDateTime zonedDateTime = ZonedDateTime.of(now, newyork);
    boolean isNYDST = newyork.getRules().isDaylightSavings
        (zonedDateTime.toInstant());
    System.out.println("isNYDST = " + isNYDST);

    ZoneId seoul = ZoneId.systemDefault();
    ZonedDateTime zonedDateTimeSeoul = ZonedDateTime.of(now, seoul);
    boolean isSeoulDST = ZoneId.systemDefault().getRules()
        .isDaylightSavings(zonedDateTimeSeoul
            .toInstant());
    System.out.println("isSeoulDST = " + isSeoulDST);

//    ====================================================
    Instant iNow = Instant.now();
    System.out.println("iNow = " + iNow);
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    System.out.println(now.format(fmt));
    Instant epoch = Instant.ofEpochSecond(0);
    System.out.println("epoch = " + epoch);
    System.out.println(LocalDate.now().plusMonths(1).atStartOfDay());

//    ======================================================
    LocalDateTime myBirthday = LocalDateTime.of(2000, 12, 2, 14, 30);
    LocalDateTime now2 = LocalDateTime.now();
    System.out.println("myBirthday = " + myBirthday);
    System.out.println("now2 = " + now2);
    // 기간을 구함. 연월일까지
    Period period = Period.between(myBirthday.toLocalDate(), now2.toLocalDate());
    System.out.println("period = " + period);

    // 초까지 구하는 것. 내 나이를 초까지 구하기.
    Duration dDate = Duration.between(myBirthday, now2);
    long h = dDate.toHours() % 24;
    System.out.println("dDate = " + dDate);
    System.out.println("h = " + h);

    // 올해 생일까지 얼마나 남았나?
    LocalDate Mybirthday2 = LocalDate.of(2026, 12, 2);
    System.out.println("Mybirthday2 = " + Mybirthday2);
    LocalDate now3 = LocalDate.now();
    System.out.println("now3 = " + now3);
    Period period2 = Period.between(now3, Mybirthday2);
    System.out.println("period2 = " + period2);

    // 0323 0900 ~ 0424 1700 까지, 휴일 제외, 플젝할 수 있는 시간(Hour)
    LocalDateTime start = LocalDateTime.of(2026, 3, 23, 9, 0);
    LocalDateTime end = LocalDateTime.of(2026, 4, 24, 17, 0);
    long hours = ChronoUnit.HOURS.between(start, end);
    System.out.println("we can work for " + hours + " hours");

    System.out.println("=========================================");

    //플젝 일 할 수 있는 시간 구하기. Hour

    LocalDateTime startDateTime = LocalDateTime.of(2026, 3, 23, 9, 0);
    LocalDateTime endDateTime = LocalDateTime.of(2026, 4, 20, 17, 0);

    // 2. 설정: 제외할 휴일 (3월 25일)
    Set<LocalDate> holidays = Set.of(LocalDate.of(2026, 3, 25));
    
    long totalWorkHours = startDateTime.toLocalDate()
        .datesUntil(endDateTime.toLocalDate().plusDays(1)) // 시작일부터 종료일까지 하루씩 순회
        .mapToLong(date -> {
          // 주말(토, 일)이거나 지정된 휴일(3월 25일)이면 0시간
          if (holidays.contains(date)) {
            return 0;
          }

          // [특수 조건] 4월 13일 ~ 4월 17일: 09:00~19:00 (10시간 - 점심 1시간 = 9시간)
          // 여기 주의!!! 논리적으로 0시 기준으로 비포 애프터 생각해야한다.
          if (!date.isBefore(LocalDate.of(2026, 4, 13)) &&
              !date.isAfter(LocalDate.of(2026, 4, 17))) {
            return 9;
          }

          // [종료일 예외 처리] 4월 20일은 17:00까지만 근무 (09:00~17:00 = 8시간이지만,
          // 만약 종료 시간이 더 빠르다면 조정이 필요함. 여기서는 17시 종료이므로 기본 8시간과 동일)

          // [기본 조건] 일반 영업일: 8시간
          return 8;
        })
        .sum();
    System.out.println("totalWorkHours = " + totalWorkHours);

    System.out.println("=================================");
    ZoneId milano = ZoneId.of("Europe/Rome");
    LocalDateTime milanoTime = LocalDateTime.now(milano);
    System.out.println("milanoTime = " + milanoTime);
    System.out.println("=================================");

  }

}
