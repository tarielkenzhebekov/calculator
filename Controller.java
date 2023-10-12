import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Controller implements ActionListener {
    private Model model;

    public Controller(Viewer viewer) {
        model = new Model(viewer);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        model.validationAndGetInfixExpr(command);
    }
}