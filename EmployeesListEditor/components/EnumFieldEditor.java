package EmployeesListEditor.components;

import EmployeesListEditor.utils.FieldDescription;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumFieldEditor extends FieldEditor {
    private JComboBox comboBox;

    private static Enum[] getEnumValues(Class<?> enumType){
        try {
            Method values = enumType.getMethod("values");
            return (Enum[])values.invoke(null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
        return null;
    }

    public EnumFieldEditor(Object o, FieldDescription fieldDescription) {
        this.object = o;
        this.fieldDescription = fieldDescription;

        comboBox = new JComboBox(getEnumValues(fieldDescription.getFieldType()));
        getValueFromObject();
        comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
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
}
