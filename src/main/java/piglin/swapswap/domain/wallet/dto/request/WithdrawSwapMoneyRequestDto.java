package piglin.swapswap.domain.wallet.dto.request;

import jakarta.validation.constraints.Positive;

public record WithdrawSwapMoneyRequestDto(@Positive Long swapMoney) {

}
