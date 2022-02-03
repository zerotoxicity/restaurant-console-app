package rrpss.controller;

import rrpss.entities.Membership;
import rrpss.entities.MembershipType;
import rrpss.util.Crypto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Control class for admin
 */
public final class AdminController {
    /**
     * Singleton instance of auth controller
     */
    private static AdminController INSTANCE;
    /**
     * Hardcoded opening hours of restaurant
     */
    private final LocalTime opening = LocalTime.of(10, 0);
    /**
     * Hardcoded closing hours of restaurant
     */
    private final LocalTime closing = LocalTime.of(21, 0);
    /**
     * Hardcoded dining hours of reservation in restaurant
     */
    public final long DINING_HOURS = 1;

    /**
     * Class constructor
     */
    private AdminController() {

    }

    /**
     * Get AdminController instance
     * @return instance
     */
    public static AdminController getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AdminController();
        return INSTANCE;
    }

    /**
     * Get all existing memberships types
     * @return memberships
     */
    public ArrayList<Membership> getMemberships() {
        return Membership.getMemberships();
    }

    /**
     * Create a membership types using a pre-defined template
     */
    public void bulkCreateMembership() {
        Membership[] memberships = {
                new Membership(Crypto.genUUID(), 5, 10, MembershipType.SLIVER, 50),
                new Membership(Crypto.genUUID(), 10, 15, MembershipType.GOLD, 100),
                new Membership(Crypto.genUUID(), 15, 20, MembershipType.DIAMOND, 200),
        };
        Membership.set(new ArrayList<>(Arrays.asList(memberships)));
    }

    /**
     * Get restaurant's opening hours
     * @return opening hours
     */
    public LocalTime getOpening() {
        return opening;
    }

    /**
     * Check if restaurant is opened
     * @return
     */
    public boolean isOpened(LocalTime time) {
        if (time.isBefore(AdminController.getInstance().getOpening()) ||
                time.isBefore(LocalTime.now()) ||
                time.isAfter(AdminController.getInstance().getClosing()))
            return false;
        return true;
    }

    /**
     * Get restaurant's closing hours
     * @return closing hours
     */
    public LocalTime getClosing() {
        return closing;
    }

    /**
     * Check if the store is closed
     * @return true if closed
     */
    public boolean isClosed() {
        return LocalTime.now().isAfter(getClosing());
    }
}
