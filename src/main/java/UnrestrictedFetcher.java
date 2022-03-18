import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UnrestrictedFetcher implements FileFetcher {
    @Override
    public String fetchFile(String fileName) throws IOException {
        String fullpath = "./src/main/resources/textfiles/" + fileName;
        Path path = Path.of(fullpath);
        return Files.readString(path);
    }
    @Override
    public String fetchFile(String fileName, boolean isTest) throws IOException {
        String testString = "";
        if (isTest)
            testString="test";
        else
            testString="main";
        String fullpath = "./src/" + testString + "/resources/textfiles/" + fileName;
        Path path = Path.of(fullpath);
        return Files.readString(path);
    }
}
