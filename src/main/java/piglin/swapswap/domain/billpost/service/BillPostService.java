package piglin.swapswap.domain.billpost.service;

import java.util.List;
import piglin.swapswap.domain.bill.entity.Bill;

public interface BillPostService {

    void createBillPost(Bill bill, List<Long> postIdList);
}
