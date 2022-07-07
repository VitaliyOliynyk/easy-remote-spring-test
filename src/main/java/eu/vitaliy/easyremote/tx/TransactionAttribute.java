package eu.vitaliy.easyremote.tx;

import java.io.Serializable;

public class TransactionAttribute implements Serializable {
    private boolean isTransactional;
    private String transactionManager;
    private boolean isRollback;

    public TransactionAttribute(boolean isTransactional, String transactionManager, boolean isRollback) {
        this.isTransactional = isTransactional;
        this.transactionManager = transactionManager;
        this.isRollback = isRollback;
    }

    public boolean isTransactional() {
        return isTransactional;
    }

    public String getTransactionManager() {
        return transactionManager;
    }

    public boolean isRollback() {
        return isRollback;
    }
}
