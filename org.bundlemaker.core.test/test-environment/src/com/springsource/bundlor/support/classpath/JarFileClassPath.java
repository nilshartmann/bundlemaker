/*
 * Copyright 2009 SpringSource
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

package com.springsource.bundlor.support.classpath;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.springsource.bundlor.ClassPath;
import com.springsource.bundlor.ClassPathEntry;

final class JarFileClassPath implements ClassPath {

    private final JarFile jarFile;

    public JarFileClassPath(JarFile jarFile) {
        this.jarFile = jarFile;
    }

    public Iterator<ClassPathEntry> iterator() {
        return new JarFileClassPathIterator(this.jarFile);
    }

    public ClassPathEntry getEntry(String name) {
        JarEntry entry = this.jarFile.getJarEntry(name);
        if (entry != null) {
            return new JarFileClassPathEntry(this.jarFile, entry);
        }
        return null;
    }

    public void close() {
        try {
            this.jarFile.close();
        } catch (IOException e) {
            // Nothing to do here
        }
    }

    public String toString() {
        return jarFile.getName();
    }

    private static class JarFileClassPathIterator implements Iterator<ClassPathEntry> {

        private final JarFile jarFile;

        private final Enumeration<JarEntry> jarEntryEnumeration;

        public JarFileClassPathIterator(JarFile jarFile) {
            this.jarFile = jarFile;
            this.jarEntryEnumeration = jarFile.entries();
        }

        public boolean hasNext() {
            return this.jarEntryEnumeration.hasMoreElements();
        }

        public ClassPathEntry next() {
            return new JarFileClassPathEntry(this.jarFile, this.jarEntryEnumeration.nextElement());
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
