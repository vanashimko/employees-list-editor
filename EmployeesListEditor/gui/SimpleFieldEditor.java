package EmployeesListEditor.gui;

import EmployeesListEditor.utils.FieldDescription;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class SimpleFieldEditor extends FieldEditor {
    private JTextField textField;

    SimpleFieldEditor(Object o, FieldDescription fieldDescription){
        this.object = o;
        this.fieldDescription = fieldDescription;
        textField = new JTextField();
        getValueFromObject();
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                saveValueToObject();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                saveValueToObject();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                saveValueToObject();
            }
        });
    }

    @Override
    protected void writeToControl(Object object) {
        textField.setText((object == null)?"":object.toString());
    }

    @Override
    protected String readFromControl() {
        return textField.getText();
    }

    @Override
    public JComponent getControl() {
        return textField;
    }
}
