package rrpss.error;

/**
 * Custom exception for input error
 */
public class InputError extends Exception {
    /**
     * Invalid input type
     * @param msg error message
     */
    public InputError(String msg) {
        super(msg);
    }
}
