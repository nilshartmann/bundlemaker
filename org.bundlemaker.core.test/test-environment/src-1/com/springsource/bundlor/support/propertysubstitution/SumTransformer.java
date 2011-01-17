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
 * The sum transformer perhaps a numeric computation (for example adding 3 or subtracting 4) on the input value.
 * 
 * @author Andy Clement
 */
final class SumTransformer implements Transformer {

    private int modifier;

    SumTransformer(int modifier) {
        this.modifier = modifier;
    }

    @SuppressWarnings("unchecked")
    public <T> T transform(T input) {
        Integer n = (Integer) input;
        return (T) Integer.valueOf(n.intValue() + modifier);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(modifier > 0 ? "+" : "");
        sb.append(Integer.toString(modifier));
        return sb.toString();
    }

}