package piglin.swapswap.domain.member.constant;

import lombok.Getter;

@Getter
public enum AnimalAdjective {
    ACTIVE("활발한"),
    ELEGANT("우아한"),
    LOVABLE("사랑스러운"),
    AGILE("민첩한"),
    CUTE("귀여운"),
    STRONG("튼튼한"),
    COLORFUL("화려한"),
    CHARMING("정겨운"),
    INTELLIGENT("영리한"),
    BRAVE("용감한");

    private final String adjective;

    AnimalAdjective(String adjective) {
        this.adjective = adjective;
    }
}

