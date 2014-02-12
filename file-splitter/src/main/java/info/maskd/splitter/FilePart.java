package info.maskd.splitter;

public class FilePart {
    private String fileName;
    private long startOffset;
    private long endOffset;

    public FilePart(String fileName, long startOffset, long endOffset) {
        this.fileName = fileName;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    public String getFileName() {
        return fileName;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public long getEndOffset() {
        return endOffset;
    }
}
