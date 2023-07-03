package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Provider;

@Service
@RequiredArgsConstructor
public class LogDemoService {

  // request scope 이기때문에 MyLogger Bean은 찾을 수가 없다.
  // proxyMode를 이용 할 수 있다.
  private final MyLogger myLogger;

  // private final ObjectProvider<MyLogger> myLoggerProvider;

  public void logic(String id) {
    // MyLogger myLogger = myLoggerProvider.getObject();
    myLogger.log("service id : " + id);
  }
}
