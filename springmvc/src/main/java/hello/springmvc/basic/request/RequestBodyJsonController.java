package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @PostMapping("/request-body-json-v1")
  public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

    ServletInputStream inputStream = request.getInputStream();
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
    log.info("messageBody={}", messageBody);

    HelloData data = objectMapper.readValue(messageBody, HelloData.class);
    log.info("date={}", data);

    response.getWriter().write("ok");
  }
}
