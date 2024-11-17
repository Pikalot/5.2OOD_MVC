import java.util.ArrayList;

public class GraphTester {
    public static void main(String[] args) {
        ArrayList<Double> data = new ArrayList<>();

        data.add(33.0);
        data.add(44.0);
        data.add(22.0);
        data.add(22.0);

        GraphModel model = new GraphModel(data);
        TextFrame textFrame = new TextFrame(model);
        BarFrame graphFrame = new BarFrame(model);
    }
}
