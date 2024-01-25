package piglin.swapswap.domain.member.repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import piglin.swapswap.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {

    Optional<Member> findByEmailAndIsDeletedIsFalse(String Email);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByIdAndIsDeletedIsFalse(Long userId);

    boolean existsByNicknameAndIsDeletedIsFalse(String nickname);

    void deleteAllByIsDeletedIsTrueAndModifiedTimeBefore(LocalDateTime fourTeenDaysAgo);

    boolean existsByIdAndIsDeletedIsFalse(Long memberId);

    boolean existsByNickname(String nickname);

    Optional<Member> findByNickname(String nickname);

}
