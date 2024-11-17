import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class BarFrame extends JFrame implements GraphObserver, GraphController {
    private GraphModel model;
    private ArrayList<Double> values;
    private int iconWidth = 300;
    private int iconHeight = 100;
    private int index;
    private double value;

    public BarFrame(GraphModel model) {
        this.model = model;
        this.values = model.getData();
        this.model.addObserver(this);

        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(500, 300);
        ArrayList<Path2D> paths = new ArrayList<>();

        Icon barIcon = new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g;
                double barHeight = getIconHeight() / values.size();
                double maxWidth = getMax();
                paths.clear();

                for (int i = 0; i < values.size(); i++) {
                    double barWidth = values.get(i) * getIconWidth()/maxWidth;
                    Rectangle2D bar = new Rectangle2D.Double(0, i * barHeight, barWidth, barHeight);
                    Path2D path2D = new Path2D.Double();
                    path2D.append(bar, false);
                    paths.add(path2D);

                    g2.setColor(Color.DARK_GRAY);
                    g2.fill(bar);
                    g2.setColor(Color.orange);
                    g2.draw(bar);
                }
            }

            @Override
            public int getIconWidth() {
                return iconWidth;
            }

            @Override
            public int getIconHeight() {
                return iconHeight;
            }
        };

        JLabel barLabel = new JLabel(barIcon);
        barLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < paths.size(); i++) {
                    if (paths.get(i).contains(0, e.getY())) {
                        index = i;
                        value = e.getX() * getMax() / iconWidth;
                        notifyModel();
                    }
                }
            }
        });

        add(barLabel);
        pack();
        setVisible(true);
    }

    @Override
    public void notifyModel() {
        model.updateData(index, value);
    }

    @Override
    public void update() {
        this.values = model.getData();
        repaint();
    }

    private double getMax(){
        double max = 0;
        for (double v: values) {
            if (max < v) max = v;
        }
        return max;
    }
}