package su.elevenetc.flowcontrol;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by eugene.levenetc on 31/05/2017.
 */
public abstract class FlowControl<Parcel extends FlowParcel> {

    private List<FlowStep> steps;
    private TreeMap<FlowStep, StepScreen> stepsMap;
    private int currentIndex;

    protected abstract void init();

    public void start() {
        steps.get(currentIndex).onStart();
    }

    public void onBackPressed(FlowStep flowStep) {
        stepsMap.get(flowStep).onBackPressed();
    }

    public void onSuccess(FlowStep flowStep) {

    }

    public void doWork(FlowStep flowStep) {

    }

    public void onStart(FlowStep flowStep) {

    }

    public void getStep() {
        for (FlowStep step : steps) {

        }
    }

    enum State {
        IN_PROGRESS
    }

    static class Builder {


        void add(FlowStep step) {

        }
    }
}
