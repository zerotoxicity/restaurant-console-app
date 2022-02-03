package rrpss.util.validation;

/**
 * Predicate interface
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 * @param <T>
 */

/**
 * Predicate interface for defining custom validation
 * @param <T>
 */
public interface Predicate<T> {
    String val(T param);
}