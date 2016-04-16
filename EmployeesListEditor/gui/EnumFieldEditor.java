package EmployeesListEditor.gui;

import EmployeesListEditor.utils.FieldDescription;

import javax.swing.*;
import java.awt.event.ItemEvent;

class EnumFieldEditor extends FieldEditor {
    private JComboBox<Enum> comboBox;

    EnumFieldEditor(Object o, FieldDescription fieldDescription) {
        this.object = o;
        this.fieldDescription = fieldDescription;
        Enum[] enumValues = (Enum[]) fieldDescription.getClassType().getEnumConstants();
        comboBox = new JComboBox<>(enumValues);
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
}
