package EmployeesListEditor.plugins;

public class NoPlugin extends PluginInfo {
    @Override
    public Plugin getInstance() {
        return null;
    }

    @Override
    public String getPluginName() {
        return "No plugin";
    }

    @Override
    public String getPluginExtension() {
        return "";
    }
}
