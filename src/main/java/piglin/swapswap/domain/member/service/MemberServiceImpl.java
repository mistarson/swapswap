package piglin.swapswap.domain.member.service;

import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.member.dto.MemberNicknameDto;
import piglin.swapswap.domain.member.dto.MemberResponseDto;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.repository.MemberRepository;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResponseDto getUser(String identifier) {

        long userId = Integer.parseInt(identifier);
        var user = this.memberRepository.findById(userId)
                .orElseThrow(
                        () -> new NoSuchElementException("user id : " + userId + " not exist."));

        return MemberResponseDto.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }
}
