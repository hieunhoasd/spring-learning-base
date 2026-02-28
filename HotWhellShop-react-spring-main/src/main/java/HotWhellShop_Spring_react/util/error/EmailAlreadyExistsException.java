package HotWhellShop_Spring_react.util.error;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String meassge) {
        super(meassge);
    }
}
