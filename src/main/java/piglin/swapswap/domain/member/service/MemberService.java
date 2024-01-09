package piglin.swapswap.domain.member.service;

import piglin.swapswap.domain.member.dto.MemberResponseDto;

public interface MemberService {

    MemberResponseDto getUser(String identifier);
}
