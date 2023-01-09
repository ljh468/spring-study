package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

  // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
  // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();


  // 인터페이스에만 의존 (DIP 원칙 ok.!!)
  private MemberRepository memberRepository;

  private DiscountPolicy discountPolicy;

  // 필드 주입 (외부에서 사용하기 어렵기때문에 사용하지 말것, 테스트시에만 사용)
  // @Autowired private MemberRepository memberRepository;
  // @Autowired private DiscountPolicy discountPolicy;


  // 생성자 주입
  @Autowired
  public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }

  // setter 주입
  // @Autowired(required = false) // 선택적으로 가능 (필수가 아니라는 설정)
  // public void setMemberRepository(MemberRepository memberRepository) {
  //   this.memberRepository = memberRepository;
  // }
  //
  // @Autowired
  // public void setDiscountPolicy(DiscountPolicy discountPolicy) {
  //   this.discountPolicy = discountPolicy;
  // }

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);
    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

  // 테스트 용
  public MemberRepository getMemberRepository() {
    return memberRepository;
  }
}
