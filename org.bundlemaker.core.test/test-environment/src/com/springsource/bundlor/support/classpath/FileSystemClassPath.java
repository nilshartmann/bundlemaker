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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.springsource.bundlor.ClassPath;
import com.springsource.bundlor.ClassPathEntry;

final class FileSystemClassPath implements ClassPath {

    private final File classPathRoot;

    public FileSystemClassPath(File classPathRoot) {
        this.classPathRoot = classPathRoot;
    }

    public ClassPathEntry getEntry(String name) {
        File file = new File(classPathRoot, name);
        if (file.exists()) {
            return new FileSystemClassPathEntry(this.classPathRoot, file);
        }
        return null;
    }

    public Iterator<ClassPathEntry> iterator() {
        return new FileSystemClassPathIterator(this.classPathRoot);
    }

    public void close() {
        // Nothing to close
    }

    public String toString() {
        return classPathRoot.getAbsolutePath();
    }

    private static class FileSystemClassPathIterator implements Iterator<ClassPathEntry> {

        private final Iterator<ClassPathEntry> fileSystemIterator;

        public FileSystemClassPathIterator(File root) {
            List<ClassPathEntry> entries = new ArrayList<ClassPathEntry>();
            enumerateEntries(root, root, entries);
            this.fileSystemIterator = entries.iterator();
        }

        public boolean hasNext() {
            return fileSystemIterator.hasNext();
        }

        public ClassPathEntry next() {
            return fileSystemIterator.next();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void enumerateEntries(File root, File orignalRootPath, List<ClassPathEntry> entries) {
            for (File file : root.listFiles()) {
                if (file.isDirectory()) {
                    enumerateEntries(file, orignalRootPath, entries);
                } else {
                    String name = file.getAbsolutePath().substring(orignalRootPath.getAbsolutePath().length());
                    name = normalizeWindowsPaths(name);
                    name = normalizeRootPaths(name);
                    entries.add(new FileSystemClassPathEntry(orignalRootPath, file));
                }
            }
        }

        private String normalizeWindowsPaths(String name) {
            return name.replace('\\', '/');
        }

        private String normalizeRootPaths(String name) {
            if (name.startsWith("/")) {
                return name.substring(1);
            }
            return name;
        }
    }
}
