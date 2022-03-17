import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UnrestrictedFetcher implements FileFetcher {

    @Override
    public String fetchFile(String fileName) throws IOException{
        Path path = Path.of(fileName);
        return Files.readString(path);
    }
    
}
