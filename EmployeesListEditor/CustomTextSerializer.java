package EmployeesListEditor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CustomTextSerializer implements Serializer {
    @Override
    public void serialize(Object o, OutputStream outputStream) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        writeObject(o, outputStreamWriter);
        outputStreamWriter.close();

    }

    @Override
    public Object deserialize(InputStream inputStream) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return readObject(scanner.next());
    }

    private void writeObject(Object o, OutputStreamWriter outputStreamWriter) throws IOException{
        if (o == null){
            outputStreamWriter.append("null,");
            return;
        }
        outputStreamWriter.append("{");
        String className = o.getClass().getCanonicalName();
        switch (FieldsExtractor.getFieldType(o.getClass())){
            case PRIMITIVE:
                outputStreamWriter.append("Primitive:");
                outputStreamWriter.append(className).append(":");
                outputStreamWriter.append("\"").append(o.toString()).append("\"");
                break;
            case LIST:
                outputStreamWriter.append("List:");
                outputStreamWriter.append(className).append(":");
                outputStreamWriter.append("[");
                for (Object listItem : (List) o) {
                    writeObject(listItem, outputStreamWriter);
                }
                outputStreamWriter.append("]");
                break;
            case ENUM:
                outputStreamWriter.append("Enum:");
                outputStreamWriter.append(className).append(":");
                outputStreamWriter.append("\"").append(o.toString()).append("\"");
                break;
            case OBJECT:
                outputStreamWriter.write("Object:");
                outputStreamWriter.append(className).append(":");
                outputStreamWriter.append("[");
                ArrayList<FieldDescription> fields = FieldsExtractor.getFields(o);
                for (FieldDescription field : fields){
                    outputStreamWriter.append(field.getName()).append(":");
                    writeObject(field.getFieldValue(), outputStreamWriter);
                }
                outputStreamWriter.append("]");
                break;
        }
        outputStreamWriter.append("}");
    }

    private static Object readObject(String inputString) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
//        int startIndex = inputString.indexOf('{') + 1;
//        int endIndex = inputString.indexOf(':', startIndex);
//        String itemType = inputString.substring(startIndex, endIndex);
//
//        startIndex = endIndex + 1;
//        endIndex = inputString.indexOf(':', startIndex);
//        String className = inputString.substring(startIndex, endIndex);
//        switch (itemType){
//            case "Primitive":
//                startIndex = endIndex + 1;
//                endIndex = getBlockEnd(inputString, startIndex, '"', '"');
//                String value = inputString.substring(startIndex, endIndex);
//                switch (className){
//                    case "java.lang.Integer":
//                        return Integer.valueOf(value);
//                    case "java.lang.String":
//                        return value;
//                }
//                break;
//            case "List":
//                ArrayList<Object> list = new ArrayList<>();
//                startIndex = endIndex + 1;
//                endIndex = getBlockEnd(inputString, startIndex, '[', ']');
//                String listBlock = inputString.substring(startIndex, endIndex);
//                startIndex = 0;
//                while ((startIndex = listBlock.indexOf("{", startIndex)) != -1){
//                    endIndex = getBlockEnd(listBlock, startIndex, '{', '}');
//                    list.add(readObject(listBlock.substring(startIndex, endIndex)));
//                    startIndex = endIndex + 1;
//                }
//                return list;
//            case "Object":
//                Object o = createObjectByClassName(className);
//                startIndex = endIndex + 1;
//                endIndex = getBlockEnd(inputString, startIndex, '[', ']');
//                String objectBlock = inputString.substring(startIndex + 1, endIndex);
//                startIndex = 0;
//                endIndex = 0;
//                HashMap<String, Object> fields = new HashMap<>();
//                while (startIndex = objectBlock.indexOf(':'))
//
//
//
//        }
        return null;
    }

    private static String getType(String inputString){
        return inputString.substring(inputString.indexOf('{') + 1, inputString.indexOf(':'));
    }

    private static String getClassName(String inputString){
        return inputString.substring(inputString.indexOf(':') + 1, inputString.indexOf(':'));
    }

    private static Object createObjectByClassName(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException{
        Class classDefinition = Class.forName(className);
        return classDefinition.newInstance();
    }

    private static int getBlockEnd(String inputString, int startIndex, char startChar, char endChar){
        int nestingLevel = 1;
        int i = inputString.indexOf(startChar, startIndex) + 1;
        while (!(nestingLevel == 0 && inputString.charAt(i) == endChar)){
            if (inputString.charAt(i) == startChar){
                nestingLevel++;
            } else if (inputString.charAt(i) == endChar){
                nestingLevel--;
            }
            i++;
        }
        return i;
    }

    private static String getCollectionBlock(String inputString, int startIndex){
        return inputString.substring(startIndex + 1, getBlockEnd(inputString, startIndex, '[', ']'));
    }

    private static String getItemBlock(String inputString, int startIndex){
        return inputString.substring(startIndex + 1, getBlockEnd(inputString, startIndex, '{', '}'));
    }

    private static String getPrimitiveValueBlock(String inputString, int startIndex){
        return inputString.substring(startIndex + 1, getBlockEnd(inputString, startIndex, '"', '"'));
    }
}
