import info.maskd.splitter.FilePart;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep implements Callable<Void> {
    private FilePart filePart;
    private String pattern;

    public Grep(FilePart filePart, String pattern) {
        this.filePart = filePart;
        this.pattern = pattern;
    }

    @Override
    public Void call() throws Exception {
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(filePart.getFileName(), "r")) {
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, filePart.getStartOffset(), filePart.getEndOffset() - filePart.getStartOffset());

            mappedByteBuffer.load();

            MappedInputStream mappedInputStream = new MappedInputStream(mappedByteBuffer);
            InputStreamReader inputStreamReader = new InputStreamReader(mappedInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            Pattern compiledPattern = Pattern.compile(pattern);

            String line;
            while((line = bufferedReader.readLine()) != null) {
                Matcher matcher = compiledPattern.matcher(line);

                if (matcher.find()) {
                    System.out.println(line);
                }
            }

            mappedByteBuffer.clear();

            bufferedReader.close();
        }

        return null;
    }
}
