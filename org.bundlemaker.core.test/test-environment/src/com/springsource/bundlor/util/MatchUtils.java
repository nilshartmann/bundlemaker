/*
 * Copyright 2008-2009 SpringSource
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.springsource.bundlor.util;

import java.util.regex.Pattern;

/**
 * Wildcard package matching utils.
 * <p/>
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Threadsafe.
 * 
 * @author Rob Harrop
 */
public final class MatchUtils {

    /**
     * Indicates that no match was found.
     */
    public static final int NO_MATCH = -1;

    private static final String WILDCARD = "*";

    private static final String DOT_WILDCARD = ".*";

    private static final String PACKAGE_SEPARATOR = ".";

    private static final String REGEX_ESCAPED_PACKAGE_SEPARATOR = "\\.";

    private static final String REGEX_WILDCARD = ".*";

    /** Pattern to match on optional following package segments: com.foo.* -> com.foo.bar */
    private static final String REGEX_OPTIONAL_DOT_WILDCARD = "(\\..*)?";

    private static final String REGEX_OPTIONAL_WILDCARD = "(\\.)?(.*)?";

    /**
     * Tests whether <code>candidate</code> matches the supplied <code>pattern</code>.
     * 
     * @param candidate the candidate.
     * @param pattern the pattern to match against.
     * @return <code>true</code> if there is a match; otherwise <code>false</code>.
     */
    public static boolean matches(String candidate, String pattern) {
        if (WILDCARD.equals(pattern)) {
            return true;
        }

        boolean wildcard = pattern.endsWith(WILDCARD);
        boolean dotWildcard = pattern.endsWith(DOT_WILDCARD);
        String matchString = wildcard ? stripWildcard(pattern) : pattern;

        // Escape the package separator '.'
        matchString = matchString.replace(PACKAGE_SEPARATOR, REGEX_ESCAPED_PACKAGE_SEPARATOR);

        // Make '*' a regex wildcard '.*'
        matchString = matchString.replace(WILDCARD, REGEX_WILDCARD);

        // If pattern ended with wildcard append an optional regex wildcard
        if (dotWildcard) {
            matchString += REGEX_OPTIONAL_DOT_WILDCARD;
        } else if (wildcard) {
            matchString += REGEX_OPTIONAL_WILDCARD;
        }

        return Pattern.matches(matchString, candidate);
    }

    /**
     * Performs a ranked match between the candidate package and the supplied pattern. If no match is found, the match
     * rank is {@link #NO_MATCH}. When a match is found a higher rank signifies a more specific match. For example,
     * <code>com.foo.bar.*</code> is a more specific match against <code>com.foo.bar.Fudge</code> than
     * <code>com.foo.*</code>.
     * 
     * @param candidate the candidate package.
     * @param pattern the package pattern.
     * @return the match rank.
     */
    public static int rankedMatch(String candidate, String pattern) {
        if (pattern == null || pattern.length() == 0) {
            return NO_MATCH;
        }
        if (WILDCARD.equals(pattern)) {
            return 0;
        }
        boolean wildcard = pattern.endsWith(WILDCARD);
        String matchString = wildcard ? stripWildcard(pattern) : pattern;

        if ((!wildcard && candidate.equals(pattern)) || (wildcard && candidate.startsWith(matchString))) {
            String[] candidateComponents = candidate.split("\\.");
            String[] matchStringComponents = matchString.split("\\.");

            if (matchStringComponents[matchStringComponents.length - 1].equals(candidateComponents[matchStringComponents.length - 1])) {
                int specificity = 0;
                int x = NO_MATCH;
                while ((x = pattern.indexOf('.', x + 1)) > -1) {
                    specificity++;
                }
                return specificity;
            } else {
                return NO_MATCH;
            }
        } else {
            return NO_MATCH;
        }
    }

    /**
     * Strips the wildcard from the supplied pattern.
     * 
     * @param pattern the pattern.
     * @return the stripped pattern.
     */
    private static String stripWildcard(String pattern) {
        int lastIndex = pattern.length() - WILDCARD.length();
        if (lastIndex > 0 && pattern.charAt(lastIndex - 1) == '.') {
            lastIndex--;
        }
        return pattern.substring(0, lastIndex);
    }
}
