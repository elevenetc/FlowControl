package su.elevenetc.flowcontrol;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        FlowBuilder builder = new FlowBuilder();

        final StepA stepA = new StepA(builder);
        final StepB stepB = new StepB(builder);
        final StepC stepC = new StepC(builder);
        final StepD stepD = new StepD(builder);
        final StepZ stepZ = new StepZ(builder);
        final StepX stepX = new StepX(builder);

        builder.startWith(stepA)
                .thenOneOf(
                        stepB.then(stepD).then(stepZ),
                        stepC.then(stepD).then(stepX)
                );

        stepA.onStart();
    }

    static class StepA extends FlowStep {

        int value = 10;

        public StepA(FlowBuilder flowBuilder) {
            super(flowBuilder);
        }

        @Override
        public void doWork() {
            super.doWork();
            if (value == 10) {
                goTo(StepB.class);
            } else {
                goTo(StepC.class);
            }
        }
    }

    static class StepB extends FlowStep {
        public StepB(FlowBuilder flowBuilder) {
            super(flowBuilder);
        }

        @Override
        public void doWork() {
            super.doWork();
            goTo(StepD.class);
        }
    }

    static class StepC extends FlowStep {
        public StepC(FlowBuilder flowBuilder) {
            super(flowBuilder);
        }
    }

    static class StepD extends FlowStep {
        public StepD(FlowBuilder flowBuilder) {
            super(flowBuilder);
        }

        @Override
        public void doWork() {
            super.doWork();
            nextStep();
        }
    }

    static class StepZ extends FlowStep {
        public StepZ(FlowBuilder flowBuilder) {
            super(flowBuilder);
        }
    }

    static class StepX extends FlowStep {
        public StepX(FlowBuilder flowBuilder) {
            super(flowBuilder);
        }
    }
}