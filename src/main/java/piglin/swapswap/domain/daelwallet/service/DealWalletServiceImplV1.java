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

@Service
@RequiredArgsConstructor
public class DealWalletServiceImplV1 implements DealWalletService {

    private final DealWalletRepository dealWalletRepository;
    private final WalletService walletService;

    @Override
    @Transactional
    public void createDealWallet(Deal deal, Member member, Long swapMoney) {

        DealWallet dealWallet = DealWalletMapper.setDealWallet(deal, null, null);

        if (deal.getFirstUserId().equals(member.getId())) {
            dealWallet.updateFirstSwapMoney(swapMoney);
        }

        if (deal.getSecondUserId().equals(member.getId())) {
            dealWallet.updateSecondSwapMoney(swapMoney);
        }

        walletService.withdrawSwapMoney(swapMoney, HistoryType.DEAL_WITHDRAW, member.getId());

        dealWalletRepository.save(dealWallet);
    }

    @Override
    public void updateDealWallet(Deal deal, Member member, Long swapMoney) {

        DealWallet dealWallet = dealWalletRepository.findByDealId(deal.getId()).orElseThrow(
                () -> new RuntimeException("")
        );

        if(deal.getFirstUserId().equals(member.getId())) {
            dealWallet.updateFirstSwapMoney(swapMoney);
        }

        if(deal.getSecondUserId().equals(member.getId())) {
            dealWallet.updateSecondSwapMoney(swapMoney);
        }

        walletService.withdrawSwapMoney(swapMoney, HistoryType.DEAL_WITHDRAW, member.getId());
    }

    @Override
    public void withdrawMemberSwapMoneyAtUpdate(Deal deal, Member member) {

        DealWallet dealWallet = dealWalletRepository.findByDealId(deal.getId()).orElseThrow(
                () -> new RuntimeException("")
        );

        Long swapMoney = 0L;

        if(deal.getFirstUserId().equals(member.getId())) {
            swapMoney = dealWallet.getFirstSwapMoney();
            dealWallet.updateFirstSwapMoney(null);
        }

        if(deal.getSecondUserId().equals(member.getId())) {
            swapMoney = dealWallet.getSecondSwapMoney();
            dealWallet.updateSecondSwapMoney(null);
        }

        walletService.depositSwapMoney(swapMoney, HistoryType.DEAL_DEPOSIT, member.getId());
    }

    @Override
    public void withdrawMemberSwapMoneyAtComplete(Deal deal) {

        DealWallet dealWallet  = dealWalletRepository.findByDealId(deal.getId()).orElseThrow(
                () -> new RuntimeException("")
        );

        walletService.depositSwapMoney(dealWallet.getFirstSwapMoney(),HistoryType.DEAL_DEPOSIT , deal.getSecondUserId());
        walletService.depositSwapMoney(dealWallet.getSecondSwapMoney(),HistoryType.DEAL_DEPOSIT , deal.getFirstUserId());
    }

    @Override
    public Boolean existsDealWallet(Long dealId) {

        return dealWalletRepository.existsByDealId(dealId);
    }
}
