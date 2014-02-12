import info.maskd.splitter.FilePart;
import info.maskd.splitter.FileSplitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        int cpuCount = Runtime.getRuntime().availableProcessors();

        FileSplitter fileSplitter = new FileSplitter(args[0]);
        FilePart[] fileParts = fileSplitter.splitByParts(cpuCount);

        ExecutorService executorService = Executors.newFixedThreadPool(cpuCount);

        List<Future<Long>> futures = new ArrayList<>();
        for(FilePart filePart: fileParts) {
            futures.add(executorService.submit(new LineCalculator(filePart)));
        }

        long newLineCount = 0;
        for (Future<Long> future: futures) {
            newLineCount += future.get();
        }

        System.out.println(newLineCount);

        executorService.shutdown();
    }
}
