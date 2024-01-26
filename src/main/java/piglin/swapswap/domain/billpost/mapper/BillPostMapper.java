package piglin.swapswap.domain.billpost.mapper;

import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.billpost.entity.BillPost;
import piglin.swapswap.domain.post.entity.Post;

public class BillPostMapper {

    public static BillPost createBillPost(Bill bill, Post post) {

        return BillPost.builder()
                .bill(bill)
                .post(post)
                .build();
    }
}
