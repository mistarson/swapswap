package piglin.swapswap.domain.billcoupon.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.billcoupon.dto.BillCouponResponseDto;
import piglin.swapswap.domain.billcoupon.mapper.BillCouponMapper;
import piglin.swapswap.domain.billcoupon.repository.BillCouponRepository;
import piglin.swapswap.domain.membercoupon.entity.MemberCoupon;

@Service
@RequiredArgsConstructor
public class BillCouponServiceImplV1 implements BillCouponService {

    private final BillCouponRepository billCouponRepository;

    @Override
    public List<BillCouponResponseDto> getBillCouponDtoList(Bill bill) {

        List<MemberCoupon> memberCouponList = billCouponRepository.findMemberCouponFromBillCouponByBill(bill);

        return BillCouponMapper.toBillCouponResponseDtoList(memberCouponList);
    }

}
