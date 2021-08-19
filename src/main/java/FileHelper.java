import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * FileHelper Ultimate Version will the support of java NIO and jdk 11.
 *
 * @author Paper Folding
 */
public class FileHelper {
    public static void WriteToFile(String fileLocation, String content) {
        Path path = Paths.get(fileLocation);
        try {
            Files.writeString(path, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
        catch (IOException ignored) {
        }
    }

    public static String ReadFromFile(String fileLocation) {
        Path path = Paths.get(fileLocation);
        try {
            String result = Files.readString(path, StandardCharsets.UTF_8);
            return result;
        }
        catch (IOException ignore) {
        }
        return "";
    }

    public static void CreateDirectoryIfNotExists(String location) {
        Path path = Paths.get(location);
        if (Files.notExists(path)) {
            try {
                Files.createDirectory(path);
            }
            catch (IOException ignored) {
            }
        }
    }

    public static void DownloadFile(String link, String storePath, String storeFilename) {
        URL url;
        ReadableByteChannel rbc = null;
        FileOutputStream fos = null;
        try {
            url = new URL(link);
            rbc = Channels.newChannel(url.openStream());
            fos = new FileOutputStream(storePath + storeFilename);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        catch (MalformedURLException ignored) {
        }
        catch (IOException ignored) {
        }
        finally {
            try {
                if (rbc != null)
                    rbc.close();
                if (fos != null)
                    fos.close();
            }
            catch (IOException ignored) {
            }
        }
    }
}
