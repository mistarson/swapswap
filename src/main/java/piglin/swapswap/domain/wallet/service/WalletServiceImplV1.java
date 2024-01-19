package piglin.swapswap.domain.wallet.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.service.MemberService;
import piglin.swapswap.domain.wallet.entity.Wallet;
import piglin.swapswap.domain.wallet.repository.WalletRepository;
import piglin.swapswap.domain.wallethistory.constant.HistoryType;
import piglin.swapswap.domain.wallethistory.entity.WalletHistory;
import piglin.swapswap.domain.wallethistory.mapper.WalletHistoryMapper;
import piglin.swapswap.domain.wallethistory.service.WalletHistoryService;
import piglin.swapswap.global.exception.common.ErrorCode;
import piglin.swapswap.global.exception.wallet.InvalidWithdrawException;

@Service
@RequiredArgsConstructor
public class WalletServiceImplV1 implements WalletService {

    private final WalletRepository walletRepository;

    private final WalletHistoryService walletHistoryService;

    private final MemberService memberService;


    @Override
    public Wallet createWallet() {

        return walletRepository.save(Wallet.createWallet());
    }

    @Override
    @Transactional
    public void normalDepositSwapMoney(Long depositSwapMoney, Long memberId) {

        Member member = memberService.getMemberWithWallet(memberId);

        Wallet wallet = member.getWallet();
        wallet.depositSwapMoney(depositSwapMoney);

        recordWalletHistory(depositSwapMoney, HistoryType.NORMAL_DEPOSIT);
    }

    @Override
    @Transactional
    public void normalWithdrawSwapMoney(Long withdrawSwapMoney, Long memberId) {

        Member member = memberService.getMemberWithWallet(memberId);

        Wallet wallet = member.getWallet();
        if (impossibleWithdrawSwapMoney(wallet, withdrawSwapMoney)) {
            throw new InvalidWithdrawException(ErrorCode.LACK_OF_SWAP_MONEY_EXCEPTION);
        }
        wallet.withdrawSwapMoney(withdrawSwapMoney);

        recordWalletHistory(withdrawSwapMoney, HistoryType.NORMAL_WITHDRAW);
    }

    private boolean impossibleWithdrawSwapMoney(Wallet wallet, Long withdrawSwapMoney) {

        return wallet.getSwapMoney() < withdrawSwapMoney;
    }

    private void recordWalletHistory(Long swapMoney, HistoryType historyType) {

        WalletHistory walletHistory = WalletHistoryMapper.createWalletHistory(swapMoney,
                historyType);
        walletHistoryService.recordWalletHistory(walletHistory);
    }
}
