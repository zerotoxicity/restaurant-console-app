package rrpss.error;

/**
 * Custom exception for invalid credential
 */
public class InvalidCredentials extends Exception {
    /**
     * Invalid user credential
     * @param msg error message
     */
    public InvalidCredentials(String msg) {
        super(msg);
    }
}
