package piglin.swapswap.domain.deal.dto.response;

import java.util.List;
import lombok.Builder;
import piglin.swapswap.domain.billcoupon.dto.BillCouponResponseDto;
import piglin.swapswap.domain.billpost.dto.BillPostResponseDto;
import piglin.swapswap.domain.deal.constant.DealStatus;

@Builder
public record DealDetailResponseDto(

    Long id,

    DealStatus dealStatus,

    Long firstMemberId,

    Long secondMemberId,

    String firstMemberNickname,

    String secondMemberNickname,

    List<BillPostResponseDto> firstDealPostList,

    List<BillPostResponseDto> secondDealPostList,

    Long firstExtraFee,

    Long secondExtraFee,

    Boolean firstAllow,

    Boolean secondAllow,

    Boolean firstTake,

    Boolean secondTake,

    Boolean useSwapMoneyFirstMember,

    Boolean useSwapMoneySecondMember,

    Long firstMemberCommission,

    Long secondMemberCommission,

    List<BillCouponResponseDto> firstCouponList,

    List<BillCouponResponseDto> secondCouponList
    ) {
}
