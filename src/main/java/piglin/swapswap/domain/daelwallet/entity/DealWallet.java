package piglin.swapswap.domain.daelwallet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import piglin.swapswap.domain.common.BaseTime;
import piglin.swapswap.domain.deal.entity.Deal;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DealWallet extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long firstSwapMoney;

    @Column
    private Long secondSwapMoney;

    @OneToOne(optional = false)
    @JoinColumn(name = "deal_id", nullable = false)
    private Deal deal;

    public void updateFirstSwapMoney(Long swapMoney) {
        this.firstSwapMoney = swapMoney;
    }

    public void updateSecondSwapMoney(Long swapMoney) {
        this.secondSwapMoney = swapMoney;
    }
}
