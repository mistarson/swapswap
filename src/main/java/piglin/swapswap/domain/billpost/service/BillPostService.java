package piglin.swapswap.domain.billpost.service;

import java.util.List;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.post.entity.Post;

public interface BillPostService {

    void createBillPost(Bill bill, List<Long> postIdList);
}
