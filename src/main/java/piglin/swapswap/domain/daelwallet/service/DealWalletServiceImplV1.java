package piglin.swapswap.domain.daelwallet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piglin.swapswap.domain.daelwallet.entity.DealWallet;
import piglin.swapswap.domain.daelwallet.mapper.DealWalletMapper;
import piglin.swapswap.domain.daelwallet.repository.DealWalletRepository;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.wallet.service.WalletService;
import piglin.swapswap.domain.wallethistory.constant.HistoryType;
import piglin.swapswap.global.exception.dealwallet.DealWalletNotFoundException;

@Service
@RequiredArgsConstructor
public class DealWalletServiceImplV1 implements DealWalletService {

    private final DealWalletRepository dealWalletRepository;
    private final WalletService walletService;

    @Override
    @Transactional
    public void createDealWallet(Deal deal, Member member, Long totalFee) {

        DealWallet dealWallet = DealWalletMapper.setDealWallet(deal, null, null);

        if (deal.getFirstMemberbill().getMember().getId().equals(member.getId())) {
            dealWallet.updateFirstSwapMoney(totalFee);
        }

        if (deal.getSecondMemberbill().getMember().getId().equals(member.getId())) {
            dealWallet.updateSecondSwapMoney(totalFee);
        }

        walletService.withdrawSwapMoney(totalFee, HistoryType.TEMPORARY_WITHDRAW, member.getId());

        dealWalletRepository.save(dealWallet);
    }

    @Override
    @Transactional
    public boolean existDealWalletByDealId(Long dealId) {

        return dealWalletRepository.existsByDealId(dealId);
    }

    @Override
    @Transactional
    public void updateDealWallet(Deal deal, Member member, Long totalFee) {

        DealWallet dealWallet = dealWalletRepository.findByDealId(deal.getId())
                .orElseThrow(DealWalletNotFoundException::new);

        if (deal.getFirstMemberbill().getMember().getId().equals(member.getId())) {
            dealWallet.updateFirstSwapMoney(totalFee);
        }
        if (deal.getSecondMemberbill().getMember().getId().equals(member.getId())) {
            dealWallet.updateSecondSwapMoney(totalFee);
        }

        walletService.withdrawSwapMoney(totalFee, HistoryType.TEMPORARY_WITHDRAW, member.getId());
    }

    @Override
    public void withdrawMemberSwapMoneyAtComplete(Deal deal) {

        DealWallet dealWallet = dealWalletRepository.findByDealId(deal.getId())
                .orElseThrow(DealWalletNotFoundException::new);

        if (dealWallet.getFirstSwapMoney() != null) {
            Long firstMemberTotalFee = dealWallet.getFirstSwapMoney() - deal.getFirstMemberbill().getCommission();
            walletService.depositSwapMoney(firstMemberTotalFee,
                    HistoryType.DEAL_DEPOSIT, deal.getSecondMemberbill().getMember().getId());
        }
        if (dealWallet.getSecondSwapMoney() != null) {
            Long secondMemberTotalFee = dealWallet.getSecondSwapMoney() - deal.getSecondMemberbill().getCommission();
            walletService.depositSwapMoney(secondMemberTotalFee,
                    HistoryType.DEAL_DEPOSIT, deal.getFirstMemberbill().getMember().getId());
        }
    }

    @Override
    public void rollbackTemporarySwapMoney(Deal deal) {

        DealWallet dealWallet = dealWalletRepository.findByDealId(deal.getId())
                .orElseThrow(DealWalletNotFoundException::new);

        if (dealWallet.getFirstSwapMoney() != null) {
            walletService.depositSwapMoney(dealWallet.getFirstSwapMoney(),
                    HistoryType.CANCEL_WITHDRAW, deal.getFirstMemberbill().getMember().getId());
        }
        if (dealWallet.getSecondSwapMoney() != null) {
            walletService.depositSwapMoney(dealWallet.getSecondSwapMoney(),
                    HistoryType.CANCEL_WITHDRAW, deal.getSecondMemberbill().getMember().getId());
        }

        dealWalletRepository.delete(dealWallet);
    }
}
