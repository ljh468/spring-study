package hello.core.singleton;

public class StatefulService {

  // 상태를 유지하는 필드
  // private int price;

  // 상태를 유지하는 코드
  // public void order(String name, int price) {
  //   System.out.println("name = " + name + " price = " + price);
  //   this.price = price;
  // }

  // 무상태를 유지하는 코드
  public int order(String name, int price) {
    System.out.println("name = " + name + " price = " + price);
    return price;
  }

  // 공유필드에서 꺼내주면 안됨
  // public int getPrice() {
  //   return price;
  // }
}
