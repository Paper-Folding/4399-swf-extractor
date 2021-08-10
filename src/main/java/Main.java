import org.jsoup.Jsoup;
import us.codecraft.xsoup.Xsoup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println(" _  _     ____     ___     ___   ");
        System.out.println("| || |   |___ \\   / _ \\   / _ \\  ");
        System.out.println("| || |_    __) | | (_) | | (_) | ");
        System.out.println("|__   _|  |__ <   \\__, |  \\__, | ");
        System.out.println("   | |    ___) |    / /     / /  ");
        System.out.println("   |_|   |____/    /_/     /_/   ");
        System.out.println(" _______ ___   ___ .___________..______           ___        ______ .___________.  ______   .______      ");
        System.out.println("|   ____|\\  \\ /  / |           ||   _  \\         /   \\      /      ||           | /  __  \\  |   _  \\     ");
        System.out.println("|  |__    \\  V  /  `---|  |----`|  |_)  |       /  ^  \\    |  ,----'`---|  |----`|  |  |  | |  |_)  |    ");
        System.out.println("|   __|    >   <       |  |     |      /       /  /_\\  \\   |  |         |  |     |  |  |  | |      /     ");
        System.out.println("|  |____  /  .  \\      |  |     |  |\\  \\----. /  _____  \\  |  `----.    |  |     |  `--'  | |  |\\  \\----.");
        System.out.println("|_______|/__/ \\__\\     |__|     | _| `._____|/__/     \\__\\  \\______|    |__|      \\______/  | _| `._____|");
        System.out.println("\n        4399 提取器 版本 1.0 SNAPSHOT");
        System.out.println("                Developed by Paper-Folding\n");
        Scanner jin = new Scanner(System.in);
        System.out.println("请输入要下载的4399小游戏源页面:");
        String link = jin.nextLine().trim();
        String localStoragePath = FileHelper.ReadFromFile(".swfExtracto");
        if (localStoragePath == null || localStoragePath.equals("")) {
            System.out.println("请输入要保存的本地文件夹路径 (路径必须以正斜杠结尾，如: D:/swf/ ):");
            localStoragePath = jin.nextLine().replace("\\", "/").trim();
            System.out.println("是否记住保存路径(将在当前路径下生成一份\".swfExtracto\"文件, 若您日后想重新指定保存路径, 请删除此文件)?\n(Y/N)");
            String xx = jin.nextLine();
            if (xx.equals("Y") || xx.equals("y"))
                FileHelper.WriteToFile(".swfExtracto", localStoragePath);
        }
        System.out.println("开始提取并下载swf文件...");
        Map<String, Object> map = extractParameters(link);
        if (!map.get("swfUrl").toString().endsWith("swf")) {
            System.out.println("小游戏\"" + map.get("name").toString() + "\"无法被下载, 因为提取到的链接不是有效的swf文件!");
            return;
        }
        download(map.get("swfUrl").toString(), localStoragePath, map.get("name").toString() + ".swf");
        System.out.println("下载成功!文件已保存至 \"" + localStoragePath + map.get("name").toString() + ".swf\"");
    }

    public static Map<String, Object> extractParameters(String srcLink) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String srcDOM = Xsoup.compile("//script[@language=\"javascript\"]").evaluate(Jsoup.connect(srcLink).get()).get();
            Matcher matcher = Pattern.compile("_strGamePath(.+)").matcher(srcDOM);
            String swfUrl = "", gameName = "";
            while (matcher.find())
                swfUrl = "http://sda.4399.com/4399swf" + matcher.group(0).replace("_strGamePath=\"", "").replace("\";", "");
            matcher = Pattern.compile("title='(.+)';").matcher(srcDOM);
            while (matcher.find())
                gameName = matcher.group(0).replace("title='", "").replace("';", "");
            result.put("name", gameName);
            result.put("swfUrl", swfUrl);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return result;
        }
    }

    public static void download(String link, String storePath, String storeFilename) {
        int byteread = 0;
        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            inStream = conn.getInputStream();
            fs = new FileOutputStream(storePath + storeFilename);
            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1)
                fs.write(buffer, 0, byteread);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (inStream != null) {
                try {
                    inStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fs != null) {
                try {
                    fs.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
