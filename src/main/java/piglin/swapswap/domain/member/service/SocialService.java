package piglin.swapswap.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import piglin.swapswap.domain.member.dto.SocialUserInfo;
import piglin.swapswap.domain.member.entity.Member;

public interface SocialService {

    String getToken(String code) throws JsonProcessingException;

    SocialUserInfo getUser(String identifier);

    Member registerUserIfNeeded(SocialUserInfo kakaoUserInfo);

}
