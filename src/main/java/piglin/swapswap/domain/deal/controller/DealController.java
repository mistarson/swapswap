package piglin.swapswap.domain.deal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.service.DealService;

@Controller
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @PostMapping("/deals/{secondMemberId}")
    public String createDeal(@Valid @ModelAttribute("dealCreateRequestDto") DealCreateRequestDto requestDto,
            @PathVariable Long secondMemberId, RedirectAttributes redirectAttributes) {
        Long memberId = 1L;
        Long dealId = dealService.createDeal(memberId, requestDto, secondMemberId);
        redirectAttributes.addAttribute("dealId", dealId);
        System.out.println("dealId = " + dealId);
        return "redirect:/deals/" + secondMemberId;
    }
    @GetMapping("/deals")
    public String createDealForm(Model model) {

    model.addAttribute("dealCreateRequestDto", new DealCreateRequestDto(
        0, 0, null, null));

    return "deal/createDealForm";
    }
}
