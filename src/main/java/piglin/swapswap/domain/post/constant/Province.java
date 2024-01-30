package piglin.swapswap.domain.post.constant;

import lombok.Getter;

@Getter
public enum Province {
    NONE("기타"),
    GYEONGGI("경기도"),
    SOUTH_CHUNGCHEONG("충청남도"),
    NORTH_GYEONGSANG("경상북도"),
    SOUTH_GYEONGSANG("경상남도"),
    NORTH_CHUNGCHEONG("충청북도"),
    GANGWON("강원도"),
    NORTH_JEOLLA("전라북도"),
    JEJU("제주도"),
    SOUTH_JEOLLA("전라남도");

    private final String name;

    Province(String name) {
        this.name = name;
    }
}