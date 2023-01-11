package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest {

  @Test
  void prototypeFind() {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(PrototypeBean.class);
    PrototypeBean prototypeBean1 = applicationContext.getBean(PrototypeBean.class);
    prototypeBean1.addCount();
    assertThat(prototypeBean1.getCount()).isEqualTo(1);

    PrototypeBean prototypeBean2 = applicationContext.getBean(PrototypeBean.class);
    prototypeBean2.addCount();
    assertThat(prototypeBean2.getCount()).isEqualTo(1);

  }

  @Test
  void singletonClientUsePrototype() {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
    ClientBean clientBean1 = applicationContext.getBean(ClientBean.class);
    int count1 = clientBean1.logic();
    assertThat(count1).isEqualTo(1);

    ClientBean clientBean2 = applicationContext.getBean(ClientBean.class);
    int count2 = clientBean2.logic();
    assertThat(count2).isEqualTo(1);
  }

  @Scope("singleton")
  static class ClientBean {

    // 1. 생성시점에 주입되어 내부에 계속 가지고 있음
    // private final PrototypeBean prototypeBean;
    //
    // @Autowired
    // public ClientBean(PrototypeBean prototypeBean) {
    //   this.prototypeBean = prototypeBean;
    // }
    // public int logic() {
    //   prototypeBean.addCount();
    //   return prototypeBean.getCount();
    // }

    // DI 컨테이너에서 빈을 탐색 (DL)
    // 2. DL을 이용해서 항상 새로운 프로토타입 빈이 생성되는 것을 확인
    // @Autowired
    // private ObjectProvider<PrototypeBean> prototypeBeanProvider;
    //
    // public int logic() {
    //   PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
    //   prototypeBean.addCount();
    //   return prototypeBean.getCount();
    // }

    // 3. 스프링에 의존적이지 않은 JSR-330 Provider 활용
    // 자바 표준이므로 스프링이 아닌 다른 컨테이너에서도 사용할 수 있다.
    @Autowired
    private Provider<PrototypeBean> prototypeBeanProvider;

    public int logic() {
      PrototypeBean prototypeBean = prototypeBeanProvider.get();
      prototypeBean.addCount();
      return prototypeBean.getCount();
    }
  }

  @Scope("prototype")
  static class PrototypeBean {

    private int count =0;

    public void addCount() {
      count++;
    }

    public int getCount() {
      return count;
    }

    @PostConstruct
    public void init() {
      System.out.println("PrototypeBean.init" + this);
    }

    @PreDestroy
    public void destroy() {
      System.out.println("PrototypeBean.destroy");
    }
  }
}
