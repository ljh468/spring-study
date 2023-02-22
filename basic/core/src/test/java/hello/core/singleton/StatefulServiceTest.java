package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

  @Test
  void statefulServiceSingleton() {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig.class);
    StatefulService statefulService1 = applicationContext.getBean(StatefulService.class);
    StatefulService statefulService2 = applicationContext.getBean(StatefulService.class);

    // ThreadA : A사용자 10000원 주문
    int userAPrice = statefulService1.order("userA", 10000);

    // ThreadB : B사용자 20000원 주문
    int userBPrice = statefulService2.order("userB", 20000);

    // 사용자A 주문 금액 조회
    // 사용자A는 10000원을 기대했지만, 기대와 다르게 20000원 출력
    // statefulService1.getPrice(); X

    // 싱글톤 객체의 상태를 유지하기 때문
    // 공유필드는 조심해야한다! 스프링 빈은 항상 무상태(stateless)로 설계해야함
    // assertThat(statefulService1.getPrice()).isEqualTo(20000);

    // 무상태를 유지
    System.out.println("userAPrice = " + userAPrice);
    assertThat(userAPrice).isEqualTo(10000);
    System.out.println("userBPrice = " + userBPrice);
    assertThat(userBPrice).isEqualTo(20000);
  }

  static class TestConfig {

    @Bean
    public StatefulService statefulService() {
      return new StatefulService();
    }
  }

}