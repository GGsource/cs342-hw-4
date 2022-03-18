import java.io.File;
import java.util.HashSet;

public class ProxyOfficialFetcher implements FileFetcher {
    private FileFetcher fetcher = new UnrestrictedFetcher();
    private static File f = new File("./src/main/resources/textfiles");
    private static String[] directoryFiles = f.list();
    private static HashSet<String> funFiles = new HashSet<>();

    static {
        //Create a list of all files in the resources folder
        for (String file : directoryFiles) {
            //If it has "entertainment" in the file name, put it on our list
            if (file.toLowerCase().contains("entertainment")) {
                funFiles.add(file);
            }
        }
    }

    @Override
    public String fetchFile(String fileName) throws Exception {
        if (funFiles.contains(fileName)) {
            //Throw error if official tries to access a fun file
            throw new Exception("No slacking on the job, agent!");
        }
        //Otherwise get file contents as usual.
        return fetcher.fetchFile(fileName);
    }
    
}
