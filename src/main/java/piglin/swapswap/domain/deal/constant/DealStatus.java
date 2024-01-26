package piglin.swapswap.domain.deal.constant;

import lombok.Getter;

@Getter
public enum DealStatus {
    REQUESTED(DealName.REQUESTED),
    DEALING(DealName.DEALING),
    COMPLETED(DealName.COMPLETED);

    private final String name;

    DealStatus(String name) {
        this.name = name;
    }

    public static class DealName {

        public static final String REQUESTED = "요청 중";
        public static final String DEALING = "거래 진행 중";
        public static final String COMPLETED = "거래 완료";
    }
}
