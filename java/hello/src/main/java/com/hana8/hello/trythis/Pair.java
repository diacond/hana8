package com.hana8.hello.trythis;

public class Pair<T, U> {
  private T first;
  private U second;

  public Pair(T first, U second) {
    this.first = first;
    this.second = second;
  }

  public T getFirst() { return first; }
  public void setFirst(T first) { this.first = first; }

  public U getSecond() { return second; }
  public void setSecond(U second) { this.second = second; }

  // 제네릭 타입 반전, 새 객체 생성
  public Pair<U, T> swap() {
    return new Pair<>(this.second, this.first);
  }

  @Override
  public String toString() {
    return "Pair{first=" + first + ", second=" + second + "}";
  }
}
