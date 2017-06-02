package su.elevenetc.flowcontrol;

import java.util.List;

import static su.elevenetc.flowcontrol.utils.Utils.isTrue;

/**
 * Created by eugene.levenetc on 31/05/2017.
 */
public class FlowStep {

    private final FlowControl flowControl;
    private final int config;
    private State state = State.NOT_STARTED;
    private FlowStep previous;
    private FlowStep next;
    List<FlowStep> flow;

    public FlowStep() {
        flowControl = null;
        config = 0;
    }

    public FlowStep(FlowControl flowControl, int config) {
        this.flowControl = flowControl;
        this.config = config;
    }

    public void setFlow(List<FlowStep> flow) {
        this.flow = flow;
    }

    public <PreviousInput> void setPrevious(FlowStep previous) {
        this.previous = previous;
    }

    public <NextOutput> FlowStep or(FlowStep next) {
        return previous;
    }


    public FlowStep once() {
        return this;
    }

    public FlowStep ifNeeded() {
        return this;
    }

    public FlowStep withBack() {
        //allow back to previous
        return this;
    }

    public FlowStep onSuccess(FlowStep flowStep) {
        return this;
    }

    public FlowStep withBackTo(FlowStep flowStep) {
        return this;
    }

    public BackError onError(Class<? extends Throwable> t) {
        return new BackError<>(t, this);
    }

    static class BackError<currentI, currentO> {

        private Class<? extends Throwable> t;
        private FlowStep current;

        public BackError(Class<? extends Throwable> t, FlowStep current) {
            this.t = t;

            this.current = current;
        }

        public FlowStep backTo(FlowStep onErrorBackStep) {
            //associate exception type with onErrorBackStep;
            return current;
        }
    }

    public FlowStep withErrorBackTo(FlowStep step) {
        //add error even which allows to just back to particular step
        return this;
    }


    public void onStart() {
        flowControl.onStart(this);

        if (state == State.FINISHED) {
            onSuccess();
        } else {
            doWork();
        }
    }

    public void doWork() {
        flowControl.doWork(this);
    }

    public void onPause() {

    }

    public void onSuccess() {
        flowControl.onSuccess(this);
    }

    public void onBackPressed() {
        if (isTrue(config, Config.NO_BACK)) {
            return;
        }
        flowControl.onBackPressed(this);
    }

    public void onRetry() {

    }

    public State getState() {
        return state;
    }

    void setState(State state) {
        this.state = state;
    }

    enum State {
        NOT_STARTED, IN_PROGRESS, ERROR, FINISHED
    }

    public static class Config {
        public static final int RETRY_TILL_SUCCESS = 0b000000001;
        public static final int NO_RETRY = 0b000000011;
        public static final int NO_BACK = 0b000000111;
        public static final int CANCEL_FLOW_ON_ERROR = 0b000001111;
        public static final int GO_BACK_ON_ERROR = 0b000011111;
    }
}
