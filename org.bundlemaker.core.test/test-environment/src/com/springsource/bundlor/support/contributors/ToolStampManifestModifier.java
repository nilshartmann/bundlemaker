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

package com.springsource.bundlor.support.contributors;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.springsource.bundlor.support.ManifestModifier;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * Stamps the manifest with the name of the tool and it's version
 * <p />
 * 
 * <strong>Concurrent Semantics</strong><br />
 * 
 * Threadsafe
 * 
 * @author Ben Hale
 */
public final class ToolStampManifestModifier implements ManifestModifier {

    private static final String HEADER = "Tool";

	private static final String VALUE_FORMAT = "Bundlor %s";

    /**
     * {@inheritDoc}
     */
    public void modify(ManifestContents manifest) {
        String version = this.getClass().getPackage().getImplementationVersion();
        if (version == null) {
            version = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        }

        manifest.getMainAttributes().put(HEADER, String.format(VALUE_FORMAT, version));
    }
}
