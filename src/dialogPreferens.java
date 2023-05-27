import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class dialogPreferens extends JDialog {
    private int alarmHour;
    private int alarmMinute;
    private int timerHour;
    private int timerMinute;
    private String message;
    private NotificationType notificationType;

    public int getTimerMinute() {
        return timerMinute;
    }

    public String getMessage() {
        return message;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public int getTimerHour() {
        return timerHour;
    }

    public int getAlarmMinute() {
        return alarmMinute;
    }

    public int getAlarmHour() {
        return alarmHour;
    }

    public enum NotificationType {
        NONE,
        ALARM,
        TIMER;
    }

    private int convertToInt(String value) {  //convert string -> int
        if (value.isEmpty()) return 0;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    private final JPanel contentPanel = new JPanel();

    void addCompForBorder(Border border,
                          String description,
                          Container container) {
        JPanel comp = new JPanel(new GridLayout(1, 1), false);
        JLabel label = new JLabel(description, JLabel.CENTER);
        comp.add(label);
        comp.setBorder(border);

        container.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(comp);
    }

    public dialogPreferens(JFrame owner) {
        super(owner, true);
        setAlwaysOnTop(true);
        setTitle("Preference");
        setBounds(100, 100, 300, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setBackground(Color.white);
        //add Panel ALERT
        JPanel alert = new JPanel();
        alert.setVisible(true);
        alert.setBorder(BorderFactory.createTitledBorder("Alert"));
        alert.setLayout(new BoxLayout(alert, BoxLayout.Y_AXIS));
        contentPanel.add(alert); //add in content
        alert.setBackground(Color.white);
        alert.setAlignmentX(Component.CENTER_ALIGNMENT);
        //add group button
        ButtonGroup trueAlert = new ButtonGroup();
        JRadioButton nonAlertRadioButton = new JRadioButton("Non alert         ");
        JRadioButton alarmClockRadioButton = new JRadioButton("Alarm Clock      ");
        JRadioButton timerRadioButton = new JRadioButton("Timer            ");
        nonAlertRadioButton.setBackground(Color.white);
        alarmClockRadioButton.setBackground(Color.white);
        timerRadioButton.setBackground(Color.white);
        //set one select
        nonAlertRadioButton.setSelected(true);
        //add in group
        trueAlert.add(nonAlertRadioButton);
        trueAlert.add(alarmClockRadioButton);
        trueAlert.add(timerRadioButton);
        //add in panel
        alert.add(nonAlertRadioButton);
        alert.add(alarmClockRadioButton);
        alert.add(timerRadioButton);
        alert.add(Box.createGlue());//added horizontal end glue
        //add Tabled Pane
        Font font = new Font("Verdana", Font.PLAIN, 10);
        JTabbedPane tabbedPane = new JTabbedPane();                 //tabled pain (main)
        tabbedPane.setFont(font);
        tabbedPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(tabbedPane);
//panels
        //ALarm clock panel
        //main panel
        JPanel panelAlarmClock = new JPanel();
        panelAlarmClock.setLayout(new BoxLayout(panelAlarmClock, BoxLayout.Y_AXIS));
        tabbedPane.addTab("Alarm Clock", panelAlarmClock);
        //text
        JLabel textAlarmClock = new JLabel();
        textAlarmClock.setText("Trigger Time (h:m)");
        panelAlarmClock.add(textAlarmClock);
        textAlarmClock.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel forClockPanel = new JPanel();
        forClockPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        forClockPanel.setLayout(new BoxLayout(forClockPanel, BoxLayout.X_AXIS));
        panelAlarmClock.add(forClockPanel);

        JTextField alarmHoursField = new JTextField();
//        hoursField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel colon = new JLabel();
        colon.setFont(font);
        colon.setAlignmentX(Component.CENTER_ALIGNMENT);
        colon.setText("  :  ");

        JTextField alarmMinutsField = new JTextField();
//        minutsField.setAlignmentX(Component.CENTER_ALIGNMENT);

        forClockPanel.add(alarmHoursField);
        forClockPanel.add(colon);
        forClockPanel.add(alarmMinutsField);


        //Timer panel
        JPanel panelTimer = new JPanel();
        tabbedPane.addTab("Timer", panelTimer);
        panelTimer.setLayout(new BoxLayout(panelTimer, BoxLayout.Y_AXIS));

        JLabel textTimer = new JLabel();
        textTimer.setText("Range Timer");
        panelTimer.add(textTimer);

        JPanel forTimer = new JPanel();
        forTimer.setLayout(new BoxLayout(forTimer, BoxLayout.X_AXIS));
        panelTimer.add(forTimer);

        JTextField timerHoursField = new JTextField();
        JTextField timerMinutsField = new JTextField();
        forTimer.add(timerHoursField);
        forTimer.add(new JLabel("  :  "));
        forTimer.add(timerMinutsField);


        //text MESSAGE
        JLabel textArea = new JLabel();
        textArea.setFont(font);
        textArea.setText("Message: ");
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(textArea);

        //added scroll and area text
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(10, 30, 360, 180);
        contentPanel.add(scrollPane);

        JTextArea areaText = new JTextArea();
        scrollPane.setViewportView(areaText);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        notificationType = NotificationType.ALARM;
                        message = areaText.getText();
                        //choose Alarm clock
                        if (alarmClockRadioButton.isSelected()) {
                            notificationType = NotificationType.ALARM;
                            alarmHour = convertToInt(alarmHoursField.getText());
                            alarmMinute = convertToInt(alarmMinutsField.getText());
                        }
                        //choose Timer
                        if (timerRadioButton.isSelected()) {
                            notificationType = NotificationType.TIMER;
                            timerHour = convertToInt(timerHoursField.getText());
                            timerMinute = convertToInt(timerMinutsField.getText());
                        }

                        setVisible(false);
                        dispose();
                    }
                });
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        dispose();
                    }
                });
            }
        }
    }

}
