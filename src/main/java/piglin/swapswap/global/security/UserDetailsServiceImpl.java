package piglin.swapswap.global.security;

import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.repository.MemberRepository;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public UserDetails loadUserByUserId(long userId) throws NoSuchElementException {
        Member user = memberRepository.findById(userId)
                .orElseThrow(
                        () -> new NoSuchElementException("user id : " + userId + " not exist"));

        return new UserDetailsImpl(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

        return new UserDetailsImpl(member);
    }
}
