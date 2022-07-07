package eu.vitaliy.easyremote.util;

public class ExceptionUtil {

    public static <T> T wrapException(SupplierWithException<T> supplierWithException) {
        try {
            return supplierWithException.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public interface SupplierWithException<T> {
        T get() throws Exception;
    }
}
