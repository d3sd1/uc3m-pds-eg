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

public class PatternChecker {
    public boolean checkLengthMin(String toCheck, int minLength) {
        return this.checkLengthBetween(toCheck, minLength, Integer.MAX_VALUE);
    }

    public boolean checkLengthMax(String toCheck, int maxLength) {
        return this.checkLengthBetween(toCheck, 0, maxLength);
    }

    public boolean checkLengthBetween(String toCheck, int minLength, int maxLength) {
        final int length = toCheck.length();
        return length >= minLength && length <= maxLength;
    }

    public boolean checkRegex(String toCheck, String regex) throws PatternSyntaxException {
        Pattern serialNumberPattern = Pattern.compile(regex);
        return serialNumberPattern.matcher(toCheck).matches();
    }

    public boolean checkValueInAccepted(String value, String... accepted) {
        Optional<String> optional = Arrays.stream(accepted)
                .filter(x -> x.equalsIgnoreCase(value))
                .findFirst();
        return optional.isPresent();
    }
}