package org.bill.wflite;

public enum OrderFlowState {
    START,
    CREATE_ORDER,
    SETTLEMENT_MAKER,
    SETTLEMENT_CHECKER,
    INVESTMENT_MAKER,
    INVESTMENT_CHECKER,
    END_APPROVED,
    END_REJECTED
}
