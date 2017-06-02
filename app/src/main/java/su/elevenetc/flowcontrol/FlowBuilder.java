package su.elevenetc.flowcontrol;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eugene.levenetc on 02/06/2017.
 */
class FlowBuilder {

    List<FlowStep> flow = new LinkedList<>();

    FlowBuilder startWith(FlowStep step) {
        flow.add(step);
        step.setFlow(flow);
        return this;
    }

    FlowBuilder then(FlowStep step) {
        flow.add(step);
        step.setFlow(flow);
        return this;
    }

    FlowBuilder thenOneOf(FlowStep... step) {

        return this;
    }

    FlowBuilder onError(Class<Throwable> error) {
        return this;
    }

    FlowBuilder backTo(FlowStep step) {
        return this;
    }
}
