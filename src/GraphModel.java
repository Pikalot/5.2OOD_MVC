import java.util.ArrayList;

public class GraphModel {
    private ArrayList<GraphObserver> observers;
    private ArrayList<Double> data;

    public GraphModel(ArrayList<Double> data) {
        this.data = data;
        observers = new ArrayList<>();
    }

    public ArrayList<GraphObserver> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<GraphObserver> observers) {
        this.observers = observers;
//        updateObservers();
    }

    public void addObserver(GraphObserver obs) {
        this.observers.add(obs);
//        updateObservers();
    }

    public ArrayList<Double> getData() {
        return data;
    }

    public void setData(ArrayList<Double> data) {
        this.data = data;
        updateObservers();
    }

    public void updateData(int index, double value) {
        this.data.set(index, value);
        updateObservers();
    }

    public void updateObservers() {
        for (GraphObserver obs: observers) {
            obs.update();
        }
    }
}