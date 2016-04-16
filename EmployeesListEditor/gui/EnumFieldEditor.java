package EmployeesListEditor.gui;

import EmployeesListEditor.utils.FieldDescription;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class EnumFieldEditor extends FieldEditor {
    private JComboBox<Enum> comboBox;
    private Map<Enum, String> enumConstantsLocalizedNames;

    EnumFieldEditor(Object o, FieldDescription fieldDescription) {
        this.object = o;
        this.fieldDescription = fieldDescription;
        Enum[] enumValues = (Enum[]) fieldDescription.getClassType().getEnumConstants();
        enumConstantsLocalizedNames = createEnumConstantsLocalizedNames(enumValues);
        comboBox = new JComboBox<>(enumValues);
        comboBox.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    Enum enumValue = (Enum)value;
                    setText(enumConstantsLocalizedNames.get((enumValue)));
                }
                return this;
            }
        });
        getValueFromObject();
        comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                saveValueToObject();
            }
        });
    }

    @Override
    protected String readFromControl() {
        return comboBox.getSelectedItem().toString();
    }

    @Override
    protected void writeToControl(Object object) {
        comboBox.setSelectedItem(object);
    }

    @Override
    public JComponent getControl() {
        return comboBox;
    }

    private Map<Enum, String> createEnumConstantsLocalizedNames(Enum[] enumValues){
        Map<Enum, String> result = new HashMap<>();
        for (Enum value: enumValues){
            Class<? extends Enum> enumType = value.getClass();
            String localizedName = value.toString();
            try{
                Field enumConstant = enumType.getField(value.toString());
                if (enumConstant.isAnnotationPresent(LocalizedName.class)){
                    localizedName = enumConstant.getAnnotation(LocalizedName.class).value();
                }
            } catch (NoSuchFieldException e){
                //Then simply print enumValue.toString()
            }
            result.put(value, localizedName);
        }
        return result;
    }
}
