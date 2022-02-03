package rrpss.entities;

/**
 * Represents CourseType stereotyping a MenuItems
 *
 * @author SSP4 Group 1
 * @version 1.0
 * @since 2021-10-26
 */
public enum CourseType {
    PROMOTIONS {
        @Override
        public String toString() {
            return "Promotions";
        }
    },
    STARTERS {
        @Override
        public String toString() {
            return "Starters";
        }
    },
    MAIN_COURSE {
        @Override
        public String toString() {
            return "Main Course";
        }
    },

    DESERTS {
        @Override
        public String toString() {
            return "Deserts";
        }
    },
    DRINKS {
        @Override
        public String toString() {
            return "Drinks";
        }
    }
}