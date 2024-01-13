package piglin.swapswap.domain.coupon.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import piglin.swapswap.domain.coupon.constant.CouponType;
import piglin.swapswap.domain.coupon.dto.request.CouponCreateRequestDto;
import piglin.swapswap.domain.coupon.entity.Coupon;
import piglin.swapswap.domain.coupon.repository.CouponRepository;
import piglin.swapswap.domain.member.constant.MemberRoleEnum;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.repository.MemberRepository;
import piglin.swapswap.domain.wallet.entity.Wallet;
import piglin.swapswap.domain.wallet.repository.WalletRepository;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    CouponService couponService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    CouponRepository couponRepository;

    static int COUPON_COUNT = 5;
    static Long couponId;
    static Member member;
    static Wallet wallet;

    @BeforeEach
    void init() {

        CouponCreateRequestDto requestDto = CouponCreateRequestDto.builder()
                .couponName("수수료 할인 쿠폰")
                .couponType(CouponType.FEE)
                .expiredTime(LocalDateTime.now().plusHours(1L))
                .couponCount(COUPON_COUNT)
                .build();

        couponId = couponService.createCoupon(requestDto);

        wallet = Wallet.builder()
                .money(1L)
                .build();

        walletRepository.save(wallet);

        member = Member.builder()
                .email("yeowuli2@naver.com")
                .nickname("손창현")
                .role(MemberRoleEnum.USER)
                .isDeleted(false)
                .wallet(wallet)
                .build();

        memberRepository.save(member);

    }

    @Test
    @DisplayName("잔여 쿠폰이 5장이고 5명의 유저가 동시에 쿠폰을 발급받으려는 상황")
    void issueCoupon() throws InterruptedException {

        //given
        final int PARTICIPATION_PEOPLE = 5;

        CountDownLatch countDownLatch = new CountDownLatch(PARTICIPATION_PEOPLE);
        List<ParticipateWorker> workers = Stream.generate(
                        () -> new ParticipateWorker(member, countDownLatch))
                .limit(PARTICIPATION_PEOPLE)
                .toList();

        // when
        List<Thread> threads = workers.stream().map(Thread::new).toList();

        threads.forEach(Thread::start);
        countDownLatch.await();

        // then
        Coupon coupon = couponRepository.findById(couponId).get();
        System.out.println("최종 쿠폰 잔여 갯수: " + coupon.getCount());
    }

    private class ParticipateWorker implements Runnable {

        private Member member;
        private CountDownLatch countDownLatch;

        public ParticipateWorker(Member member, CountDownLatch countDownLatch) {
            this.member = member;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            couponService.issueEventCoupon(couponId, member);
            countDownLatch.countDown();
        }
    }

}