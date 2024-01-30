package piglin.swapswap.domain.member.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import piglin.swapswap.global.annotation.SwapLog;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImplV1 implements MemberService {

    private final MemberRepository memberRepository;
    private final PostService postService;
    private final MemberCouponService memberCouponService;
    private final WalletHistoryService walletHistoryService;
    private final FavoriteService favoriteService;
    private final ChatRoomMemberService chatRoomMemberService;


    @SwapLog
    @Override
    @Transactional
    public void updateNickname(Member member, MemberNicknameDto requestDto) {

        log.info("\nupdateNickname - memberCurrentNickname: {} | memberNicknameWillBe: {}", member.getNickname(), requestDto.nickname());
        checkMemberExists(member.getId());

        if (memberRepository.existsByNicknameAndIsDeletedIsFalse(requestDto.nickname())) {
            throw new BusinessException(ErrorCode.ALREADY_EXIST_USER_NAME_EXCEPTION);
        }

        member.updateMember(requestDto.nickname());
        log.info("\nmemberChangedNickname: {}", member.getNickname());
    }

    @SwapLog
    @Override
    @Transactional
    public void deleteMember(Member member) {

        log.info("\ndeleteMember - memberId: {} | memberEmail: {}", member.getId(), member.getEmail());
        member = getMemberWithWallet(member.getId());
        log.info("\nmemberWalletId: {}", member.getWallet().getId());
        Wallet wallet = member.getWallet();

        member.deleteMember();
        wallet.deleteWallet();

        walletHistoryService.deleteAllWalletHistoriesByWallet(member.getWallet());

        chatRoomMemberService.deleteAllChatroomByMember(member);

        memberCouponService.deleteAllMemberCouponByMember(member);

        favoriteService.deleteAllFavoriteByMember(member);

        List<Post> post = postService.findByMemberId(member.getId());

        favoriteService.deleteAllFavoriteByPostList(post);

        postService.deleteAllPostByMember(member);

        log.info("\nmemberIsDeleted: {} | walletIsDeleted: {}", member.getIsDeleted(), wallet.isDeleted());
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

        return memberRepository.findByIdAndIsDeletedIsFalse(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION)
        );
    }

    @Override
    public boolean checkNicknameExists(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Override
    public List<Member> getMembers(List<Long> memberIds) {
        return memberRepository.findByIdIn(memberIds);
    }

    public void checkMemberExists(Long memberId) {

        if (!memberRepository.existsByIdAndIsDeletedIsFalse(memberId)) {
           throw new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }
    }
}