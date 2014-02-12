import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;

public class MappedInputStream extends InputStream {
    private MappedByteBuffer mappedByteBuffer;

    public MappedInputStream(MappedByteBuffer mappedByteBuffer) {
        this.mappedByteBuffer = mappedByteBuffer;
    }

    @Override
    public int read() throws IOException {
        if (!mappedByteBuffer.hasRemaining())
            return -1;

        return mappedByteBuffer.get();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (!mappedByteBuffer.hasRemaining())
            return -1;

        int size = Math.min(len, mappedByteBuffer.remaining());
        mappedByteBuffer.get(b, off, size);

        return size;
    }
}
