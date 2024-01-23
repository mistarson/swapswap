package piglin.swapswap.domain.deal.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import piglin.swapswap.domain.common.BaseTime;
import piglin.swapswap.domain.daelwallet.entity.DealWallet;
import piglin.swapswap.domain.deal.constant.DealStatus;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deal extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private DealStatus dealStatus;

    @Column(nullable = false)
    private Long firstUserId;

    @Column(nullable = false)
    private Long secondUserId;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Map<Integer, Long> firstPostIdList;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Map<Integer, Long> secondPostIdList;

    @Column(nullable = false)
    private Long firstExtraFee;

    @Column(nullable = false)
    private Long secondExtraFee;

    @Column(nullable = false)
    private Boolean firstAllow;

    @Column(nullable = false)
    private Boolean secondAllow;

    @Column(nullable = false)
    private Boolean firstTake;

    @Column(nullable = false)
    private Boolean secondTake;

    @Column(nullable = false)
    private Boolean isFirstSwapMoneyUsed;

    @Column(nullable = false)
    private Boolean isSecondSwapMoneyUsed;

    @Column
    private LocalDateTime completedDealTime;

    public void updateDealFirst(Long firstExtraFee, Map<Integer, Long> firstPostIdMap) {

        this.firstExtraFee = firstExtraFee;
        this.firstPostIdList = firstPostIdMap;
        this.firstAllow = false;
        this.secondAllow = false;
    }

    public void updateDealSecond(Long secondExtraFee, Map<Integer, Long> secondPostIdMap) {

        this.secondExtraFee = secondExtraFee;
        this.secondPostIdList = secondPostIdMap;
        this.firstAllow = false;
        this.secondAllow = false;
    }

    public void updateDealFirstMemberAllow() {

        firstAllow = !firstAllow;
    }

    public void updateDealSecondMemberAllow() {

        secondAllow = !secondAllow;
    }

    public void updateDealFirstMemberTake() {

        firstTake = true;
    }

    public void updateDealSecondMemberTake() {

        secondTake = true;
    }

    public void updateDealFirstMemberSwapMoneyUsing() {

        isFirstSwapMoneyUsed = !isFirstSwapMoneyUsed;
    }

    public void updateDealSecondMemberSwapMoneyUsing() {

        isSecondSwapMoneyUsed = !isSecondSwapMoneyUsed;
    }

    public void updateDealStatus(DealStatus dealStatus) {

        if (dealStatus.equals(DealStatus.COMPLETED)) {
            this.completedDealTime = LocalDateTime.now();
        }

        this.dealStatus = dealStatus;
    }
}

