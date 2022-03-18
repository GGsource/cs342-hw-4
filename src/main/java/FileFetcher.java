public interface FileFetcher {
    
    public String fetchFile(String fileName) throws Exception;
    public String fetchFile(String fileName, boolean isTest) throws Exception;
}
