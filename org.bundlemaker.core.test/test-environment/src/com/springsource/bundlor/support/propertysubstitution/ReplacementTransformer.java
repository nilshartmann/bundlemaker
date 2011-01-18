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

package com.springsource.bundlor.support.propertysubstitution;

/**
 * Replacement transformer is configured with a new value upon construction and returns that value instead of any value
 * that it is passed.
 * 
 * @author Andy Clement
 */
final class ReplacementTransformer implements Transformer {

    private String replacement;

    ReplacementTransformer(String replacement) {
        this.replacement = replacement;
    }

    @SuppressWarnings("unchecked")
    public <T> T transform(T input) {
        return (T) replacement;
    }

    public String toString() {
        return replacement;
    }

}
