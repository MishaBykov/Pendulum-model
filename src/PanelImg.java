import javax.swing.*;
import java.awt.*;

class PanelImg {
    JPanel panel = new JPanel();

    Rku rku;
    PanelImg(final TrajectorySolution trajectorySolution, final Pendulum pendulum ) {
        GridLayout gl = new GridLayout(1, 2);
        gl.setHgap(10);
        panel.setLayout(gl);
        panel.add(trajectorySolution);
        panel.add(pendulum);
    }
}
