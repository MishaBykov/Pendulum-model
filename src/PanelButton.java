import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PanelButton {
    public JPanel panel = new JPanel();
    private TrajectorySolution trajectorySolution;
    private Pendulum pendulum;
    private final JButton buttonStartStop = new JButton();
    private final JButton buttonReset = new JButton();
    private final JButton buttonClear = new JButton();
    private SliderText[] masSliderText;

    private ActionListener startStop = new ActionListener() {
        private boolean pulsing = false;
        @Override
        public void actionPerformed(ActionEvent e) {
            if (pulsing) {
                pulsing = false;
                trajectorySolution.stop();
                pendulum.stop();
                buttonStartStop.setText("Start");
            } else {
                pulsing = true;
                trajectorySolution.start();
                pendulum.start();
                buttonStartStop.setText("Stop");
            }
        }
    };

    private ActionListener reset = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (SliderText aMasSliderText : masSliderText) {
                aMasSliderText.resetParameter();
            }
            trajectorySolution.clear();
        }
    };

    private ActionListener clear = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            trajectorySolution.clear();
        }
    };

    PanelButton(final TrajectorySolution trajectorySolution, final Pendulum pendulum, SliderText[] masSliderText) {
        this.trajectorySolution = trajectorySolution;
        this.pendulum = pendulum;
        this.masSliderText = masSliderText;
        GridLayout gl = new GridLayout(3, 1);
        gl.setVgap(10);
        panel.setLayout(gl);

        buttonStartStop.setText("Start");
        buttonStartStop.setPreferredSize(new Dimension(70, 26));
        buttonStartStop.addActionListener(startStop);
        panel.add(buttonStartStop);

        buttonReset.setText("Reset");
        buttonReset.setPreferredSize(new Dimension(70, 26));
        buttonReset.addActionListener(reset);
        panel.add(buttonReset);

        buttonClear.setText("Clear");
        buttonClear.addActionListener(clear);
        panel.add(buttonClear);

    }
}
