package piglin.swapswap.domain.daelwallet.service;

import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.member.entity.Member;

public interface DealWalletService {

     void createDealWallet(Deal deal, Member member, Long totalFee);

//     void withdrawMemberSwapMoneyAtComplete(Long billId);

     void rollbackTemporarySwapMoney(Deal deal);

     void removeDealWallet(Deal deal, Long loginMemberId);
}
