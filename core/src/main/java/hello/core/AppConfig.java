package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class  AppConfig {

  // @Bean -> memberService -> new MemoryMemberRepository()
  // @Bean -> orderService -> new MemoryMemberRepository() -> new RateDiscountPolicy()
  // new MemoryMemberRepository를 중복으로 생성되어 싱글톤이 깨지는 것처럼 보임
  // 스프링 컨테이너는 이 문제를 어떻게 해결할까?

  // call AppConfig.memberService
  // call AppConfig.memberRepository
  // call AppConfig.orderService
  // call AppConfig.discountPolicy
  // 스프링 컨테이너는 싱글톤을 보장해주고 있다.
  // @Configuration과 바이트코드 조작의 마법
  @Bean
  public MemberService memberService() {
    System.out.println("call AppConfig.memberService");
    return new MemberServiceImpl(memberRepository());
  }

  @Bean
  public OrderService orderService() {
    System.out.println("call AppConfig.orderService");
    return new OrderServiceImpl(memberRepository(),
                                discountPolicy());
  }

  @Bean
  public MemberRepository memberRepository() {
    System.out.println("call AppConfig.memberRepository");
    return new MemoryMemberRepository();
  }

  @Bean
  public DiscountPolicy discountPolicy() {

    System.out.println("call AppConfig.discountPolicy");
    // return new FixDiscountPolicy();
    return new RateDiscountPolicy();
  }
}
