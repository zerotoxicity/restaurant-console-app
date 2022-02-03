package rrpss.ui.auth;

import rrpss.error.InvalidCredentials;
import rrpss.ui.UI;

/**
 * Boundary class for login
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class LoginUI extends UI {
    /**
     * access login methods
     */
    final LoginController loginController = new LoginController();

    /**
     * Login form
     */
    private void form() {
        lifecycle: while(true) {
            print("Enter username: ");
            String username = promptStr();

            print("Enter password: ");
            String password = promptStr();
            try {
                if (loginController.login(username, password)) break lifecycle;
            } catch (InvalidCredentials ex) {
                println(ex.getMessage());
                print("Press q to quit, others to try again: ");
                String input = promptStr();
                if (input.equalsIgnoreCase("q")) break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        form();
    }
}
