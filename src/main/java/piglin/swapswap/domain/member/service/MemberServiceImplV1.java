package piglin.swapswap.domain.member.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.chatroom_member.service.ChatRoomMemberService;
import piglin.swapswap.domain.favorite.service.FavoriteService;
import piglin.swapswap.domain.member.dto.MemberNicknameDto;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.repository.MemberRepository;
import piglin.swapswap.domain.membercoupon.service.MemberCouponService;
import piglin.swapswap.domain.post.entity.Post;
import piglin.swapswap.domain.post.service.PostService;
import piglin.swapswap.domain.wallet.entity.Wallet;
import piglin.swapswap.domain.wallethistory.service.WalletHistoryService;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class MemberServiceImplV1 implements MemberService {

    private final MemberRepository memberRepository;
    private final PostService postService;
    private final MemberCouponService memberCouponService;
    private final WalletHistoryService walletHistoryService;
    private final FavoriteService favoriteService;
    private final ChatRoomMemberService chatRoomMemberService;

    @Transactional
    public void updateNickname(Member member, MemberNicknameDto requestDto) {

        member = memberRepository.findById(member.getId()).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION)
        );

        if (memberRepository.existsByNicknameAndIsDeletedIsFalse(requestDto.nickname())) {
            throw new BusinessException(ErrorCode.ALREADY_EXIST_USER_NAME_EXCEPTION);
        }

        member.updateMember(requestDto.nickname());
    }


    @Transactional
    public void deleteMember(Member loginMember) {

        Member member = getMemberWithWallet(loginMember.getId());
        Wallet wallet = member.getWallet();

        member.deleteMember();
        wallet.deleteWallet();

        walletHistoryService.deleteAllWalletHistoriesByWallet(member.getWallet());



        chatRoomMemberService.deleteAllChatroomByMember(loginMember);

        memberCouponService.deleteAllMemberCouponByMember(loginMember);

        favoriteService.deleteAllFavoriteByMember(loginMember);

        List<Post> post = postService.findByMemberId(loginMember.getId());

        favoriteService.deleteAllFavoriteByPostList(post);

        postService.deleteAllPostByMember(loginMember);

    }

    @Override
    @Transactional
    public Long getMySwapMoney(Long memberId) {

        Member member = getMemberWithWallet(memberId);

        return member.getWallet().getSwapMoney();
    }

    @Override
    public Member getMemberWithWallet(Long memberId) {
        return memberRepository.findByIdWithWallet(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
    }

    @Override
    public Member getMember(Long memberId) {

        return memberRepository.findById(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
    }

    @Override
    public boolean checkNicknameExists(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Override
    public List<Member> getMembers(List<Long> memberIds) {
        return memberRepository.findByIdIn(memberIds);
    }
}