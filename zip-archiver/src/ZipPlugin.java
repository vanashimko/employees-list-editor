import EmployeesListEditor.plugins.Plugin;

import java.io.InputStream;
import java.io.OutputStream;

public class ZipPlugin implements Plugin {
    @Override
    public void compress(InputStream in, OutputStream out) {

    }

    @Override
    public void decompress(InputStream in, OutputStream out) {

    }

    @Override
    public void test() {
        System.out.println(getClass().getName());
    }
}
