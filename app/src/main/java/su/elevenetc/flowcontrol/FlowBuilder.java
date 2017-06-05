package su.elevenetc.flowcontrol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by eugene.levenetc on 02/06/2017.
 */
public class FlowBuilder {

    Map<Class<? extends FlowStep>, FlowStep> stepsByClass = new HashMap<>();
    Map<FlowStep, StepNode> stepNodes = new HashMap<>();
    Set<StepNode> allNodes = new HashSet<>();
    StepNode rootNode;

    void next(FlowStep from, FlowStep to) {
        register(from);
        register(to);
        get(from).next.add(get(to));
    }

    FlowStep startWith(FlowStep rootStep) {
        register(rootStep);
        this.rootNode = get(rootStep);
        return rootStep;
    }

    FlowBuilder thenOneOf(FlowStep from, FlowStep... nextSteps) {

        register(from);

        final StepNode fromNode = get(from);

        for (FlowStep next : nextSteps) {

            register(next);


            fromNode.next.add(get(next));
        }
        return this;
    }

    FlowBuilder onError(Class<Throwable> error) {
        return this;
    }

    FlowBuilder backTo(FlowStep step) {
        return this;
    }

    void register(FlowStep step) {
        if (!stepsByClass.containsKey(step.getClass())) {
            stepsByClass.put(step.getClass(), step);
            allNodes.add(get(step));
        }
    }

    void goTo(FlowStep from, Class<? extends FlowStep> to) {
        StepNode source = stepNodes.get(from);
        StepNode target = get(stepsByClass.get(to));

        //get params from source
        //finish source
        //set params to target
        //start target
        target.step.onStart();
    }

    StepNode get(FlowStep step) {
        if (stepNodes.containsKey(step)) {
            return stepNodes.get(step);
        } else {
            StepNode node = new StepNode(step);
            stepNodes.put(step, node);
            return node;
        }
    }

    public void goToNext(FlowStep from) {
        final StepNode step = get(from);

        //TODO check why D has only one next step, but not two: Z and X
        if (step.next.size() > 1) {
            throw new UnsupportedOperationException("No defined behaviour of multi next step");
        }
    }

    static class StepNode {
        Set<StepNode> next = new HashSet<>();
        FlowStep step;

        public StepNode(FlowStep from) {
            this.step = from;
        }
    }
}
