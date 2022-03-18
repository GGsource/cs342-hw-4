import java.io.File;
import java.util.HashSet;

public class ProxyCivillianFetcher implements FileFetcher {
    //Use an instance of the unrestricted fetcher even within civillian
    private FileFetcher fetcher = new UnrestrictedFetcher();
    private File f;
    private static HashSet<String> classifiedFiles = new HashSet<>();

    @Override
    public String fetchFile(String fileName) throws Exception {
        restrictedFilesBuilder(false);
        if (classifiedFiles.contains(fileName)) {
            throw new Exception("THIS FILE IS CLASSIFIED, MOVE ON CITIZEN.");
        }
        return fetcher.fetchFile(fileName);
    }
    
    @Override
    public String fetchFile(String fileName, boolean isTest) throws Exception {
        restrictedFilesBuilder(isTest);
        if (classifiedFiles.contains(fileName)) {
            throw new Exception("THIS FILE IS CLASSIFIED, MOVE ON CITIZEN.");
        }
        return fetcher.fetchFile(fileName, isTest);
    }

    private void restrictedFilesBuilder(boolean isTest) {
        if (isTest)
            f = new File("./src/test/resources/textfiles");
        else
            f = new File("./src/main/resources/textfiles");
        String[] directoryFiles = f.list();
        //Create a list of all files in the resources folder
        for (String file : directoryFiles) {
            //If it has "classified" in the file name, put it on our list
            if (file.toLowerCase().contains("classified")) {
                classifiedFiles.add(file);
            }
        }
    }
}
