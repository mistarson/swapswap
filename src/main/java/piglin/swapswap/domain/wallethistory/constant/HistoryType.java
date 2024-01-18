package piglin.swapswap.domain.wallethistory.constant;

public enum HistoryType {

    NORMAL_DEPOSIT(HistoryTypeName.NORMAL_DEPOSIT),
    NORMAL_WITHDRAW(HistoryTypeName.NORMAL_WITHDRAW),
    DEAL_DEPOSIT(HistoryTypeName.DEAL_DEPOSIT),
    DEAL_WITHDRAW(HistoryTypeName.DEAL_WITHDRAW);
    
    private final String name;

    HistoryType(String name) {
        this.name = name;
    }

    public static class HistoryTypeName {
        public static String NORMAL_DEPOSIT = "일반 입금";
        public static String NORMAL_WITHDRAW = "일반 출금";
        public static String DEAL_DEPOSIT = "거래 입금";
        public static String DEAL_WITHDRAW = "거래 출금";
    } 
    
}
