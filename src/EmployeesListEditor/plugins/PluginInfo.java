package EmployeesListEditor.plugins;

import EmployeesListEditor.utils.IterableEnumeration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class PluginInfo {
    private final String PROPERTIES_FILE = "plugin.properties";
    private final String CLASS_PROPERTY = "class";
    private final String NAME_PROPERTY = "name";
    private final String EXTENSION_PROPERTY = "extension";
    private Plugin instance;
    private String pluginName;
    private String pluginExtension;

    private static final PluginInfo noPluginMarker = new PluginInfo();

    private PluginInfo() {
        pluginName = "No plugin";
        pluginExtension = "";
        instance = null;
    }

    PluginInfo(File file) throws PluginLoadException {
        try {
            Properties properties = getPluginProperties(file);
            if (properties == null) {
                throw new PluginLoadException("No properties file found.");
            }

            String pluginClassName = properties.getProperty(CLASS_PROPERTY);
            if (pluginClassName == null || pluginClassName.isEmpty()) {
                throw new PluginLoadException("Missing main class property.");
            }

            pluginName = properties.getProperty(NAME_PROPERTY);
            if (pluginName == null) {
                pluginName = pluginClassName;
            }

            pluginExtension = properties.getProperty(EXTENSION_PROPERTY);
            if (pluginExtension == null || pluginExtension.isEmpty()) {
                throw new PluginLoadException("Missing plugin extension property.");
            }

            URL jarURL = file.toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
            Class pluginClass = classLoader.loadClass(pluginClassName);
            instance = (Plugin) pluginClass.newInstance();
        } catch (Exception e) {
            throw new PluginLoadException(e);
        }
    }

    public Plugin getInstance() {
        return instance;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getPluginExtension() {
        return pluginExtension;
    }

    private Properties getPluginProperties(File file) throws IOException {
        Properties result = null;
        JarFile jarFile = new JarFile(file);
        IterableEnumeration<JarEntry> entries = new IterableEnumeration<>(jarFile.entries());
        for (JarEntry entry : entries) {
            if (entry.getName().equals(PROPERTIES_FILE)) {
                try (InputStream in = jarFile.getInputStream(entry)){
                    result = new Properties();
                    result.load(in);
                }
            }
        }
        return result;
    }

    public static PluginInfo getNoPluginMarker() {
        return noPluginMarker;
    }
}
