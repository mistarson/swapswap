package piglin.swapswap.domain.daelwallet.service;

import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.member.entity.Member;

public interface DealWalletService {



     void createDealWallet(Deal deal, Member member, Long firstSwapMoney);

     void updateDealWallet(Deal deal, Member member, Long firstSwapMoney);

     void withdrawMemberSwapMoneyAtUpdate(Deal deal, Member member);

     void withdrawMemberSwapMoneyAtComplete(Deal deal);

     void withdrawMemberSwapMoneyAtDealUpdate(Deal deal);
     Boolean existsDealWallet(Long dealId);
}
