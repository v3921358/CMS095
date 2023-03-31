package tools;

import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class reDirect {/*
 * Console重定向到JTextArea
     */
    public reDirect() {
        JFrame jf = new JFrame();
        jf.setBounds(100, 100, 700, 400);
        JTextArea jta = new JTextArea();
        jta.setFont(new Font(null, Font.BOLD, 20));
        jta.setEditable(false);//设置不可编辑
        JTextAreaOutputStream out = new JTextAreaOutputStream(jta);
        System.setOut(new PrintStream(out));//设置输出重定向 
        System.setErr(new PrintStream(out));//将错误输出也重定向,用于e.pritnStackTrace
        JScrollPane jsp = new JScrollPane(jta);//设置滚动条
        jf.add(jsp);
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        reDirect rd = new reDirect();
        System.out.println("重定向");
        System.out.println(12345);
        System.out.println(true);
        try {
            int[] s = {1, 2, 3, 4, 5};
            int i = s[8];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class JTextAreaOutputStream extends OutputStream {

    private final JTextArea destination;

    public JTextAreaOutputStream(JTextArea destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination is null");
        }

        this.destination = destination;
    }

    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException {
        final String text = new String(buffer, offset, length);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                destination.append(text);
            }
        });
    }

    @Override
    public void write(int b) throws IOException {
        write(new byte[]{(byte) b}, 0, 1);
    }
}
