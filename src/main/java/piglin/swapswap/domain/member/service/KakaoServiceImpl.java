package piglin.swapswap.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import piglin.swapswap.domain.member.dto.SocialUserInfo;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.mapper.MemberMapper;
import piglin.swapswap.domain.member.repository.MemberRepository;
import piglin.swapswap.domain.wallet.entity.Wallet;
import piglin.swapswap.domain.wallet.repository.WalletRepository;
import piglin.swapswap.global.jwt.JwtUtil;

@Service
@RequiredArgsConstructor
@Log4j2
public class KakaoServiceImpl implements SocialService {

    private final WalletRepository walletRepository;
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    @Transactional
    public String kakaoLogin(String code) throws Exception {

        String accessToken = getToken(code);
        SocialUserInfo kakaoUserInfo = getUser(accessToken);
        Member kakaoMember = registerUserIfNeeded(kakaoUserInfo);

        return jwtUtil.createToken(kakaoMember.getEmail(), kakaoMember.getRole());
    }

    @Override
    public String getToken(String code) throws JsonProcessingException {

        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "a304535271497a06332e50e9eec191ab");
        body.add("redirect_uri", "http://localhost:8080/login/kakao/callback");
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());

        return jsonNode.get("access_token").asText();
    }

    @Override
    public SocialUserInfo getUser(String accessToken) {

        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = null;
        try {
            jsonNode = new ObjectMapper().readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);

        return SocialUserInfo.createSocialUserInfo(id, nickname, email);
    }

    public Member registerUserIfNeeded(SocialUserInfo kakaoUserInfo) {
        String kakaoEmail = kakaoUserInfo.email();

        return memberRepository.findByEmail(kakaoEmail)
                .map(existingMember -> {
                    if (isWithdrawnMember(existingMember)) {
                        existingMember.reRegisterMember();
                    }
                    return existingMember;
                })
                .orElseGet(() -> {
                    Wallet savedWallet = walletRepository.save(Wallet.builder().money(0L).build());
                    return memberRepository.save(
                            MemberMapper.createMember(kakaoUserInfo, savedWallet));
                });
    }

    public boolean isWithdrawnMember(Member member) {
        return member.getIsDeleted();
    }

}
