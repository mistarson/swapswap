package piglin.swapswap.domain.deal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.deal.dto.request.DealCreateRequestDto;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.deal.mapper.DealMapper;
import piglin.swapswap.domain.deal.repository.DealRepository;
import piglin.swapswap.domain.member.MemberRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealRepository dealRepository;
    private final MemberRepository memberRepository;

     public void createDeal(Long memberId, DealCreateRequestDto requestDto, Long secondMemberId) {

       Member member = getMember(memberId);
       Member secondMember = getMember(secondMemberId);

       Boolean firstAllow = true; Boolean firstTake = false;
       Boolean secondAllow = false; Boolean secondTake = false;

       DealStatus dealStatus = checkDeal(firstAllow, secondAllow);

       Long firstUserId = memberId;
       Long secondUserId = secondMemberId;
       List<Long> firstPostIdList = requestDto.firstPostIdList();
       List<Long> secondPostIdList = requestDto.secondPostIdList();
       Map<Integer, Object> firstPostIdListMap = new HashMap<>();
       Map<Integer, Object> secondPostIdListMap = new HashMap<>();

       for(int i=0; i< firstPostIdList.size(); i++) {
         firstPostIdListMap.put(i, firstPostIdList.get(i));
       }

       for (int i=0; i< secondPostIdList.size(); i++) {
         secondPostIdListMap.put(i,secondPostIdList.get(i));
       }

       Deal deal = DealMapper.createDeal(requestDto, firstPostIdListMap, secondPostIdListMap,
           dealStatus,firstAllow, secondAllow, firstTake, secondTake, firstUserId, secondUserId);

       dealRepository.save(deal);
    }

    private Member getMember(Long memberId) {

       return memberRepository.findById(memberId).orElseThrow(
           () -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION)
       );
    }

    private DealStatus checkDeal(Boolean firstAllow, Boolean secondAllow) {

       if(firstAllow&&secondAllow) {

         return DealStatus.DEALING;
       }
       else {

        return DealStatus.ALLOW;
       }

    }
}
