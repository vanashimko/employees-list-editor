package EmployeesListEditor.plugins;

public class PluginLoadException extends Exception {
    public PluginLoadException(){}

    public PluginLoadException(String message){
        super(message);
    }

    public PluginLoadException(Throwable cause){
        super(cause);
    }
}
