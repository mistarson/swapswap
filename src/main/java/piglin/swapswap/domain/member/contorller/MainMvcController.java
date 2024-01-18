package piglin.swapswap.domain.member.contorller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class MainMvcController {

    @GetMapping("/login")
    public String login() {
        return "/member/login";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/myPage")
    public String myPage() {
        return "/member/myPage";
    }

    @GetMapping("/editProfile")
    public String editProfile() {
        return "/member/editProfile";
    }

    @GetMapping("/unregister")
    public String unregister() {
        return "/member/unregister";
    }

    @GetMapping("/error/errorpage")
    public String handleError() {
        return "/error/errorpage";
    }
}
