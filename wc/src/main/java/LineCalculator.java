import info.maskd.splitter.FilePart;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.Callable;

public class LineCalculator implements Callable<Long> {
    private FilePart filePart;

    public LineCalculator(FilePart filePart) {
        this.filePart = filePart;
    }

    @Override
    public Long call() throws Exception {
        long result = 0;

        try(RandomAccessFile randomAccessFile = new RandomAccessFile(filePart.getFileName(), "r")) {
            MappedByteBuffer mappedByteBuffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, filePart.getStartOffset(), filePart.getEndOffset() - filePart.getStartOffset());

            mappedByteBuffer.load();

            for (long i = 0; i < mappedByteBuffer.limit(); i++) {
                if (mappedByteBuffer.get() == '\n') {
                    result += 1;
                }
            }

            mappedByteBuffer.clear();
        }

        return result;
    }
}
