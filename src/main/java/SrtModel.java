public class SrtModel {
    private String content;
    private String start;
    private String end;

    public SrtModel() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStart(double time) {
        this.start = getSrtTimeText(time);
    }

    public void setEnd(double time) {
        this.end = getSrtTimeText(time);
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }


    private String getSrtTimeText(double time) {
        time = Math.floor(time / 1000);
        // 剩余毫秒
        String millisecond = Integer.toString((int) (time % 1000));
        time = Math.floor(time / 1000);
        // 剩余秒
        String second = Integer.toString((int) (time % 60));
        time = Math.floor(time / 60);
        // 剩余分钟
        String minute = Integer.toString((int) (time % 60));
        time = Math.floor(time / 60);
        // 剩余时数
        String hour = Integer.toString((int) time);
        hour = formatDigit(hour, 2);
        minute = formatDigit(minute, 2);
        second = formatDigit(second, 2);
        millisecond = formatDigit(millisecond, 3);
        return hour + ':' + minute + ':' + second + ',' + millisecond;
    }

    /**
     * 格式化数字
     *
     * @param digit  数字
     * @param length 长度
     * @returns {string}
     */
    private String formatDigit(String digit, int length) {
        String str = digit;
        while (str.length() < length) {
            str = '0' + str;
        }
        return str;
    }
}
