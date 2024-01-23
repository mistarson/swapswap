package piglin.swapswap.domain.deal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.dto.request.DealUpdateRequestDto;
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

    @ResponseBody
    @PostMapping
        public ResponseEntity<?> createDeal(@Valid @RequestBody DealCreateRequestDto requestDto,
            @AuthMember Member member) {

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
                0L, 0L, null, null, null));

        return "deal/dealCreateForm";
    }

    @GetMapping("/request/list")
    public String  getRequestDealList(
            @AuthMember Member member,
            Model model) {

        model.addAttribute("dealGetListResponseDto", dealService.getMyRequestDealList(member.getId()));
        model.addAttribute("memberNickname", member.getNickname());

        return "deal/dealRequestDealListForm";
    }

    @GetMapping("response/list")
    public String getResponseDealList(
            @AuthMember Member member,
            Model model) {

        model.addAttribute("dealGetListResponseDto", dealService.getMyResponseDealList(member.getId()));
        model.addAttribute("memberNickname", member.getNickname());

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

    @PutMapping("/{dealId}/member/{memberId}")
    public ResponseEntity<?> updateDeal(@Valid @AuthMember Member member, @PathVariable Long dealId,
            @PathVariable Long memberId, @RequestBody DealUpdateRequestDto requestDto){

        dealService.updateDeal(member, dealId, memberId, requestDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{dealId}/member/{memberId}")
    public String showUpdateDealForm(@PathVariable Long dealId,
            @PathVariable Long memberId, Model model) {

        dealService.checkDeal(dealId);
        model.addAttribute("memberId", memberId);
        model.addAttribute("dealId",dealId);
        model.addAttribute("dealUpdateRequestDto", new DealUpdateRequestDto(
                0L, null));

        return "deal/dealUpdateForm";
    }

    @ResponseBody
    @PatchMapping("/{dealId}/allow")
    public ResponseEntity<?> updateDealAllow(@PathVariable Long dealId, @AuthMember Member member) {

        dealService.updateDealAllow(dealId, member);

        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @PatchMapping("/{dealId}/take")
    public ResponseEntity<?> takeDeal(@PathVariable Long dealId, @AuthMember Member member) {

        dealService.takeDeal(dealId, member);

        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @PatchMapping("/{dealId}/use")
    public ResponseEntity<?> updateDealSwapMoneyIsUsing(@PathVariable Long dealId, @AuthMember Member member) {

        dealService.updateDealSwapMoneyIsUsing(dealId, member);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public String getDealHistory(@AuthMember Member member,
    Model model) {

        model.addAttribute("dealHistoryResponseDto",dealService.getDealHistoryList(member.getId()));
        model.addAttribute("memberNickname", member.getNickname());

        return "deal/dealHistory";
    }
}
