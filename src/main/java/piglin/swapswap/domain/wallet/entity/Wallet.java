package piglin.swapswap.domain.wallet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import piglin.swapswap.domain.common.BaseTime;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wallet extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long swapMoney;

    @Column(nullable = false)
    private boolean isDeleted;

    public void depositSwapMoney(Long depositSwapMoney) {

        swapMoney += depositSwapMoney;
    }

    public void withdrawSwapMoney(Long withdrawSwapMoney) {

        swapMoney -= withdrawSwapMoney;
    }

    public static Wallet createWallet() {
        return Wallet.builder()
                .swapMoney(0L)
                .isDeleted(false)
                .build();
    }
}
