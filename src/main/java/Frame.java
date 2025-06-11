import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

public class Frame {
    public static void main(String[] args) {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("字幕转换工具");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1));
        JButton txtToSrtBtn = new JButton("txt文件转化srt");
        txtToSrtBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TxtToSrt.convert();
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(frame, "编译完成！");
                });
            }
        });

        JButton txtBtn = new JButton("生成txt文件");
        txtBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConvertSrt.convert(ConvertSrt.TYPE_TEXT);
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(frame, "编译完成！");
                });
            }
        });
        JButton srtBtn = new JButton("生成srt文件");
        srtBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConvertSrt.convert(ConvertSrt.TYPE_SRT);
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(frame, "编译完成！");
                });
            }
        });
        JButton allBtn = new JButton("生成所有文件");
        allBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConvertSrt.convert(ConvertSrt.TYPE_ALL);
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(frame, "编译完成！");
                });
            }
        });
        JPanel cards = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cards.add(txtBtn);
        cards.add(srtBtn);
        cards.add(allBtn);
        mainPanel.add(buildJTextPane("当前目录地址", ConvertSrt.FILE_PATH));
        mainPanel.add(buildJTextPane("当前素材地址", ConvertSrt.DRAFT_PATH));
        mainPanel.add(buildJTextPane("百度翻译地址", ConvertSrt.BAIDU_PATH));
        mainPanel.add(cards);
        mainPanel.add(txtToSrtBtn);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(mainPanel);
        frame.add(panel);
        frame.setLocationRelativeTo(null); // 居中显示
        frame.setVisible(true);
    }

    private static JTextPane buildJTextPane(String info, String urlPath) {
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText(String.format("<html>%2$s: <a href=%1$s'>%1$s</a></html>", urlPath, info));
        textPane.setEditable(false);
        textPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    try {
                        if (urlPath.contains("http")) {
                            Desktop.getDesktop().browse(new URL(urlPath).toURI());
                        } else {
                            Desktop.getDesktop().open(new File(urlPath));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        return textPane;
    }


}
