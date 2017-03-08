package spring.aop;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController("/")
public class HomeController {

    static int counter = 0;

    @RequestMapping
    public String index() {
        inTry();
        return "OK";
    }


    @RetryOnFailure(attempts = 3, delay = 2, unit = TimeUnit.SECONDS)
    public void inTry() {
        throw new RuntimeException("Exception in try " + ++counter);
    }

}
