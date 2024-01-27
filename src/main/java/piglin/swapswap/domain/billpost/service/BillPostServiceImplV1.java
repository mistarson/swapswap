package piglin.swapswap.domain.billpost.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.billpost.dto.BillPostResponseDto;
import piglin.swapswap.domain.billpost.mapper.BillPostMapper;
import piglin.swapswap.domain.billpost.repository.BillPostRepository;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.post.entity.Post;
import piglin.swapswap.domain.post.service.PostService;
import piglin.swapswap.global.exception.post.AlreadyDealingPostException;

@Service
@RequiredArgsConstructor
public class BillPostServiceImplV1 implements BillPostService{

    private final PostService postService;
    private final BillPostRepository billPostRepository;

    @Override
    public void createBillPost(Bill bill, List<Long> postIdList) {

        for (Long postId : postIdList) {
            Post post = postService.getPost(postId);
            if (!post.getDealStatus().equals(DealStatus.REQUESTED)) {
                throw new AlreadyDealingPostException();
            }

            billPostRepository.save(BillPostMapper.createBillPost(bill, post));
        }
    }

    @Override
    public List<BillPostResponseDto> getBillPostDtoList(Bill bill) {

        List<Post> postList = billPostRepository.findPostFromBillPostByBill(bill);

        return BillPostMapper.toBillPostResponseDto(postList);
    }
}
