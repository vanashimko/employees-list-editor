package EmployeesListEditor.plugins;

import java.io.InputStream;
import java.io.OutputStream;

public interface Plugin {
    void compress(InputStream in, OutputStream out);
    void decompress(InputStream in, OutputStream out);

    void test();
}
