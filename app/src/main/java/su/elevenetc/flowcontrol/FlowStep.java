package su.elevenetc.flowcontrol;

import static su.elevenetc.flowcontrol.utils.Utils.isTrue;

/**
 * Created by eugene.levenetc on 31/05/2017.
 */
public class FlowStep {

    private final FlowControlMap flowControl;
    private final int config;
    private State state = State.NOT_STARTED;

    public FlowStep(FlowControlMap flowControl, int config) {
        this.flowControl = flowControl;
        this.config = config;
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

    public void onError(Throwable t) {
        if (isTrue(config, Config.GO_BACK_ON_ERROR)) {
            onBackPressed();
        }
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
