package piglin.swapswap.domain.deal.constant;

import lombok.Getter;

@Getter
public enum DealStatus {
    REQUESTED("요청 상태"), DEALING("거래 중"), COMPLTIED("거래 완료");

    private final String name;

    DealStatus(String name) {
        this.name = name;
    }
}

