import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

class SliderText extends JFrame {

    final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    final JSlider slider = new JSlider();
    final JTextField textField = new JTextField();
    private int idParameter;
    private Rku rku;
    private double scale;

    ChangeListener cl = new ChangeListener() {
        public void stateChanged(ChangeEvent event) {
            JSlider source = (JSlider) event.getSource();
            textField.getDocument().removeDocumentListener(dl);
            textField.setText("" + source.getValue()/scale);
            textField.getDocument().addDocumentListener(dl);
        }
    };

    DocumentListener dl = new DocumentListener() {
        public void insertUpdate(DocumentEvent e) {
            if (textField.getText().matches("^\\d+\\.\\d+$") || (textField.getText().matches("^\\d+$"))) {
                double a = Double.parseDouble(textField.getText())*scale;
                String str = Double.toString(a);
                int b = Integer.parseInt(str.replaceAll("\\.\\d+$",""));
                if (a <= slider.getMaximum() && a >= slider.getMinimum()) {
                    slider.removeChangeListener(cl);
                    slider.setValue(b);
                    slider.addChangeListener(cl);
                }
            }
        }

        public void removeUpdate(DocumentEvent e) {
            if (textField.getText().matches("^\\d+\\.\\d+$") || (textField.getText().matches("^\\d+$"))) {
                double a = Double.parseDouble(textField.getText())*scale;
                String str = Double.toString(a);
                int b = Integer.parseInt(str.replaceAll("\\.\\d+$",""));
                if (a <= slider.getMaximum() && a >= slider.getMinimum()) {
                    slider.removeChangeListener(cl);
                    slider.setValue(b);
                    slider.addChangeListener(cl);
                }
            }
        }

        public void changedUpdate(DocumentEvent e) {
            if ((textField.getText().matches("^\\d+\\.\\d+$")) || (textField.getText().matches("^\\d+$"))) {
                double a = Double.parseDouble(textField.getText())*scale;
                String str = Double.toString(a);
                int b = Integer.parseInt(str.replaceAll("\\.\\d+$",""));
                if (a <= slider.getMaximum() && a >= slider.getMinimum()) {
                    slider.removeChangeListener(cl);
                    slider.setValue(b);
                    slider.addChangeListener(cl);
                }
            }
        }
    };

    void resetParameter(){
        if ((textField.getText().matches("^\\d+\\.\\d+$")) || (textField.getText().matches("^\\d+$"))) {
            rku.setParameters(Double.parseDouble(textField.getText()), idParameter);
        }
    }

    SliderText(Rku rku, int idParameter, String str, double scale) {
        this.scale = scale;
        this.rku = rku;
        this.idParameter = idParameter;
        panel.add(new JLabel(str));

        textField.setText(""+rku.getParameters(idParameter));
        textField.getDocument().addDocumentListener(dl);
        textField.setPreferredSize(new Dimension(70, 26));
        panel.add(textField);

        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.addChangeListener(cl);

        panel.add(slider);
    }
}

