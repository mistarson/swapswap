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

        if (deal.getRequestMemberbill().getMember().getId().equals(member.getId())) {
            dealWallet.updateRequestMemberSwapMoney(totalFee);
        }

        if (deal.getReceiveMemberbill().getMember().getId().equals(member.getId())) {
            dealWallet.updateReceiveMemberSwapMoney(totalFee);
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

        if (deal.getRequestMemberbill().getMember().getId().equals(member.getId())) {
            dealWallet.updateRequestMemberSwapMoney(totalFee);
        }
        if (deal.getReceiveMemberbill().getMember().getId().equals(member.getId())) {
            dealWallet.updateReceiveMemberSwapMoney(totalFee);
        }

        walletService.withdrawSwapMoney(totalFee, HistoryType.TEMPORARY_WITHDRAW, member.getId());
    }

    @Override
    public void withdrawMemberSwapMoneyAtComplete(Deal deal) {

        DealWallet dealWallet = dealWalletRepository.findByDealId(deal.getId())
                .orElseThrow(DealWalletNotFoundException::new);

        if (dealWallet.getRequestMemberSwapMoney() != null) {
            Long requestMemberTotalFee = dealWallet.getRequestMemberSwapMoney() - deal.getRequestMemberbill().getCommission();
            walletService.depositSwapMoney(requestMemberTotalFee,
                    HistoryType.DEAL_DEPOSIT, deal.getReceiveMemberbill().getMember().getId());
        }
        if (dealWallet.getReceiveMemberSwapMoney() != null) {
            Long receiveMemberTotalFee = dealWallet.getReceiveMemberSwapMoney() - deal.getReceiveMemberbill().getCommission();
            walletService.depositSwapMoney(receiveMemberTotalFee,
                    HistoryType.DEAL_DEPOSIT, deal.getRequestMemberbill().getMember().getId());
        }
    }

    @Override
    public void rollbackTemporarySwapMoney(Deal deal) {

        DealWallet dealWallet = dealWalletRepository.findByDealId(deal.getId())
                .orElseThrow(DealWalletNotFoundException::new);

        if (dealWallet.getRequestMemberSwapMoney() != null) {
            walletService.depositSwapMoney(dealWallet.getRequestMemberSwapMoney(),
                    HistoryType.CANCEL_WITHDRAW, deal.getRequestMemberbill().getMember().getId());
        }
        if (dealWallet.getReceiveMemberSwapMoney() != null) {
            walletService.depositSwapMoney(dealWallet.getReceiveMemberSwapMoney(),
                    HistoryType.CANCEL_WITHDRAW, deal.getReceiveMemberbill().getMember().getId());
        }

        dealWalletRepository.delete(dealWallet);
    }
}
