package piglin.swapswap.domain.member.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainMvcController {

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/error/errorpage")
    public String handleError() {
        return "error/errorpage";
    }

    @GetMapping("/error/accessdenied")
    public String handleAccessError() {
        return "error/accessdenied";
    }

}


