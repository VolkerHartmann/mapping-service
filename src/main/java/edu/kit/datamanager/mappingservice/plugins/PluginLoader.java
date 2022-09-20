/*
 * Copyright 2022 Karlsruhe Institute of Technology.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.kit.datamanager.mappingservice.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Class for loading plugins.
 *
 * @author maximilianiKIT
 */
public class PluginLoader {

    /**
     * Logger for this class.
     */
    static Logger LOG = LoggerFactory.getLogger(PluginLoader.class);

    /**
     * Load plugins from a given directory.
     *
     * @param plugDir Directory containing plugins.
     * @return Map of plugins.
     * @throws IOException            If there is an error with the file system.
     * @throws MappingPluginException If there is an error with the plugin or the input.
     */
    public static Map<String, IMappingPlugin> loadPlugins(File plugDir) throws IOException, MappingPluginException {
        if (plugDir == null || plugDir.getAbsolutePath().isBlank())
            throw new MappingPluginException(MappingPluginState.INVALID_INPUT, "Empty input!");
        File[] plugJars = plugDir.listFiles(new JARFileFilter());
        if (plugJars == null || plugJars.length < 1)
            throw new MappingPluginException(MappingPluginState.NOT_FOUND, "No plugins found.");
        ClassLoader cl = new URLClassLoader(PluginLoader.fileArrayToURLArray(plugJars));
        List<Class<IMappingPlugin>> plugClasses = PluginLoader.extractClassesFromJARs(plugJars, cl);

        List<IMappingPlugin> IMappingPluginList = PluginLoader.createPluggableObjects(plugClasses);
        Map<String, IMappingPlugin> result = new HashMap<>();
        for (IMappingPlugin i : IMappingPluginList) {
            System.out.println("Found Plugin: " + i.id());
            i.setup();
            result.put(i.id(), i);
        }

        return result;
    }


    private static URL[] fileArrayToURLArray(File[] files) throws MalformedURLException {

        URL[] urls = new URL[files.length];
        for (int i = 0; i < files.length; i++) {
            urls[i] = files[i].toURI().toURL();
        }
        return urls;
    }

    private static List<Class<IMappingPlugin>> extractClassesFromJARs(File[] jars, ClassLoader cl) throws IOException, MappingPluginException {

        List<Class<IMappingPlugin>> classes = new ArrayList<>();
        for (File jar : jars) {
            classes.addAll(PluginLoader.extractClassesFromJAR(jar, cl));
        }
        return classes;
    }

    private static List<Class<IMappingPlugin>> extractClassesFromJAR(File jar, ClassLoader cl) throws IOException, MappingPluginException {

        List<Class<IMappingPlugin>> classes = new ArrayList<>();
        JarInputStream jaris = new JarInputStream(new FileInputStream(jar));
        JarEntry ent;
        while ((ent = jaris.getNextJarEntry()) != null) {
            if (ent.getName().toLowerCase().endsWith(".class")) {
                try {
                    Class<?> cls = cl.loadClass(ent.getName().substring(0, ent.getName().length() - 6).replace('/', '.'));
                    if (PluginLoader.isPluggableClass(cls)) {
                        classes.add((Class<IMappingPlugin>) cls);
                    }
                } catch (ClassNotFoundException e) {
                    LOG.info("Can't load Class " + ent.getName());
                    throw new MappingPluginException(MappingPluginState.UNKNOWN_ERROR, "Can't load Class " + ent.getName(), e);
                }
            }
        }
        jaris.close();
        return classes;
    }

    private static boolean isPluggableClass(Class<?> cls) {

        for (Class<?> i : cls.getInterfaces()) {
            if (i.equals(IMappingPlugin.class)) {
                return true;
            }
        }
        return false;
    }

    private static List<IMappingPlugin> createPluggableObjects(List<Class<IMappingPlugin>> pluggable) throws MappingPluginException {
        List<IMappingPlugin> plugs = new ArrayList<>(pluggable.size());
        for (Class<IMappingPlugin> plug : pluggable) {
            try {
                plugs.add(plug.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                LOG.info("Can't instantiate plugin: " + plug.getName());
                throw new MappingPluginException(MappingPluginState.UNKNOWN_ERROR, "Can't instantiate plugin: " + plug.getName(), e);
            } catch (IllegalAccessException e) {
                LOG.info("IllegalAccess for plugin: " + plug.getName());
                throw new MappingPluginException(MappingPluginState.UNKNOWN_ERROR, "IllegalAccess for plugin: " + plug.getName(), e);
            }
        }
        return plugs;
    }
}
