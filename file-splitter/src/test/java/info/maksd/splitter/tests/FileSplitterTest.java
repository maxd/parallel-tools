package info.maksd.splitter.tests;

import info.maskd.splitter.FilePart;
import info.maskd.splitter.FileSplitter;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class FileSplitterTest {

    private String emptyFileName = this.getClass().getResource("/empty.txt").getFile();
    private String oneLineFileName = this.getClass().getResource("/1-new-line.txt").getFile();
    private String twoLineFileName = this.getClass().getResource("/2-new-line.txt").getFile();
    private String threeLineFileName = this.getClass().getResource("/3-new-line.txt").getFile();

    @Test(expected = IllegalArgumentException.class)
    public void pass_null_file_name() {
        new FileSplitter(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void pass_empty_file_name() {
        new FileSplitter("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void pass_non_existent_file_name() {
        new FileSplitter("unknown.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void split_empty_file_by_0_part() throws IOException {
        FileSplitter fileSplitter = new FileSplitter(emptyFileName);
        fileSplitter.splitByParts(0);
    }

    @Test
    public void split_empty_file_by_1_part() throws IOException {
        FileSplitter fileSplitter = new FileSplitter(emptyFileName);
        FilePart[] fileParts = fileSplitter.splitByParts(1);

        Assert.assertEquals(1, fileParts.length);
        assertFilePart(emptyFileName, 0, 0, fileParts[0]);
    }

    @Test
    public void split_one_new_line_file_by_1_part() throws IOException {
        FileSplitter fileSplitter = new FileSplitter(oneLineFileName);
        FilePart[] fileParts = fileSplitter.splitByParts(1);

        Assert.assertEquals(1, fileParts.length);
        assertFilePart(oneLineFileName, 0, 1, fileParts[0]);
    }

    @Test
    public void split_one_new_line_file_by_2_part() throws IOException {
        FileSplitter fileSplitter = new FileSplitter(oneLineFileName);
        FilePart[] fileParts = fileSplitter.splitByParts(2);

        Assert.assertEquals(1, fileParts.length);
        assertFilePart(oneLineFileName, 0, 1, fileParts[0]);
    }

    @Test
    public void split_two_new_line_file_by_1_part() throws IOException {
        FileSplitter fileSplitter = new FileSplitter(twoLineFileName);
        FilePart[] fileParts = fileSplitter.splitByParts(1);

        Assert.assertEquals(1, fileParts.length);
        assertFilePart(twoLineFileName, 0, 2, fileParts[0]);
    }

    @Test
    public void split_two_new_line_file_by_2_part() throws IOException {
        FileSplitter fileSplitter = new FileSplitter(twoLineFileName);
        FilePart[] fileParts = fileSplitter.splitByParts(2);

        Assert.assertEquals(2, fileParts.length);
        assertFilePart(twoLineFileName, 0, 1, fileParts[0]);
        assertFilePart(twoLineFileName, 1, 2, fileParts[1]);
    }

    @Test
    public void split_two_new_line_file_by_3_part() throws IOException {
        FileSplitter fileSplitter = new FileSplitter(twoLineFileName);
        FilePart[] fileParts = fileSplitter.splitByParts(3);

        Assert.assertEquals(2, fileParts.length);
        assertFilePart(twoLineFileName, 0, 1, fileParts[0]);
        assertFilePart(twoLineFileName, 1, 2, fileParts[1]);
    }

    @Test
    public void split_three_new_line_file_by_1_part() throws IOException {
        FileSplitter fileSplitter = new FileSplitter(threeLineFileName);
        FilePart[] fileParts = fileSplitter.splitByParts(1);

        Assert.assertEquals(1, fileParts.length);
        assertFilePart(threeLineFileName, 0, 3, fileParts[0]);
    }

    @Test
    public void split_three_new_line_file_by_2_part() throws IOException {
        FileSplitter fileSplitter = new FileSplitter(threeLineFileName);
        FilePart[] fileParts = fileSplitter.splitByParts(2);

        Assert.assertEquals(2, fileParts.length);
        assertFilePart(threeLineFileName, 0, 2, fileParts[0]);
        assertFilePart(threeLineFileName, 2, 3, fileParts[1]);
    }

    @Test
    public void split_three_new_line_file_by_3_part() throws IOException {
        FileSplitter fileSplitter = new FileSplitter(threeLineFileName);
        FilePart[] fileParts = fileSplitter.splitByParts(3);

        Assert.assertEquals(3, fileParts.length);
        assertFilePart(threeLineFileName, 0, 1, fileParts[0]);
        assertFilePart(threeLineFileName, 1, 2, fileParts[1]);
        assertFilePart(threeLineFileName, 2, 3, fileParts[2]);
    }

    private void assertFilePart(String fileName, int startOffset, int endOffset, FilePart filePart) {
        Assert.assertEquals(fileName, filePart.getFileName());
        Assert.assertEquals(startOffset, filePart.getStartOffset());
        Assert.assertEquals(endOffset, filePart.getEndOffset());
    }

}
