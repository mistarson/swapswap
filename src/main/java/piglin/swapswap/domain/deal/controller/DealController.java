package piglin.swapswap.domain.deal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.service.DealService;

@Controller
@RequestMapping("/api/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;
    @PostMapping("{secondMemberId}")
      void createDeal(@RequestBody DealCreateRequestDto requestDto,
                      @PathVariable Long secondMemberId
                                    //  @AuthenticationPrincipal UserDetailsImpl userDetails
                                    ){
       Long memberId=1L;
       dealService.createDeal(memberId, requestDto, secondMemberId);
    }
}
