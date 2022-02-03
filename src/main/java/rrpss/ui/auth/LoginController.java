package rrpss.ui.auth;

import rrpss.controller.AuthController;
import rrpss.error.InvalidCredentials;
import rrpss.entities.Staff;
import rrpss.util.Crypto;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Control class for login
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class LoginController {
    /**
     * Login user if account exists
     * @param username username of user
     * @param password password of user
     * @return true if account exist
     * @throws InvalidCredentials
     * error is thrown when hashsum does not match
     */
    public boolean login(String username, String password) throws InvalidCredentials {
        InvalidCredentials err = new InvalidCredentials("Invalid username or password");
        Staff staff = Staff.findByUsername(username);
        if (staff == null) throw err;
        boolean match = false;
        try {
            match = Crypto.compare(password, staff.getPassword());
        } catch (InvalidKeySpecException ik) {

        } catch (NoSuchAlgorithmException na) {

        }

        if (!match) throw err;
        AuthController.getInstance().authenticate(staff);
        return true;
    }
}
