import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

public class TxtToSrt {
    private static final String FINAL_PATH = ConvertSrt.FILE_PATH + "最终翻译文件.srt";

    public static void convert() {
        int i = 0;
        List<String> translatorList = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ConvertSrt.ZIMU_TXT), StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                translatorList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder contentSb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ConvertSrt.ZIMU_SRT), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (line.contains("--")) {
                    contentSb.append(i + 1);
                    contentSb.append(ConvertSrt.RN);
                    contentSb.append(line.trim());
                    contentSb.append(ConvertSrt.RN);
                    contentSb.append(translatorList.get(i).trim());
                    contentSb.append(ConvertSrt.RN + ConvertSrt.RN);
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConvertSrt.saveStringToFile(contentSb.toString(), FINAL_PATH);
        System.out.println("任務完成");
    }
}