package info.maskd.splitter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class FileSplitter {
    private String fileName;

    public FileSplitter(String fileName) {
        if (fileName == null || fileName.length() == 0 || !new File(fileName).exists()) {
            throw new IllegalArgumentException("File is not exists.");
        }

        this.fileName = fileName;
    }

    public FilePart[] splitByParts(int partCount) throws IOException {
        if (partCount < 1) {
            throw new IllegalArgumentException("The partCount argument must be great or equal than 1.");
        }

        File file = new File(fileName);
        long approximatePartSize = Math.max((long)Math.ceil((double) file.length() / (double) partCount), 1);

        List<FilePart> result = new ArrayList<>();

        try(RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            do {
                long startOffset = randomAccessFile.getFilePointer();

                long approximateEndOffset = Math.min(startOffset + approximatePartSize, file.length());
                randomAccessFile.seek(Math.max(approximateEndOffset - 1, 0));
                while(randomAccessFile.getFilePointer() < file.length() && randomAccessFile.readByte() != '\n');

                long endOffset = randomAccessFile.getFilePointer();

                result.add(new FilePart(fileName, startOffset, endOffset));
            } while(randomAccessFile.getFilePointer() < file.length());
        }

        return result.toArray(new FilePart[result.size()]);
    }
}
