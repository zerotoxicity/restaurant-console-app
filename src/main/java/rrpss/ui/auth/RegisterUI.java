package rrpss.ui.auth;

import rrpss.entities.JobTitle;
import rrpss.ui.UI;

/**
 * Boundary class for registering a new staff member
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class RegisterUI extends UI {

    /**
     * registerController for accessing registration methods
     */
    private RegisterController controller = new RegisterController();

    /**
     * Form for adding a new staff
     */
    private void form() {
        print("Enter name: ");
        String name = promptStr();

        JobTitle[] titles = JobTitle.values();
        subtitle("Available Job Titles");
        for (JobTitle t : titles)
            println(String.format("[%d] %s", t.ordinal(), t.toString()));

        print("\nSelect title: ");
        JobTitle title = titles[promptInt(0, titles.length - 1)];

        print("Enter username: ");
        String username = promptStr((String s) -> {
            if (controller.usernameExists(s))
                return String.format("%s is taken. Please key in another name", s);
            return "";
        });

        print("Enter password: ");
        String password = promptHidden();
        controller.register(name,
                title,
                username,
                password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        title("Register new staff");
        form();
    }
}
