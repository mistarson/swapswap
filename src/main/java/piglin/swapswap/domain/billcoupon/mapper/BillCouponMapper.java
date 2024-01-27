package piglin.swapswap.domain.billcoupon.mapper;

import java.util.List;
import piglin.swapswap.domain.billcoupon.dto.BillCouponResponseDto;
import piglin.swapswap.domain.membercoupon.entity.MemberCoupon;

public class BillCouponMapper {

    public static List<BillCouponResponseDto> toBillCouponResponseDtoList(
            List<MemberCoupon> memberCouponList) {

        return memberCouponList.stream().map(memberCoupon -> BillCouponResponseDto.builder()
                .name(memberCoupon.getName())
                .build()).toList();
    }

}
