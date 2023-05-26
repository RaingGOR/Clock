import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class MainFrame extends JFrame {
    private dialogPreferens paramFrame;

    public JPanel contentPane;

    private String getStringNowDate(String format) {
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        return formatDate.format(new GregorianCalendar().getTime());
    }

    public MainFrame() {
        //default properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - 150, dimension.height / 2 - 150, 300, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        setContentPane(contentPane);
        setResizable(false);
        setTitle("Clocks");
        //panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(topPanel);
        //Labels
        JLabel labelTime = new JLabel();
        Font font = new Font("Times New Roman", Font.PLAIN, 60);
        labelTime.setText("--:--:--");
        labelTime.setFont(font);
        labelTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(labelTime);

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
        // timer add real time and date
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelTime.setText(getStringNowDate("HH:mm:ss"));
                labelDate.setText(getStringNowDate("dd MMMM yyyy"));
                labelDay.setText(getStringNowDate("EEEE"));
            }
        });
        timer.start();

        //add preferens
        JButton preferensButton = new JButton();
        preferensButton.setVisible(true);
        preferensButton.setText("");
        topPanel.add(preferensButton);

        ImageIcon imageIcon = new ImageIcon("images/preferencesIcon.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back
        preferensButton.setIcon(imageIcon); //add image in button
        preferensButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    paramFrame = new dialogPreferens(MainFrame.this);
                    paramFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                } catch (Exception z) {
                    z.printStackTrace();
                }
                preferensButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        paramFrame.setVisible(true);
                    }
                });

            }
        });

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
