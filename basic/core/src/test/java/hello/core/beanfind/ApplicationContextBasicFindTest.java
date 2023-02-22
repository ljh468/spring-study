package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextBasicFindTest {

  AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("빈 이름으로 조회")
  void finDeanByName() {
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
    assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("이름없이 타입으로만 조회")
  void finDeanByType() {
    MemberService memberService = applicationContext.getBean(MemberService.class);
    assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  // 구현체로 조회시 유연성이 떨어짐
  @Test
  @DisplayName("구체 타입으로 조회")
  void finDeanByName2() {
    MemberService memberService = applicationContext.getBean("memberService", MemberServiceImpl.class);
    assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("빈 이름으로 조회 X")
  void findBeanByNameX() {
    // applicationContext.getBean("xxxxx", MemberService.class);
    assertThrows(NoSuchBeanDefinitionException.class,
                 () -> applicationContext.getBean("xxxxx", MemberService.class));
  }
}
