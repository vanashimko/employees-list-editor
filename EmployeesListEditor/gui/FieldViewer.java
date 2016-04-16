package EmployeesListEditor.gui;

import EmployeesListEditor.utils.FieldDescription;
import EmployeesListEditor.utils.FieldsExtractor;

import javax.swing.*;

class FieldViewer extends JPanel {
    FieldViewer(Object object, FieldDescription fieldDescription) {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        GroupLayout.SequentialGroup btnHorizontalGroup = layout.createSequentialGroup();
        layout.setHorizontalGroup(btnHorizontalGroup);

        GroupLayout.ParallelGroup btnVerticalGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        layout.setVerticalGroup(btnVerticalGroup);

        JLabel lblFieldName = new JLabel(fieldDescription.getName());
        btnHorizontalGroup.addComponent(lblFieldName);
        btnVerticalGroup.addComponent(lblFieldName);

        JComponent fieldEditor =createControl(object, fieldDescription);
        btnHorizontalGroup.addComponent(fieldEditor);
        btnVerticalGroup.addComponent(fieldEditor);

    }

    private JComponent createControl(Object o, FieldDescription fieldDescription) {
        Class<?> fieldType = fieldDescription.getClassType();
        FieldEditor fieldEditor;
        if (isEditableWithTextField(fieldType)) {
            fieldEditor = new SimpleFieldEditor(o, fieldDescription);
        } else if (fieldType.isEnum()) {
            fieldEditor = new EnumFieldEditor(o, fieldDescription);
        } else return null;

        return fieldEditor.getControl();

    }

    private boolean isEditableWithTextField(Class<?> fieldType) {
        return (FieldsExtractor.isBoxed(fieldType) && !fieldType.equals(Boolean.class)) || (fieldType.isPrimitive() && !fieldType.equals(boolean.class)) || fieldType.equals(String.class);
    }
}
