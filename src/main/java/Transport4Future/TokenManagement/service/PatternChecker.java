/*
 * Copyright (c) 2020.
 * Content created by:
 * - Andrei García Cuadra
 * - Miguel Hernández Cassel
 *
 * For the module PDS, on university Carlos III de Madrid.
 * Do not share, review nor edit any content without implicitly asking permission to it's owners, as you can contact by this email:
 * andreigarciacuadra@gmail.com
 *
 * All rights reserved.
 */

package Transport4Future.TokenManagement.service;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * The type Pattern checker.
 * Required by constraint checker.
 */
public class PatternChecker {
    /**
     * Check length min boolean.
     *
     * @param toCheck   the to check
     * @param minLength the min length
     * @return the boolean
     */
    public boolean checkLengthMin(String toCheck, int minLength) {
        return this.checkLengthBetween(toCheck, minLength, Integer.MAX_VALUE);
    }

    /**
     * Check length max boolean.
     *
     * @param toCheck   the to check
     * @param maxLength the max length
     * @return the boolean
     */
    public boolean checkLengthMax(String toCheck, int maxLength) {
        return this.checkLengthBetween(toCheck, 0, maxLength);
    }

    /**
     * Check length between boolean.
     *
     * @param toCheck   the to check
     * @param minLength the min length
     * @param maxLength the max length
     * @return the boolean
     */
    public boolean checkLengthBetween(String toCheck, int minLength, int maxLength) {
        if (toCheck == null) {
            return false;
        }
        final int length = toCheck.length();
        return length >= minLength && length <= maxLength;
    }

    /**
     * Check regex boolean.
     *
     * @param toCheck the to check
     * @param regex   the regex
     * @return the boolean
     * @throws PatternSyntaxException the pattern syntax exception
     */
    public boolean checkRegex(String toCheck, String regex) throws PatternSyntaxException {
        if (toCheck == null || regex == null) {
            return true;
        }
        Pattern serialNumberPattern = Pattern.compile(regex);
        return !serialNumberPattern.matcher(toCheck).matches();
    }

    /**
     * Check value in accepted boolean.
     *
     * @param value    the value
     * @param accepted the accepted
     * @return the boolean
     */
    public boolean checkValueInAccepted(String value, String... accepted) {
        if (value == null || accepted == null) {
            return false;
        }
        Optional<String> optional = Arrays.stream(accepted)
                .filter(x -> x.equalsIgnoreCase(value))
                .findFirst();
        return optional.isPresent();
    }
}
