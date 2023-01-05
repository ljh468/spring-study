package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
  public static void main(String[] args) {
    // AppConfig appConfig = new AppConfig();
    // MemberService memberService = appConfig.memberService();

    // 스프링은 ApplicationContext가 관리됨
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
    // 기본적으로 @Bean이라 적힌 메서드는 ApplicationContext으로 들어갈때 메서드이름으로 등록된다. (등록된 객체를 스프링빈 이라고함.)
    // 등록된이름, 반환타입

    Member member = new Member(1L, "memberA", Grade.VIP);
    memberService.join(member);


    Member findMember = memberService.findMember(1L);
    System.out.println("new member = " + member.getName());
    System.out.println("find Member = " + findMember.getName());
  }
}
