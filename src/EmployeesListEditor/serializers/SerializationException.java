package EmployeesListEditor.serializers;

public class SerializationException extends Exception {
    public SerializationException(){}

    public SerializationException(String message){
        super(message);
    }

    public SerializationException(Throwable cause){
        super(cause);
    }


}
