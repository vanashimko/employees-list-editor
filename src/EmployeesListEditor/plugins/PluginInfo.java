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

public class PluginInfo {
    private final String PROPERTIES_FILE = "plugin.properties";
    private final String CLASS_PROPERTY = "class";
    private Plugin instance;

    public PluginInfo(File file) throws PluginLoadException{
        try{
            Properties properties = getPluginProperties(file);
            if (properties == null){
                throw new PluginLoadException("No properties file found.");
            }

            String pluginClassName = properties.getProperty(CLASS_PROPERTY);
            if (pluginClassName == null || pluginClassName.length() == 0){
                throw new PluginLoadException("Missing main class property.");
            }

            URL jarURL = file.toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
            Class pluginClasss = classLoader.loadClass(pluginClassName);
            instance = (Plugin) pluginClasss.newInstance();
        } catch (Exception e){
            throw new PluginLoadException(e);
        }
    }

    public Plugin getInstance(){
        return instance;
    }

    private Properties getPluginProperties(File file) throws IOException{
        Properties result = null;
        JarFile jarFile = new JarFile(file);
        IterableEnumeration<JarEntry> entries = new IterableEnumeration<>(jarFile.entries());
        for (JarEntry entry : entries){
            if (entry.getName().equals(PROPERTIES_FILE)){
                InputStream in = null;
                try{
                    in = jarFile.getInputStream(entry);
                    result = new Properties();
                    result.load(in);
                } finally {
                    if (in != null){
                        in.close();
                    }
                }
            }
        }
        return result;
    }
}