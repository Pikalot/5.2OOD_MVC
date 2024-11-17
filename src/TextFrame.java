import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class TextFrame extends JFrame implements GraphController, GraphObserver {
    private GraphModel model;
    private ArrayList<Double> values;
    private JTextField[] fields;
    private double value;
    private int index;

    public TextFrame(GraphModel model) {
        this.model = model;
        values = this.model.getData();
        fields = new JTextField[values.size()];
        this.model.addObserver(this);

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField currentField = (JTextField) e.getSource();
                index = 0;

                while (index < values.size() && fields[index] != currentField) {
                    index++;
                }

                try {
                    value = Double.parseDouble(currentField.getText().trim());
                    notifyModel();
                }
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Incorrect numeric format!");
                }
            }
        };

        for (int i = 0; i < values.size(); i++) {
            JTextField field = new JTextField(values.get(i).toString(), 15);
            field.addActionListener(listener);
            fields[i] = field;
            this.add(field);
        }

        setLocation(300, 300);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        pack();
        setVisible(true);
    }

    @Override
    public void notifyModel() {
        this.model.updateData(index, value);
    }

    @Override
    public void update() {
        this.values = this.model.getData();
        for (int i = 0; i < values.size(); i++) {
            fields[i].setText(this.values.get(i).toString());
        }
    }
}