import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class MainFrame extends JFrame {
    public JPanel contentPane;

    public MainFrame() {
        //default properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - 150, dimension.height / 2 - 200, 300, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        setContentPane(contentPane);
        setResizable(false);
        //Labels
        JLabel labelTime = new JLabel();
        Font font = new Font("Times New Roman", Font.PLAIN, 60);
        labelTime.setText("--:--:--");
        labelTime.setFont(font);
        labelTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(labelTime);

        JLabel labelDate = new JLabel();
        labelDate.setFont(new Font("Times New Roman", Font.BOLD, 50));
        labelDate.setText("-- -- ----");
        labelDate.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(labelDate);

        JLabel labelDay = new JLabel();
        labelDay.setText("---");
        labelDay.setFont(new Font("Times New Roman", Font.BOLD, 40));
        labelDay.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(labelDay);
        // timer
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GregorianCalendar nowDate = new GregorianCalendar();
                SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
                String outTime = formatTime.format(nowDate.getTime());
                SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
                String outDate = formatDate.format(nowDate.getTime());
                SimpleDateFormat formatDay = new SimpleDateFormat("EEEE");
                String outDay = formatDay.format(nowDate.getTime());
                labelTime.setText(outTime);
                labelDate.setText(outDate);
                labelDay.setText(outDay);

            }
        });
        timer.start();
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}