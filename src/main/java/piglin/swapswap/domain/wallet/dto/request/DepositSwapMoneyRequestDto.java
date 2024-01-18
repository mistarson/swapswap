package piglin.swapswap.domain.wallet.dto.request;

import jakarta.validation.constraints.Positive;

public record DepositSwapMoneyRequestDto(@Positive Long swapMoney) {

}
