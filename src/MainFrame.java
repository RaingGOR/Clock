import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

            }
        });
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelTime.setText(getStringNowDate("HH:mm:ss"));
                labelDate.setText(getStringNowDate("dd MMMM yyyy"));
                labelDay.setText(getStringNowDate("EEEE"));

                if (responseTime == null) return;
                if (responseTime.before(new GregorianCalendar())) {
                    responseTime = null;
                    playSoundFromResourse("ding.wav");
                    JOptionPane.showMessageDialog(MainFrame.this, paramFrame.getMessage(), "ALARM", JOptionPane.INFORMATION_MESSAGE);
                }
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
        Image newimg = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
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

                        if (paramFrame.getNotificationType() == dialogPreferens.NotificationType.NONE) {
                            responseTime = null;
                            preferensButton.setIcon(new ImageIcon("resources/images/gear.png"));
                            return;
                        }
                        responseTime = new GregorianCalendar();
                        //alarm
                        if (paramFrame.getNotificationType() == dialogPreferens.NotificationType.ALARM) {
                            preferensButton.setIcon(new ImageIcon("resources/images/alarm.png"));
                            responseTime.set(Calendar.HOUR_OF_DAY, paramFrame.getAlarmHour());
                            responseTime.set(Calendar.MINUTE, paramFrame.getAlarmMinute());
                            responseTime.set(Calendar.SECOND, 0);

                            if (responseTime.before(new GregorianCalendar())) {
                                responseTime.add(Calendar.DAY_OF_MONTH, 1);
                            }
                        }
                        //timer
                        if (paramFrame.getNotificationType() == dialogPreferens.NotificationType.TIMER) {
                            preferensButton.setIcon(new ImageIcon("resources/images/timer.png"));
                            responseTime.add(Calendar.HOUR, paramFrame.getTimerHour());
                            responseTime.add(Calendar.MINUTE, paramFrame.getTimerMinute());
                        }
                    }
                });

            }
        });

    }

    public void playSoundFromResourse(String filePath) {
        String fullFilePath = "resources//sound//" + filePath;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File((fullFilePath)));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private GregorianCalendar responseTime;

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
