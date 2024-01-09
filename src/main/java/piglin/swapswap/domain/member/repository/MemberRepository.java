package piglin.swapswap.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
