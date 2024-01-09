package piglin.swapswap.domain.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import piglin.swapswap.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailAndIsDeletedIsFalse(String Email);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByIdAndIsDeletedIsFalse(Long userId);

    boolean existsByNicknameAndIsDeletedIsFalse(String nickname);


}
