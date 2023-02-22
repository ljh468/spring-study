package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {

  @Test
  @DisplayName("스프링 컨테이너가 싱글톤패턴을 보장하는지 확인")
  void configurationTest() {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberServiceImpl memberService = applicationContext.getBean("memberService", MemberServiceImpl.class);
    OrderServiceImpl orderService = applicationContext.getBean("orderService", OrderServiceImpl.class);
    MemoryMemberRepository memberRepository = applicationContext.getBean("memberRepository", MemoryMemberRepository.class);

    MemberRepository memberRepository1 = memberService.getMemberRepository();
    MemberRepository memberRepository2 = orderService.getMemberRepository();
    System.out.println("MemberService -> memberRepository1 = " + memberRepository1);
    System.out.println("OrderService -> memberRepository2 = " + memberRepository2);
    System.out.println("memberRepository = " + memberRepository);

    assertThat(memberRepository1).isSameAs(memberRepository);
    assertThat(memberRepository2).isSameAs(memberRepository);

    // memberRepository 인스턴스는 모두 같은 인스턴스가 공유되어 사용된다.
  }

  @Test
  void configurationDeep() {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    AppConfig bean = applicationContext.getBean(AppConfig.class);
    System.out.println("bean = " + bean.getClass());
    // bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$
  }
}
