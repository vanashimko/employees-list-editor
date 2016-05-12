package EmployeesListEditor.gui;

import EmployeesListEditor.plugins.Plugin;
import EmployeesListEditor.plugins.PluginInfo;
import EmployeesListEditor.serializers.Serializer;
import EmployeesListEditor.serializers.SerializerInfo;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SaveMethodChooser extends JFileChooser {
    private JPanel pluginTypePanel;

    private class SerializerFileFilter extends FileFilter {
        private Class<? extends Serializer> serializerClass;
        private SerializerInfo serializerInfo;

        SerializerFileFilter(Class<? extends Serializer> serializer, SerializerInfo serializerInfo) {
            this.serializerClass = serializer;
            this.serializerInfo = serializerInfo;
        }

        @Override
        public boolean accept(File f) {
            return f.getName().endsWith("." + serializerInfo.extension());
        }

        @Override
        public String getDescription() {
            return serializerInfo.name() + " (*." + serializerInfo.extension() + ")";
        }

        Serializer getSerializer(){
            Serializer result = null;
            try {
                result = serializerClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return result;
        }

        String getExtension(){
            return serializerInfo.extension();
        }
    }

    SaveMethodChooser(List<Class<? extends Serializer>> serializers, List<PluginInfo> plugins) {
        Map<Class<? extends Serializer>, SerializerInfo> availableSerializers = getAvailableSerializers(serializers);

        setAcceptAllFileFilterUsed(false);

        for (Map.Entry<Class<? extends Serializer>, SerializerInfo> entry : availableSerializers.entrySet()) {
            addChoosableFileFilter(new SerializerFileFilter(entry.getKey(), entry.getValue()));
        }

        createPluginTypeCombobox(plugins);

        setCurrentDirectory(Paths.get(".").toFile());
    }

    Serializer getSerializer() {
        return ((SerializerFileFilter)getFileFilter()).getSerializer();
    }

    @Override
    public int showDialog(Component parent, String approveButtonText) throws HeadlessException {
        this.pluginTypePanel.setVisible((getDialogType() == SAVE_DIALOG));
        return super.showDialog(parent, approveButtonText);
    }

    private void createPluginTypeCombobox(List<PluginInfo> plugins){
        final int BUTTONS_PANEL_INDEX = 3;

        JPanel bottomPanel = (JPanel)getComponent(BUTTONS_PANEL_INDEX);
        Component buttonsPanel = bottomPanel.getComponent(BUTTONS_PANEL_INDEX);
        bottomPanel.remove(3);

        bottomPanel.add(Box.createVerticalStrut(5));
        this.pluginTypePanel = new JPanel();
        pluginTypePanel.setLayout(new BoxLayout(pluginTypePanel, BoxLayout.LINE_AXIS));
        bottomPanel.add(pluginTypePanel);

        bottomPanel.add(buttonsPanel);

        JLabel pluginTypeLabel = new JLabel("Plugin:");
        pluginTypePanel.add(pluginTypeLabel);

        JComboBox<PluginInfo> pluginTypeCombobox = new JComboBox<>(plugins.toArray(new PluginInfo[plugins.size()]));
        pluginTypeCombobox.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null){
                    PluginInfo plugin = (PluginInfo)value;
                    setText(plugin.getPluginName());
                }
                return this;
            }
        });
        pluginTypePanel.add(pluginTypeLabel);
        pluginTypePanel.add(Box.createHorizontalStrut(5));
        pluginTypePanel.add(pluginTypeCombobox);
    }

    private Map<Class<? extends Serializer> , SerializerInfo> getAvailableSerializers(List<Class<? extends Serializer>> serializers) {
        Map<Class<? extends Serializer>, SerializerInfo> result = new HashMap<>();
        for (Class<? extends Serializer> serializer : serializers) {
            if (serializer.isAnnotationPresent(SerializerInfo.class)) {
                result.put(serializer, serializer.getAnnotation(SerializerInfo.class));
            }
        }
        return result;
    }

    String getExtension(){
        return ((SerializerFileFilter)getFileFilter()).getExtension();
    }
}
