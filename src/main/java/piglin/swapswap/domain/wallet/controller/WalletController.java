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
import piglin.swapswap.domain.wallet.service.WalletService;
import piglin.swapswap.global.annotation.AuthMember;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/deposit")
    public String showDepositSwapMoneyPage(Model model) {

        model.addAttribute("depositSwapMoneyRequestDto",
                new DepositSwapMoneyRequestDto(null));

        return "wallet/depositSwapMoney";
    }

    @PostMapping("/deposit")
    public String depositSwapMoney(
            @Valid @ModelAttribute("depositSwapMoneyRequestDto")
            DepositSwapMoneyRequestDto depositSwapMoneyRequestDto,
            @AuthMember Member member) {

        walletService.depositSwapMoney(depositSwapMoneyRequestDto.swapMoney(), member.getId());

        return "redirect:/members/swap-money";
    }
}
