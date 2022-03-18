import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UnrestrictedFetcher implements FileFetcher {
    @Override
    public String fetchFile(String fileName) throws IOException{
        String fullpath = "./src/main/resources/textfiles/" + fileName;
        Path path = Path.of(fullpath);
        return Files.readString(path);
    }
}
