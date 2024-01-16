package piglin.swapswap.domain.deal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.service.DealService;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.service.PostService;
import piglin.swapswap.global.annotation.AuthMember;

@Controller
@RequiredArgsConstructor
@RequestMapping("/deals")
public class DealController {

    private final DealService dealService;
    private final PostService postService;

    @PostMapping("/{secondMemberId}")
    public String createDeal(@Valid @RequestBody DealCreateRequestDto requestDto,
            @PathVariable Long secondMemberId, RedirectAttributes redirectAttributes,
            @AuthMember Member member) {
        Long dealId = dealService.createDeal(member, requestDto, secondMemberId);
        redirectAttributes.addAttribute("dealId", dealId);
        System.out.println("dealId = " + dealId);
        return "redirect:/test" + secondMemberId;
    }

    @GetMapping("/request")
    public String createDealForm(Model model, @AuthMember Member member, @RequestParam Long memberId,
            @RequestParam String memberName) {
        model.addAttribute("memberId", memberId);
        model.addAttribute("memberName", memberName);
        model.addAttribute("secondMemberId", member.getId());

        model.addAttribute("dealCreateRequestDto", new DealCreateRequestDto(
                0, 0, null, null));

        return "deal/newcreateDealForm";
    }

    @GetMapping("/test")
    public String testForm() {
        return "deal/dealTest";
    }

}
