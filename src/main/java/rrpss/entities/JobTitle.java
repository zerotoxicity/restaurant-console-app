package rrpss.entities;

/**
 Enums for Job Titles
 @author  SSP4 Group 1
 @version 1.0
 @since   2021-10-26
 */
public enum JobTitle {
    MANAGER {
        @Override
        public String toString() {
            return "Manager";
        }
    },
    WAITER {
        @Override
        public String toString() {
            return "Waiter";
        }
    }
}