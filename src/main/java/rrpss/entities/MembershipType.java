package rrpss.entities;

/**
 Enum for membership types
 @author  SSP4 Group 1
 @version 1.0
 @since   2021-10-26
 */
public enum MembershipType {
    SLIVER {
        @Override
        public String toString() {
            return "Sliver Membership";
        }
    },
    GOLD {
        @Override
        public String toString() {
            return "Gold Membership";
        }
    },
    DIAMOND {
        @Override
        public String toString() {
            return "Diamond Membership";
        }
    }
}
