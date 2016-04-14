package EmployeesListEditor.components;

import EmployeesListEditor.utils.FieldDescription;
import EmployeesListEditor.utils.FieldsExtractor;

import javax.swing.*;

public class FieldViewer extends JPanel {
    private JLabel lblFieldName;
    private FieldEditor fieldEditor;
    private Object object;
    private FieldDescription fieldDescription;

    public FieldViewer(Object object, FieldDescription fieldDescription) {
        this.object = object;
        this.fieldDescription = fieldDescription;
        lblFieldName = new JLabel(fieldDescription.getName());
        add(createControl(object, fieldDescription));
    }

    public JComponent createControl(Object o, FieldDescription fieldDescription) {
        Class<?> fieldType = fieldDescription.getFieldType();
        FieldEditor fieldEditor;
        if (FieldsExtractor.isBoxed(fieldType) && !fieldType.equals(Boolean.class)) {
            fieldEditor = new SimpleFieldEditor(o, fieldDescription);
        } else if (fieldType.isEnum()) {
            fieldEditor = new EnumFieldEditor(o, fieldDescription);
        } else return null;

        return fieldEditor.getControl();

    }
}
