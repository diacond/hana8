package com.hana8.hello;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class CollectionPlay {

  public static void main(String[] args) {
    var alist = new ArrayList<>();
    alist.add(4);
    alist.add(3);
    System.out.println("alist = " + alist);
    
    Queue<Integer> ll = new LinkedList<>();
    ll.offer(4);
    ll.offer(5);
    ll.offer(1);
    System.out.println("ll = " + ll);

    Queue<Integer> pq = new PriorityQueue<>();
    // 힙 조정이 일어나서, 음..?
    // 이진 트리 - 왼쪽부터 채운다. 다 차면
    pq.offer(4);
    pq.offer(5);
    pq.offer(1);
    pq.offer(2);
    System.out.println("pq = " + pq);
    pq.offer(3);
    System.out.println("pq.poll() = " + pq.poll());
    System.out.println("pq.poll() = " + pq.poll());
    System.out.println("pq.poll() = " + pq.poll());
    System.out.println("pq.poll() = " + pq.poll());
    System.out.println("pq.poll() = " + pq.poll());
// set의 특징 - 중복이 없다.

    Set<Integer> ts = new TreeSet<>();
    ts.add(4);
    ts.add(3);
    ts.add(1);
    System.out.println("ts = " + ts);

    Deque<Integer> dq = new ArrayDeque<>();
    dq.offer(4);
    dq.offer(5);
    dq.offer(1);
    System.out.println("dq = " + dq);
    dq.offer(2);
    System.out.println("dq = " + dq);
    dq.offer(3);
    System.out.println("dq = " + dq);
    System.out.println("dq.poll() = " + dq.poll());
    System.out.println("dq.poll() = " + dq.poll());
    System.out.println("dq.poll() = " + dq.poll());
    System.out.println("dq.poll() = " + dq.poll());
    System.out.println("dq.poll() = " + dq.poll());
    dq.push(6);
    System.out.println("dq = " + dq);
    System.out.println(dq.pop());

    Map<Integer, String> map = new HashMap<>();
    map.put(4, "k");
    map.put(11, "t");
    map.put(3, "h");
    map.put(1, "l");
    System.out.println("map = " + map);
    // 해시값이 integer라서 순서가 보장되느넫, 원래는 안되는게 맞음?
    Set<Integer> keys = map.keySet();
    System.out.println("keys = " + keys);
    // keySet - 키만 출력함
    
    for(Integer ii : keys){
      System.out.println("ii = " + ii);
    } // 키셋 만들어서 keys에 저장하고 키만 이렇게 출력하기도.


  }
}
