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

import java.util.ArrayList;
import java.util.List;

import com.springsource.util.osgi.manifest.parse.ParserLogger;

/**
 * A simple implementation of {@link ParserLogger} that outputs message to console
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Threadsafe
 * 
 * @author Ben Hale
 */
public class SimpleParserLogger implements ParserLogger {

    private volatile boolean used = false;

    private final List<String> messages = new ArrayList<String>();

    private final Object messagesMonitor = new Object();

    /**
     * {@inheritDoc}
     */
    public String[] errorReports() {
        if (this.used) {
            synchronized (messagesMonitor) {
                return messages.toArray(new String[messages.size()]);
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void outputErrorMsg(Exception re, String item) {
        this.used = true;
        synchronized (messagesMonitor) {
            messages.add(item);
        }
        System.err.println(item);
        if (re != null) {
            re.printStackTrace();
        }
    }

}
