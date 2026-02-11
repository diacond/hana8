package com.hana8.hello.trythis;

import java.util.*;

public class CollectionLog { // 여기는 왜 public?
  static class Log { // 여기는 왜 static?
    int id;
    String receiver;
    String sender;
    int amount;

    public Log(int id, String receiver, String sender, int amount) {
      this.id = id;
      this.receiver = receiver;
      this.sender = sender;
      this.amount = amount;
    }
  }

  public void solve(List<Log> logs) { // Log를 리스트에 담는게 맞음? 그리고 logs는 뭐임 갑자기?
    Map<String, Set<String>> recipientMap = new LinkedHashMap<>(); // 이건 이해함
    Map<String, Integer> freqMap = new HashMap<>(); // 이것도 이해함
    Map<String, Long> sendAmountMap = new HashMap<>(); // 이것도 이해함
    Map<String, Long> recvAmountMap = new HashMap<>(); // 여기도 오키

    String topFreqSender = "";
    int maxFreq = 0;
    String topMoneySender = "";
    long maxSendAmt = 0;
    String topMoneyReceiver = "";
    long maxRecvAmt = 0;

    for (Log log : logs) { // 로그의 4개 요소를 다 반복문으로 돌리는것 오케이.
      // (1) 받는 사람 기준 보낸 사람 명단 (중복 제거 & 순서 유지) 근데 k는 또 어디서 나온거?
      recipientMap.computeIfAbsent(log.receiver, k -> new LinkedHashSet<>()).add(log.sender);

      // (2) 빈도수 계산 및 실시간 1등 갱신
      int f = freqMap.getOrDefault(log.sender, 0) + 1; // 여기서 왜 1더하는거임?
      freqMap.put(log.sender, f);
      if (f > maxFreq) { maxFreq = f; topFreqSender = log.sender; }

      // (3) 보낸 금액 합산 및 실시간 1등 갱신
      long s = sendAmountMap.getOrDefault(log.sender, 0L) + log.amount;
      sendAmountMap.put(log.sender, s);
      if (s > maxSendAmt) { maxSendAmt = s; topMoneySender = log.sender; }

      // (4) 받은 금액 합산 및 실시간 1등 갱신
      long r = recvAmountMap.getOrDefault(log.receiver, 0L) + log.amount;
      recvAmountMap.put(log.receiver, r);
      if (r > maxRecvAmt) { maxRecvAmt = r; topMoneyReceiver = log.receiver; }
    }

    // 결과 출력
    display(recipientMap, topFreqSender, maxFreq, topMoneySender, maxSendAmt, topMoneyReceiver, maxRecvAmt);
  }

  private void display(Map<String, Set<String>> receiptMap, String tfs, int mf, String tms, long msa, String tmr, long mra) {
    System.out.println("1) 받는 사람 기준 보낸 사람 목록:");
    receiptMap.forEach((k, v) -> System.out.println(k + ": " + String.join(", ", v)));

    System.out.println("\n2) 통계 결과:");
    System.out.printf("   자주 보낸 사람: %s (%d회)\n", tfs, mf);
    System.out.printf("   최고금액 보낸 사람: %s (%,d원)\n", tms, msa);

    System.out.println("\n3) 가장 많은 금액을 받은 사람:");
    System.out.printf("   %s (%,d원)\n", tmr, mra);
  }

  public static void main(String[] args) { // 굳이 collectionLog의 객체를 만들어서 풀어야함? 그냥 main에서 풀면 안되나?
    CollectionLog analyzer = new CollectionLog();
    List<Log> data = Arrays.asList(
        new Log(1001, "Hong", "Choi", 5000),
        new Log(1002, "Lee", "Park", 20000),
        new Log(1003, "Hong", "Jade", 10000),
        new Log(1004, "Kim", "Park", 20000),
        new Log(1005, "Lee", "Choi", 5000),
        new Log(1006, "Hong", "Choi", 5000)
    );
    analyzer.solve(data);
  }
}
