package rrpss.ui.admin;

import rrpss.controller.AdminController;
import rrpss.ui.UI;

/**
 * Boundary class for generating membership types
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class CreateMembershipUI extends UI {
    /**
     * Create different membership tiers based on a pre-defined template
     */
    public void form() {
        AdminController.getInstance().bulkCreateMembership();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        subtitle("Creating membership");
        form();
    }
}
