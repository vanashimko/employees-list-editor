import EmployeesListEditor.plugins.Plugin;
import EmployeesListEditor.serializers.SerializerInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


@SerializerInfo(name = "GZIP", extension = "gz")
public class GZIPPlugin implements Plugin {
    @Override
    public void compress(InputStream in, OutputStream out) throws IOException{
        final int BUFFER = 2048;
        byte buffer[] = new byte[BUFFER];
        GZIPOutputStream zos = new GZIPOutputStream(out);

        int length;
        while ((length = in.read(buffer)) > 0) {
            zos.write(buffer, 0, length);
        }
        zos.close();
    }

    @Override
    public void decompress(InputStream in, OutputStream out) throws IOException{
        GZIPInputStream zin = new GZIPInputStream(in);

        final int BUFFER = 2048;
        byte buffer[] = new byte[BUFFER];

        int size;
        while ((size = zin.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, size);
        }
    }
}
