import javax.swing.*;
import java.awt.*;

class Run {
    public static void main(String[] args) {
        final Rku rku = new Rku(3, 1, 1, 1, 1, 0.1,100, Math.PI/4, 0.001);
        final TrajectorySolution trajectorySolution = new TrajectorySolution(Color.black, 1, rku, false);
        final Pendulum pendulum = new Pendulum(Color.black, 1, rku);
        final SliderText[] sliderTexts = new SliderText[9];
        initMSliderText(sliderTexts, rku);
        final JPanel mSliderText = new JPanel(new GridLayout(0, 2));
        for (SliderText sliderText : sliderTexts) {
            mSliderText.add(sliderText.panel);
        }


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final JFrame frame = new JFrame("Kursach");
                JPanel all = new JPanel();
                PanelImg panelImg = new PanelImg(trajectorySolution, pendulum);
                all.add(panelImg.panel);
                PanelButton panelButton = new PanelButton(trajectorySolution, pendulum, sliderTexts);
                all.add(mSliderText);
                all.add(panelButton.panel);
                frame.getContentPane().add(all);
                frame.setSize(1050, 740);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

//    g = 9.8, m, l, c, alpha, nu, theta;

    private static void initMSliderText(SliderText[] mSL, Rku rku) {
        mSL[0] = new SliderText(rku, 0, "x0", 10);
        mSL[1] = new SliderText(rku, 2, "ускор. своб. падения", 10);
        mSL[2] = new SliderText(rku, 1, "y0", 10);
        mSL[3] = new SliderText(rku, 3, "масса", 10);
        mSL[4] = new SliderText(rku, 7, "частота", 1);
        mSL[5] = new SliderText(rku, 5, "сопротивление", 10);
        mSL[6] = new SliderText(rku, 6, "амплитуда", 10);
        mSL[7] = new SliderText(rku, 4, "длина подвеса", 10);
        mSL[8] = new SliderText(rku, 8, "theta", 100);

        mSL[0].slider.setVisible(false);
        mSL[1].slider.setVisible(false);
        mSL[2].slider.setVisible(false);
        mSL[4].slider.setMaximum(500);
        mSL[8].slider.setMaximum(314);
        mSL[4].textField.setText("" + rku.getParameters(7));
        mSL[8].textField.setText("" + rku.getParameters(8));
    }
}

