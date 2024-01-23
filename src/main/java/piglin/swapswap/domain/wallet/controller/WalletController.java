package piglin.swapswap.domain.wallet.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.wallet.dto.request.DepositSwapMoneyRequestDto;
import piglin.swapswap.domain.wallet.dto.request.WithdrawSwapMoneyRequestDto;
import piglin.swapswap.domain.wallet.service.WalletService;
import piglin.swapswap.domain.wallethistory.constant.HistoryType;
import piglin.swapswap.global.annotation.AuthMember;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/deposit/normal")
    public String showNormalDepositSwapMoneyPage(Model model) {

        model.addAttribute("depositSwapMoneyRequestDto",
                new DepositSwapMoneyRequestDto(null));

        return "wallet/depositSwapMoney";
    }

    @PostMapping("/deposit/normal")
    public String normalDepositSwapMoney(
            @Valid @ModelAttribute("depositSwapMoneyRequestDto")
            DepositSwapMoneyRequestDto depositSwapMoneyRequestDto,
            @AuthMember Member member) {

        walletService.depositSwapMoney(depositSwapMoneyRequestDto.swapMoney(),
                HistoryType.NORMAL_DEPOSIT,
                member.getId());

        return "redirect:/members/swap-money";
    }

    @GetMapping("/withdraw/normal")
    public String showNormalWithdrawSwapMoneyPage(Model model) {

        model.addAttribute("withdrawSwapMoneyRequestDto", new WithdrawSwapMoneyRequestDto(null));

        return "wallet/withdrawSwapMoney";
    }

    @PostMapping("/withdraw/normal")
    public String normalWithdrawSwapMoney(
            @Valid @ModelAttribute("withdrawSwapMoneyRequestDto")
            WithdrawSwapMoneyRequestDto withdrawSwapMoneyRequestDto,
            @AuthMember Member member) {

        if (withdrawSwapMoneyRequestDto.swapMoney() != null) {
            walletService.withdrawSwapMoney(withdrawSwapMoneyRequestDto.swapMoney(),
                    HistoryType.NORMAL_WITHDRAW,
                    member.getId());
        }

        return "redirect:/members/swap-money";
    }
}
