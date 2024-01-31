package piglin.swapswap.domain.daelwallet.service;

import piglin.swapswap.domain.bill.entity.Bill;
import piglin.swapswap.domain.deal.entity.Deal;
import piglin.swapswap.domain.member.entity.Member;

public interface DealWalletService {

     void createDealWallet(Deal deal, Member member, Long swapMoney);

     void withdrawMemberSwapMoneyAtComplete(Deal deal);

     void withdrawMemberSwapMoneyAtDealUpdate(Deal deal);

     void removeDealWallet(Long dealId, Long firstMemberId, Long secondMemberId, Long loginMemberId);
}
