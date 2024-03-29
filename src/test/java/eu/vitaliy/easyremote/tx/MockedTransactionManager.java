package eu.vitaliy.easyremote.tx;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

public class MockedTransactionManager implements PlatformTransactionManager {

    @Override
    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        return new TransactionStatus() {
            @Override
            public boolean isNewTransaction() {
                return false;
            }

            @Override
            public boolean hasSavepoint() {
                return false;
            }

            @Override
            public void setRollbackOnly() {

            }

            @Override
            public boolean isRollbackOnly() {
                return false;
            }

            @Override
            public void flush() {

            }

            @Override
            public boolean isCompleted() {
                return false;
            }

            @Override
            public Object createSavepoint() throws TransactionException {
                return null;
            }

            @Override
            public void rollbackToSavepoint(Object savepoint) throws TransactionException {

            }

            @Override
            public void releaseSavepoint(Object savepoint) throws TransactionException {

            }
        };
    }

    @Override
    public void commit(TransactionStatus status) throws TransactionException {

    }

    @Override
    public void rollback(TransactionStatus status) throws TransactionException {

    }

}
