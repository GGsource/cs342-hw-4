import java.io.File;
import java.util.HashSet;

public class ProxyCivillianFetcher implements FileFetcher {
    //Use an instance of the unrestricted fetcher even within civillian
    private FileFetcher fetcher = new UnrestrictedFetcher();
    private static File f = new File("./src/main/resources");
    private static String[] directoryFiles = f.list();
    private static HashSet<String> classifiedFiles;
    static {
        //Create a list of all files in the resources folder
        for (String file : directoryFiles) {
            //If it has "classified" in the file name, put it on our list
            if (file.toLowerCase().contains("classified")) {
                classifiedFiles.add(file);
            }
        }
    }
    

    @Override
    public String fetchFile(String fileName) throws Exception {
        if (classifiedFiles.contains(fileName)) {
            throw new Exception("THIS FILE IS CLASSIFIED, MOVE ON CITIZEN.");
        }

        return fetcher.fetchFile(fileName);
    }
    
    
}
