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
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.dto.response.DealDetailResponseDto;
import piglin.swapswap.domain.deal.service.DealService;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.service.PostService;
import piglin.swapswap.global.annotation.AuthMember;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Controller
@RequiredArgsConstructor
@RequestMapping("/deals")
public class DealController {

    private final DealService dealService;
    private final PostService postService;

    @PostMapping
    public String createDeal(@Valid @RequestBody DealCreateRequestDto requestDto,
            @AuthMember Member member) {

        Long dealId = dealService.createDeal(member, requestDto);

        return "redirect:/deals/" + dealId;
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
                0, 0, null, null, null));

        return "deal/dealCreateForm";
    }

    @GetMapping("/request/list")
    public String  getRequestDealList(
            @AuthMember Member member,
            Model model) {

        model.addAttribute("dealGetListResponseDto", dealService.getMyRequestDealList(member));
        model.addAttribute("memberId", member.getId());

        return "deal/dealRequestDealListForm";
    }

    @GetMapping("response/list")
    public String getResponseDealList(
            @AuthMember Member member,
            Model model) {

        model.addAttribute("dealGetListResponseDto", dealService.getMyResponseDealList(member));
        model.addAttribute("memberId", member.getId());

        return "deal/dealResponseDealListForm";
    }

    @GetMapping("/{dealId}")
    public String getDeal(
            @AuthMember Member member, @PathVariable Long dealId,
            Model model) {

        DealDetailResponseDto responseDto = dealService.getDeal(dealId, member);

        model.addAttribute("dealDetailResponseDto", responseDto);
        model.addAttribute("firstMemberPostList", postService.getPostSimpleInfoListByPostIdList(responseDto.firstPostIdList()));
        model.addAttribute("secondMemberPostList", postService.getPostSimpleInfoListByPostIdList(responseDto.secondPostIdList()));
        model.addAttribute("memberId", member.getId());
        return  "deal/dealRequestDeal";
    }
}
