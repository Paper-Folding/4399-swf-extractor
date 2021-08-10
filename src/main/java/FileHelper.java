import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class FileHelper {

    public static void WriteToFile(String fileLocation, String content) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(fileLocation));
            FileChannel fileChannel = fileOutputStream.getChannel();
            ByteBuffer outPut = Charset.forName("utf-8").encode(content);
            fileChannel.write(outPut);
        }
        catch (IOException ignored) {

        }
        finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String ReadFromFile(String fileLocation) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String result = "";
        try {
            fileReader = new FileReader(new File(fileLocation));
            bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null)
                result += line;
        }
        catch (IOException ignored) {

        }
        finally {
            if (bufferedReader != null)
                try {
                    bufferedReader.close();
                }
                catch (IOException ignored) {
                }
            if (fileReader != null)
                try {
                    fileReader.close();
                }
                catch (IOException ignored) {
                }
        }
        return result;
    }
}
