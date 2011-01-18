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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.springsource.bundlor.ClassPathEntry;

final class JarFileClassPathEntry implements ClassPathEntry {

    private final JarFile jarFile;

    private final JarEntry entry;

    public JarFileClassPathEntry(JarFile jarFile, JarEntry entry) {
        this.jarFile = jarFile;
        this.entry = entry;
    }

    public InputStream getInputStream() {
        try {
            return this.jarFile.getInputStream(this.entry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Reader getReader() {
        return new InputStreamReader(getInputStream());
    }

    public boolean isDirectory() {
        return this.entry.isDirectory();
    }

    public String getName() {
        return this.entry.getName();
    }

    public String toString() {
        return getName();
    }

}
