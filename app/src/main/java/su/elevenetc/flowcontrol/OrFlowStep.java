package su.elevenetc.flowcontrol;

/**
 * Created by eugene.levenetc on 02/06/2017.
 */
public class OrFlowStep extends FlowStep {
    private FlowStep[] steps;

    public OrFlowStep(FlowStep... steps) {

        this.steps = steps;
    }
}
