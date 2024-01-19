package piglin.swapswap.domain.member.repository;

import java.util.Optional;
import piglin.swapswap.domain.member.entity.Member;

public interface MemberQueryRepository {

    Optional<Member> findByIdWithWallet(Long memberId);

}
