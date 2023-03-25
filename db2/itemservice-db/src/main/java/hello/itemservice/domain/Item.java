package hello.itemservice.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity //JPA 를 사용하는 객체
public class Item {

    @Id //테이블의 PK와 해당 필드를 매핑
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Identity 방식으로 PK값 생성
    private Long id;

    @Column(name = "item_name", length = 10)
    private String itemName;
    private Integer price;
    private Integer quantity;

    //기본 생성자는 필수
    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
