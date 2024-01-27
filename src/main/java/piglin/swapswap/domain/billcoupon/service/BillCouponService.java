package piglin.swapswap.domain.billcoupon.service;

import java.util.List;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.billcoupon.dto.BillCouponResponseDto;

public interface BillCouponService {

    List<BillCouponResponseDto>  getBillCouponDtoList(Bill bill);
}
