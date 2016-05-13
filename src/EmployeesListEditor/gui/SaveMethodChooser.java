package EmployeesListEditor.gui;

import EmployeesListEditor.plugins.Plugin;
import EmployeesListEditor.plugins.PluginInfo;
import EmployeesListEditor.serializers.SaveMethod;
import EmployeesListEditor.serializers.SerializationException;
import EmployeesListEditor.serializers.Serializer;
import EmployeesListEditor.serializers.SerializerInfo;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveMethodChooser extends JFileChooser {
    private JPanel pluginTypePanel;
    private JComboBox<PluginInfo> pluginTypeCombobox;
    private List<PluginInfo> plugins;

    private class SerializerFileFilter extends FileFilter {
        private Class<? extends Serializer> serializerClass;
        private SerializerInfo serializerInfo;

        SerializerFileFilter(Class<? extends Serializer> serializer, SerializerInfo serializerInfo) {
            this.serializerClass = serializer;
            this.serializerInfo = serializerInfo;
        }

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()){
                return true;
            }

            String fileName = f.getName();

            switch (SaveMethodChooser.this.getDialogType()) {
                case SAVE_DIALOG:
                    String validExtension = SaveMethodChooser.this.getSelectedPlugin().getPluginExtension();
                    if (validExtension.isEmpty()) {
                        validExtension = serializerInfo.extension();
                    }
                    return fileName.endsWith("." + validExtension);
                case OPEN_DIALOG:
                    for (PluginInfo pluginInfo : plugins) {
                        if (fileName.endsWith("." + pluginInfo.getPluginExtension())) {
                            return true;
                        }
                    }
                    break;
                default:
                    break;
            }


            return fileName.endsWith("." + serializerInfo.extension());
        }

        @Override
        public String getDescription() {
            final String EXTENSION_SEPARATOR = ", *.";

            String pluginExtension;
            String result = serializerInfo.name() + " (*.";
            switch (SaveMethodChooser.this.getDialogType()) {
                case SAVE_DIALOG:
                    pluginExtension = SaveMethodChooser.this.getSelectedPlugin().getPluginExtension();
                    if (!pluginExtension.isEmpty()) {
                        result += pluginExtension;
                    } else {
                        result += serializerInfo.extension();
                    }
                    break;
                case OPEN_DIALOG:
                    result += serializerInfo.extension();
                    for (PluginInfo pluginInfo : plugins) {
                        pluginExtension = pluginInfo.getPluginExtension();
                        if (!pluginExtension.isEmpty()) {
                            result += EXTENSION_SEPARATOR + pluginExtension;
                        }
                    }
                    break;
                default:
                    break;
            }
            return result + ")";
        }

        private Plugin getOpenerPlugin() throws SerializationException {
            String fileName = getSelectedFile().getAbsolutePath();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

            if (extension.equals(serializerInfo.extension())) {
                return null;
            }

            for (PluginInfo plugin : plugins) {
                if (plugin.getPluginExtension().equals(extension)) {
                    return plugin.getInstance();
                }
            }
            throw new SerializationException("No plugin for such file type.");
        }

        Serializer getSerializer() throws SerializationException {
            Serializer serializer;

            try {
                serializer = serializerClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new SerializationException(e);
            }

            if (SaveMethodChooser.this.getDialogType() == OPEN_DIALOG) {
                Plugin openerPlugin = getOpenerPlugin();
                if (openerPlugin != null) {
                    serializer = new SaveMethod(serializer, getOpenerPlugin());
                }
            }

            return serializer;
        }

        public String getExtension() {
            String pluginExtension = SaveMethodChooser.this.getSelectedPlugin().getPluginExtension();
            return pluginExtension.isEmpty() ? serializerInfo.extension() : pluginExtension;
        }
    }

    private PluginInfo getSelectedPlugin() {
        return (PluginInfo) pluginTypeCombobox.getSelectedItem();
    }

    SaveMethodChooser(List<Class<? extends Serializer>> serializers, List<PluginInfo> plugins) {
        Map<Class<? extends Serializer>, SerializerInfo> availableSerializers = getAvailableSerializers(serializers);

        this.plugins = new ArrayList<>();
        this.plugins.add(PluginInfo.getNoPluginMarker());
        this.plugins.addAll(plugins);
        createPluginTypeCombobox();

        setAcceptAllFileFilterUsed(false);

        for (Map.Entry<Class<? extends Serializer>, SerializerInfo> entry : availableSerializers.entrySet()) {
            addChoosableFileFilter(new SerializerFileFilter(entry.getKey(), entry.getValue()));
        }

        setCurrentDirectory(Paths.get(".").toFile());
    }

    public Serializer getSerializer() throws SerializationException {
        Serializer serializer = ((SerializerFileFilter) getFileFilter()).getSerializer();
        if (getDialogType() == SAVE_DIALOG) {
            Plugin selectedPlugin = getSelectedPlugin().getInstance();
            if (selectedPlugin != null) {
                serializer = new SaveMethod(serializer, selectedPlugin);
            }
        }
        return serializer;
    }

    @Override
    public int showDialog(Component parent, String approveButtonText) throws HeadlessException {
        this.pluginTypePanel.setVisible((getDialogType() == SAVE_DIALOG));
        return super.showDialog(parent, approveButtonText);
    }

    private void createPluginTypeCombobox() {
        final int BUTTONS_PANEL_INDEX = 3;

        JPanel bottomPanel = (JPanel) getComponent(BUTTONS_PANEL_INDEX);
        Component buttonsPanel = bottomPanel.getComponent(BUTTONS_PANEL_INDEX);
        bottomPanel.remove(3);

        bottomPanel.add(Box.createVerticalStrut(5));
        this.pluginTypePanel = new JPanel();
        pluginTypePanel.setLayout(new BoxLayout(pluginTypePanel, BoxLayout.LINE_AXIS));
        bottomPanel.add(pluginTypePanel);

        bottomPanel.add(buttonsPanel);

        JLabel pluginTypeLabel = new JLabel("Plugin:");
        pluginTypePanel.add(pluginTypeLabel);

        pluginTypeCombobox = new JComboBox<>(plugins.toArray(new PluginInfo[plugins.size()]));
        pluginTypeCombobox.addItemListener(e -> {
            rescanCurrentDirectory();
            repaint();
        });
        pluginTypeCombobox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    setText(((PluginInfo) value).getPluginName());
                }
                return this;
            }
        });
        pluginTypePanel.add(pluginTypeLabel);
        pluginTypePanel.add(Box.createHorizontalStrut(5));
        pluginTypePanel.add(pluginTypeCombobox);
    }

    private Map<Class<? extends Serializer>, SerializerInfo> getAvailableSerializers(List<Class<? extends Serializer>> serializers) {
        Map<Class<? extends Serializer>, SerializerInfo> result = new HashMap<>();
        for (Class<? extends Serializer> serializer : serializers) {
            if (serializer.isAnnotationPresent(SerializerInfo.class)) {
                result.put(serializer, serializer.getAnnotation(SerializerInfo.class));
            }
        }
        return result;
    }

    public String getExtension() {
        return ((SerializerFileFilter) getFileFilter()).getExtension();
    }
}
