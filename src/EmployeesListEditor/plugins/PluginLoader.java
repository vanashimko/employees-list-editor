package EmployeesListEditor.plugins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PluginLoader {
    public static List<PluginInfo> loadPlugins(String pluginsFolderPath){
        File pluginsFolder = new File(pluginsFolderPath);
        File [] jars = pluginsFolder.listFiles(file -> file.isFile() && file.getName().endsWith(".jar"));
        List<PluginInfo> plugins = new ArrayList<>();
        for (File file : jars){
            try{
                plugins.add(new PluginInfo(file));
            } catch (PluginLoadException e){
                System.err.println(e.getMessage());
            }
        }

        return plugins;
    }
}
