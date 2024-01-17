package piglin.swapswap.domain.member.constant;

import lombok.Getter;

@Getter
public enum MemberRole {
    USER(Authority.USER),
    ADMIN(Authority.ADMIN);

    private final String authority;

    MemberRole(String authority) {
        if (authority == null) {
            authority = Authority.USER;
        }
        this.authority = authority;
    }


    public static class Authority {

        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";

    }
}