package EmployeesListEditor.serializers;

import EmployeesListEditor.utils.FieldDescription;
import EmployeesListEditor.utils.FieldsExtractor;
import EmployeesListEditor.utils.ReflectHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@SerializerInfo(name = "Text", extension = "txt")
public class CustomTextSerializer implements Serializer {
    private int nestingLevel = 0;
    @Override
    public void serialize(Object o, OutputStream outputStream) throws SerializationException {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            nestingLevel = 0;
            writeObject(o, outputStreamWriter);
            outputStreamWriter.flush();
        } catch (Exception e) {
            throw new SerializationException(e);
        }

    }

    @Override
    public Object deserialize(InputStream inputStream) throws SerializationException {
        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        String inputString = clearWhitespaces(scanner.next());
        Object result;
        try {
            result = readObject(inputString);
        } catch (Exception e){
            throw new SerializationException(e);
        }
        return result;
    }

    private void writeObject(Object o, OutputStreamWriter outputStreamWriter) throws IOException {

        appendFormat(outputStreamWriter, "{");
        nestingLevel++;

        if (o == null) {
            appendFormat(outputStreamWriter, "null");
        } else {
            String className = o.getClass().getName();
            FieldDescription.FieldType fieldType = FieldsExtractor.getFieldType(o.getClass());
            String header = fieldType.toString() + ":" + className + ":";
            switch (fieldType) {
                case PRIMITIVE:
                    appendFormat(outputStreamWriter, header + "\"" + o.toString() + "\"");
                    break;
                case LIST:
                    appendFormat(outputStreamWriter, header);
                    nestingLevel++;
                    appendFormat(outputStreamWriter, "[");
                    nestingLevel++;
                    for (Object listItem : (List) o) {
                        writeObject(listItem, outputStreamWriter);
                    }
                    nestingLevel--;
                    appendFormat(outputStreamWriter, "]");
                    nestingLevel--;
                    break;
                case OBJECT:
                    appendFormat(outputStreamWriter, header);
                    nestingLevel++;
                    appendFormat(outputStreamWriter, "[");
                    nestingLevel++;
                    ArrayList<FieldDescription> fields = FieldsExtractor.getFields(o);
                    for (FieldDescription field : fields) {
                        appendFormat(outputStreamWriter, field.getName() + ":");
                        writeObject(field.getFieldValue(), outputStreamWriter);
                    }
                    nestingLevel--;
                    appendFormat(outputStreamWriter, "]");
                    nestingLevel--;
                    break;
            }
        }
        nestingLevel--;
        appendFormat(outputStreamWriter, "}");
    }

    private Object readObject(String inputString) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        if (inputString.equals("{null}")){
            return null;
        }
        int startIndex = inputString.indexOf('{') + 1;
        int endIndex = inputString.indexOf(':', startIndex);
        String inputStringName = inputString.substring(startIndex, endIndex);
        FieldDescription.FieldType fieldType = FieldDescription.FieldType.valueOf(inputStringName);

        startIndex = endIndex + 1;
        endIndex = inputString.indexOf(':', startIndex);
        String className = inputString.substring(startIndex, endIndex);
        startIndex = endIndex + 1;
        switch (fieldType) {
            case PRIMITIVE:
                endIndex = inputString.indexOf('"', startIndex + 1);
                String value = inputString.substring(startIndex + 1, endIndex);
                return ReflectHelper.createPrimitiveObject(className, value);
            case LIST:
                ArrayList<Object> list = new ArrayList<>();
                endIndex = getBlockEnd(inputString, startIndex, '[', ']');
                String listBlock = inputString.substring(startIndex + 1, endIndex);
                startIndex = 0;
                while ((startIndex = listBlock.indexOf("{", startIndex)) != -1) {
                    endIndex = getBlockEnd(listBlock, startIndex, '{', '}');
                    list.add(readObject(listBlock.substring(startIndex, endIndex)));
                    startIndex = endIndex + 1;
                }
                return list;
            case OBJECT:
                startIndex = endIndex + 1;
                endIndex = getBlockEnd(inputString, startIndex, '[', ']');
                String objectBlock = inputString.substring(startIndex + 1, endIndex);
                endIndex = 0;
                HashMap<String, Object> fields = new HashMap<>();
                while ((startIndex = objectBlock.indexOf(':', endIndex)) != -1) {
                    String fieldName = objectBlock.substring(endIndex, startIndex);
                    startIndex++;
                    endIndex = getBlockEnd(objectBlock, startIndex, '{', '}');
                    Object fieldValue = readObject(objectBlock.substring(startIndex, endIndex + 1));
                    if (fieldValue != null) {
                        fields.put(fieldName, fieldValue);
                    }
                    endIndex++;
                }
                return constructObject(className, fields);
            default:
                return null;
        }
    }

    private static Object constructObject(String className, Map<String, Object> fields) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> classDefinition = Class.forName(className);
        Object o = classDefinition.newInstance();
        for (Map.Entry<String, Object> entry : fields.entrySet()){
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            String setterName = "set" + fieldName;
            Class<?> fieldType = fieldValue.getClass();
            Method setterMethod;
            try {
                setterMethod = classDefinition.getMethod(setterName, fieldType);
            } catch (NoSuchMethodException e){
                if (FieldsExtractor.isBoxed(fieldType)) {
                    setterMethod = classDefinition.getMethod(setterName, FieldsExtractor.getBoxedType(fieldType));
                } else {
                    throw e;
                }
            }
            setterMethod.invoke(o, entry.getValue());
        }
        return o;
    }

    private static int getBlockEnd(String inputString, int startIndex, char startChar, char endChar) {
        int nestingLevel = 1;
        int i = startIndex + 1;
        while (nestingLevel != 0) {
            if (inputString.charAt(i) == startChar) {
                nestingLevel++;
            } else if (inputString.charAt(i) == endChar) {
                nestingLevel--;
            }
            i++;
        }
        return i - 1;
    }

    private static String clearWhitespaces(String inputString){
        StringBuilder result = new StringBuilder(inputString);
        boolean isStr = false;
        for (int i = 0; i < result.length(); i++){
            char currentChar = result.charAt(i);
            if (currentChar == '"'){
                isStr = !isStr;
            } else if (Character.isWhitespace(currentChar) && !isStr){
                result.deleteCharAt(i);
                i--;
            }
        }

        return result.toString();
    }

    private void appendFormat(OutputStreamWriter out, String s) throws IOException{
        for (int i = 0; i < nestingLevel; i++){
            out.append('\t');
        }
        out.append(s);
        out.append('\n');
    }
}
