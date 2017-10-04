package github.homepunk.com.fieldserrorhandler.exceptions;

/**
 * Created by Homepunk on 28.09.2017.
 **/

public class TargetNotBoundException extends NullPointerException {
    public static final String MESSAGE = "Target is not bound, please bind target first";

    public TargetNotBoundException() {
        super(MESSAGE);
    }
}
