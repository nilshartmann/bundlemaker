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

package com.springsource.bundlor.support.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileSystemPropertiesSource implements PropertiesSource {

    private final Properties properties;

    public FileSystemPropertiesSource(File propertiesFile) {
        this.properties = readProperties(propertiesFile);
    }

    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    public Properties getProperties() {
        return this.properties;
    }

    private Properties readProperties(File propertiesFile) {
        Properties p = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(propertiesFile);
            p.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                // Nothing to do here
            }
        }
        return p;
    }

}
