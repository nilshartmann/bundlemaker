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

package com.springsource.bundlor.support.asm;

import org.objectweb.asm.Type;

import com.springsource.bundlor.support.partialmanifest.PartialManifest;

/**
 * <p/>
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Threadsafe.
 * 
 * @author Rob Harrop
 */
final class VisitorUtils {

    /**
     * Gets the package name from the supplied {@link Type}.
     * 
     * @param type the <code>Type</code>.
     * @return the package name.
     */
    public static String getPackageName(Type type) {
        String name;
        if (type.getSort() == Type.OBJECT) {
            name = type.getClassName();
        } else if (type.getSort() == Type.ARRAY) {
            return getPackageName(type.getElementType());
        } else {
            return null;
        }
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex > -1) {
            return name.substring(0, dotIndex);
        } else {
            return null;
        }
    }

    public static String getFullyQualifiedTypeName(Type type) {
        if (type.getSort() == Type.OBJECT) {
            return type.getClassName();
        } else if (type.getSort() == Type.ARRAY) {
            return getFullyQualifiedTypeName(type.getElementType());
        } else {
            return null;
        }
    }

    /**
     * Records the supplied {@link Type Types} in the supplied {@link PartialManifest}.
     * 
     * @param partialManifest the <code>PartialManifest</code>.
     * @param types the <code>Types</code>.
     */
    public static void recordReferencedTypes(PartialManifest partialManifest, Type... types) {
        for (Type t : types) {
            partialManifest.recordReferencedType(getFullyQualifiedTypeName(t));
        }
    }

    /**
     * Records a uses of the packages for the supplied {@link Type Types} against the supplied
     * <code>exportingPackage</code>.
     * 
     * @param partialManifest the <code>PartialManifest</code>.
     * @param exportingType the exporting type.
     * @param types the <code>Types</code>.
     */
    public static void recordUses(PartialManifest partialManifest, Type exportingType, Type... types) {
        for (Type t : types) {
            String packageName = getPackageName(t);
            partialManifest.recordUsesPackage(getPackageName(exportingType), packageName);
        }
    }
}
