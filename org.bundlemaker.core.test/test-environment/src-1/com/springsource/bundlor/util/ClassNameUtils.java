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
 * A utility class for dealing with Java class names
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Threadsafe
 * 
 * @author Ben Hale
 */
public class ClassNameUtils {

    private static final Pattern FQN_CLASS_PATTERN = Pattern.compile("[a-zA-Z](([\\w_$])+)+(\\.([\\w_$])+)*");

    /**
     * Determines if a string is valid Java identifier.
     * 
     * @param candidate The candidate string
     * @return <code>true</code> if candidate is a valid Java identifier, <code>false</code> otherwise
     */
    public static boolean isValidFqn(String candidate) {
        return FQN_CLASS_PATTERN.matcher(candidate).matches();
    }

}
