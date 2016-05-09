package EmployeesListEditor.plugins;

import java.io.InputStream;
import java.io.OutputStream;

public interface Archiver {
    void compress(OutputStream outputStream);
    void decompress(OutputStream outputStream);
}
