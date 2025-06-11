import java.io.*;
import java.util.List;

public class ConvertSrt {
    public static String FILE_PATH = "D:\\CapCut SRT\\";
    public static String DRAFT_PATH = "D:\\CapCut Drafts\\";
    public static String BAIDU_PATH = "https://fanyi.baidu.com/mtpe-individual/multimodal#/";
    private static final String ZIMU = "修改成视频文件同名";
    private static final String trafeFilePath = FILE_PATH + "draft_content.json";
    public static String ZIMU_SRT = FILE_PATH + ZIMU + ".srt";
    public static String ZIMU_TXT = FILE_PATH + ZIMU + ".txt";
    public static String RN = "\r\n";
    public static int TYPE_SRT = 0;
    public static int TYPE_TEXT = 1;
    public static int TYPE_ALL = 2;

    public static void convert(int type) {
        try {
            switch (type) {
                case 0:
                    String srt = convertJSON2SRT(trafeFilePath, TYPE_SRT);
                    saveStringToFile(srt, ZIMU_SRT);
                    break;
                case 1:
                    String text = convertJSON2SRT(trafeFilePath, TYPE_TEXT);
                    saveStringToFile(text, ZIMU_TXT);
                    break;
                case 2:
                    String srt1 = convertJSON2SRT(trafeFilePath, TYPE_SRT);
                    saveStringToFile(srt1, ZIMU_SRT);
                    String text1 = convertJSON2SRT(trafeFilePath, TYPE_TEXT);
                    saveStringToFile(text1, ZIMU_TXT);
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public static void saveStringToFile(String str, String fileName) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")) {
            writer.write(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式化为 SRT
     *
     * @param index 字幕序号，从 1 开始
     * @param srt   字幕内容等信息
     * @returns {string}
     */
    public static String formatSrt(int index, SrtModel srt, int type) {
        switch (type) {
            case 0:
                return index + RN + srt.getStart() + " --> " + srt.getEnd() + RN + srt.getContent() + RN + RN;
            case 1:
                return srt.getContent() + RN;
        }
        return srt.getContent() + RN;
    }


    public static String convertJSON2SRT(String jsonContent, int type) throws Exception {
        StringBuilder srtFiles = new StringBuilder();
        JsonConvert jsonConvert = new JsonConvert(jsonContent);
        List<SrtModel> list = jsonConvert.getSrtModels();
        System.out.println("SrtModel.size :" + list.size());
        for (int i = 0; i < list.size(); i++) {
            srtFiles.append(formatSrt(i + 1, list.get(i), type));
        }
        return srtFiles.toString();
    }

}
