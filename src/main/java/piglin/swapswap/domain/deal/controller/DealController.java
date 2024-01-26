package piglin.swapswap.domain.deal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.service.DealService;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.global.annotation.AuthMember;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Controller
@RequiredArgsConstructor
@RequestMapping("/deals")
public class DealController {

    private final DealService dealService;

    @ResponseBody
    @PostMapping
        public ResponseEntity<?> createDeal(@AuthMember Member member,
            @Valid @RequestBody DealCreateRequestDto requestDto) {

        Long dealId = dealService.createDeal(member, requestDto);

        return ResponseEntity.ok().body(dealId);
    }

    @GetMapping("/request")
    public String createDealForm(Model model, @AuthMember Member member,
            @RequestParam Long secondMemberId,
            @RequestParam String memberName) {

        if (member.getId().equals(secondMemberId)) {
            throw new BusinessException(ErrorCode.REQUEST_ONLY_DIFFERENT_USER_EXCEPTION);
        }

        model.addAttribute("secondMemberId", secondMemberId);
        model.addAttribute("secondMemberName", memberName);
        model.addAttribute("memberId", member.getId());

        model.addAttribute("dealCreateRequestDto", new DealCreateRequestDto(
                null, null, null, null, null));

        return "deal/dealCreateForm";
    }

}
