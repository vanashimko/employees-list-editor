import EmployeesListEditor.plugins.Plugin;
import EmployeesListEditor.serializers.SerializerInfo;

import java.io.InputStream;
import java.io.OutputStream;

@SerializerInfo(name = "GZip", extension = "gz")
public class GZIPPlugin implements Plugin {
    @Override
    public void compress(InputStream in, OutputStream out) {
        System.out.println("gzip");
    }

    @Override
    public void decompress(InputStream in, OutputStream out) {

    }

    @Override
    public void test() {
        System.out.println(getClass().getName());
    }
}
