package piglin.swapswap.global.scheduler;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final MemberRepository memberRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredMember() {
        LocalDateTime fourTeenDaysAgo = LocalDateTime.now().minusDays(14);

        List<Member> memberListToDelete = memberRepository.findByIsDeletedAndModifiedDateBefore(true, fourTeenDaysAgo);

        memberRepository.deleteAll(memberListToDelete);

    }
}
