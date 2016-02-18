package vrto;

public class ReadOnlyDatabaseException extends RuntimeException {
    public ReadOnlyDatabaseException(String message) {
        super(message);
    }
}
