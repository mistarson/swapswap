package piglin.swapswap.domain.member.contorller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class MainMvcController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/userinfo")
    public String userinfo() {
        return "userinfo";
    }

}
