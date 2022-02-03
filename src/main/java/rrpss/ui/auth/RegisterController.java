package rrpss.ui.auth;

import rrpss.controller.AuthController;
import rrpss.entities.JobTitle;
import rrpss.entities.Staff;
import rrpss.util.Crypto;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

/**
 * Control class for registering a staff
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class RegisterController {

    /**
     * Maintains a list of existing Staffs
     */
    ArrayList<Staff> existingStaffs;

    /**
     * Class constructor
     */
    public RegisterController() {
        existingStaffs = Staff.getStaffs();
    }

    /**
     * Check if name exists in the database
     * @param name name to be inputted into the database
     * @return true if duplicate, false if not duplicate
     */
    public boolean usernameExists(String name) {
        for (Staff s : existingStaffs) {
            if (s.getUsername().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    /**
     * Add staff to the database
     * @param name staff's name
     * @param title staff's job title
     * @param username  account username
     * @param password account password
     */
    public void register(String name, JobTitle title, String username, String password) {
        String hashed = "";
        try {
            hashed = Crypto.genHash(password);
            Crypto.compare(password, hashed);
        } catch (InvalidKeySpecException ik) {

        } catch (NoSuchAlgorithmException na) {

        }
        Staff staff = new Staff(
                Crypto.genUUID(),
                name,
                title,
                username,
                hashed
        );

        AuthController.getInstance().authenticate(staff);
        staff.save();
    }
}

