import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonConvert {
    private JsonReader reader;
    private final List<SrtModel> srtModels = new ArrayList<>();
    private final Map<String, SrtTime> timeMap = new HashMap<>();
    private final List<String[]> textList = new ArrayList<>();

    public JsonConvert(String filePath) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            reader = new JsonReader(br);
            reader.setLenient(true);
            reader.beginObject();
            while (reader.hasNext()) {
                convert();
            }
            for (String[] text : textList) {
                if (text[0] != null) {
                    SrtModel model = new SrtModel();
                    SrtTime time = timeMap.get(text[0]);
                    model.setContent(text[1]);
                    model.setStart(time.start);
                    model.setEnd(time.start + time.duration);
                    srtModels.add(model);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            reader.close();
        }
    }

    public List<SrtModel> getSrtModels() {
        return srtModels;
    }

    private void convert() throws IOException {
        String rootName = reader.nextName();
        if ("materials".equals(rootName)) {
            nextObject(name -> {
                nextArray(() -> {
                    String[] strings = new String[2];
                    nextObject(name2 -> {
                        if ("id".equals(name2)) {
                            strings[0] = reader.nextString();
                        } else {
                            strings[1] = reader.nextString();
                        }
                    }, "id", "recognize_text");
                    if (strings[0] != null && strings[1] != null && !strings[1].isEmpty()) {
                        textList.add(strings);
                    }
                });
            }, "texts");
        } else if ("tracks".equals(rootName)) {
            nextArray(() -> {
                nextObject(name2 -> {
                    nextArray(() -> {
                        SrtTime time = new SrtTime();
                        nextObject(name3 -> {
                            if (name3.equals("material_id")) {
                                time.id = reader.nextString();
                            } else {
                                nextObject(name4 -> {
                                    if (name4.equals("duration")) {
                                        time.duration = reader.nextLong();
                                    } else {
                                        time.start = reader.nextLong();
                                    }
                                }, "duration", "start");
                            }
                        }, "material_id", "target_timerange");
                        if (time.id != null) {
                            timeMap.put(time.id, time);
                        }
                    });
                }, "segments");
            });
        } else {
            reader.skipValue();
        }
    }


    private void nextArray(OnLoadArray load) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            load.onLoading();
        }
        reader.endArray();
    }

    private void nextObject(OnLoadObject load, String... types) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            boolean isRun = false;
            for (String type : types) {
                if (name.equals(type)) {
                    load.onLoading(name);
                    isRun = true;
                }
            }
            if (!isRun) {
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    interface OnLoadObject {
        void onLoading(String data) throws IOException;
    }

    interface OnLoadArray {
        void onLoading() throws IOException;
    }
}
