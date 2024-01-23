package piglin.swapswap.domain.member.constant;

import lombok.Getter;

@Getter
public enum AnimalName {
    CAT("고양이"),
    DOG("개"),
    ELEPHANT("코끼리"),
    PANDA("판다"),
    TIGER("호랑이"),
    ZEBRA("얼룩말"),
    DOLPHIN("돌고래"),
    LION("사자"),
    OWL("올빼미"),
    PENGUIN("펭귄");

    private final String name;

    AnimalName(String name) {
        this.name = name;
    }
}