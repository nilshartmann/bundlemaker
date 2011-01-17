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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;

import com.springsource.bundlor.ClassPathEntry;

final class FileSystemClassPathEntry implements ClassPathEntry {

    private final File root;

    private final File file;

    public FileSystemClassPathEntry(File root, File file) {
        this.root = root;
        this.file = file;
    }

    public InputStream getInputStream() {
        try {
            return new FileInputStream(this.file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Reader getReader() {
        try {
            return new FileReader(this.file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return this.file.getAbsolutePath().substring(this.root.getAbsolutePath().length() + 1);
    }

    public boolean isDirectory() {
        return this.file.isDirectory();
    }

    public String toString() {
        return getName();
    }

}
