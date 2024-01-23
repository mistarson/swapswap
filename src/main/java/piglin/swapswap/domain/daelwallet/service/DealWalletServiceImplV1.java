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

        DealWallet dealWallet = findDealWalletByDeal(deal);

        if (deal.getFirstUserId().equals(member.getId())) {
            dealWallet.updateFirstSwapMoney(swapMoney);
        }

        if (deal.getSecondUserId().equals(member.getId())) {
            dealWallet.updateSecondSwapMoney(swapMoney);
        }

        walletService.withdrawSwapMoney(swapMoney, HistoryType.DEAL_WITHDRAW, member.getId());
    }

    @Override
    public void withdrawMemberSwapMoneyAtUpdate(Deal deal, Member member) {

        DealWallet dealWallet = findDealWalletByDeal(deal);

        Long swapMoney = 0L;

        if (deal.getFirstUserId().equals(member.getId())) {
            if (!(dealWallet.getFirstSwapMoney() == null)) {
                swapMoney = dealWallet.getFirstSwapMoney();
            }
            dealWallet.updateFirstSwapMoney(null);
        }

        if (deal.getSecondUserId().equals(member.getId())) {
            if (!(dealWallet.getSecondSwapMoney() == null)) {
                swapMoney = dealWallet.getSecondSwapMoney();
            }
            dealWallet.updateSecondSwapMoney(null);
        }

        if(!(swapMoney == 0L)) {
            walletService.depositSwapMoney(swapMoney, HistoryType.DEAL_DEPOSIT, member.getId());
        }
    }

    @Override
    public void withdrawMemberSwapMoneyAtComplete(Deal deal) {

        DealWallet dealWallet = findDealWalletByDeal(deal);

        if (!(dealWallet.getFirstSwapMoney() == null)) {
            walletService.depositSwapMoney(dealWallet.getFirstSwapMoney(), HistoryType.DEAL_DEPOSIT,
                    deal.getSecondUserId());
        }
        if (!(dealWallet.getSecondSwapMoney() == null)) {
            walletService.depositSwapMoney(dealWallet.getSecondSwapMoney(),
                    HistoryType.DEAL_DEPOSIT, deal.getFirstUserId());
        }
    }

    @Override
    public void withdrawMemberSwapMoneyAtDealUpdate(Deal deal) {

        DealWallet dealWallet = getDealWallet(deal);

        if (!(dealWallet.getFirstSwapMoney() == null)) {
            walletService.depositSwapMoney(dealWallet.getFirstSwapMoney(), HistoryType.DEAL_DEPOSIT,
                    deal.getFirstUserId());
        }
        if (!(dealWallet.getSecondSwapMoney() == null)) {
            walletService.depositSwapMoney(dealWallet.getSecondSwapMoney(),
                    HistoryType.DEAL_DEPOSIT, deal.getSecondUserId());
        }

        dealWalletRepository.delete(dealWallet);
    }

    @Override
    public Boolean existsDealWallet(Long dealId) {

        return dealWalletRepository.existsByDealId(dealId);
    }

    private DealWallet getDealWallet(Deal deal) {
        return findDealWalletByDeal(deal);
    }

    private DealWallet findDealWalletByDeal(Deal deal) {

        return dealWalletRepository.findByDealId(deal.getId())
                .orElseThrow(() -> new RuntimeException(""));
    }
}
