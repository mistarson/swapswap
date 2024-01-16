package piglin.swapswap.domain.member.repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByEmailAndIsDeletedIsFalse(String Email);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByIdAndIsDeletedIsFalse(Long userId);

    boolean existsByNicknameAndIsDeletedIsFalse(String nickname);

    List<Member> findByIsDeletedAndModifiedTimeBefore(boolean b, LocalDateTime fourTeenDaysAgo);
}
