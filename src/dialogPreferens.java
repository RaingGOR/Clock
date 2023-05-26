import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class dialogPreferens extends JDialog {
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
        setBounds(100, 100, 300, 600);
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
        JRadioButton nonAlert = new JRadioButton("Non alert         ");
        JRadioButton alarmClock = new JRadioButton("Alarm Clock      ");
        JRadioButton timer = new JRadioButton("Timer            ");
        nonAlert.setBackground(Color.white);
        alarmClock.setBackground(Color.white);
        timer.setBackground(Color.white);
        //set one select
        nonAlert.setSelected(true);
        //add in group
        trueAlert.add(nonAlert);
        trueAlert.add(alarmClock);
        trueAlert.add(timer);
        //add in panel
        alert.add(nonAlert);
        alert.add(alarmClock);
        alert.add(timer);
        alert.add(Box.createGlue());//added horizontal end glue

        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }

}
