package org.bundlemaker.core.util;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.osgi.framework.Bundle;

/**
 * A ClassLoader delegating to a given OSGi bundle.
 *
 */
public class BundleDelegatingClassLoader extends ClassLoader {
    private final Bundle bundle;
    private final ClassLoader classLoader;

    public BundleDelegatingClassLoader(Bundle bundle) {
        this(bundle, null);
    }

    public BundleDelegatingClassLoader(Bundle bundle, ClassLoader classLoader) {
        this.bundle = bundle;
        this.classLoader = classLoader;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return bundle.loadClass(name);
    }

    protected URL findResource(String name) {
        URL resource = bundle.getResource(name);
        if (classLoader != null && resource == null) {
            resource = classLoader.getResource(name);
        }
        return resource;
    }

    @SuppressWarnings("unchecked")
    protected Enumeration findResources(String name) throws IOException {
        return bundle.getResources(name);
    }

    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class clazz;
        try {
            clazz = findClass(name);
        } catch (ClassNotFoundException cnfe) {
            if (classLoader != null) {
                try {
                    clazz = classLoader.loadClass(name);
                } catch (ClassNotFoundException e) {
                    throw new ClassNotFoundException(name + " from bundle " + bundle.getBundleId() + " (" + bundle.getSymbolicName() + ")", cnfe);
                }
            } else {
                throw new ClassNotFoundException(name + " from bundle " + bundle.getBundleId() + " (" + bundle.getSymbolicName() + ")", cnfe);
            }
        }
        if (resolve) {
            resolveClass(clazz);
        }
        return clazz;
    }

    public Bundle getBundle() {
        return bundle;
    }
}