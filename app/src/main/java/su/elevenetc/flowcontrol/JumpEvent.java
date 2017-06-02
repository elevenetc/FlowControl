package su.elevenetc.flowcontrol;

/**
 * Created by eugene.levenetc on 02/06/2017.
 */
public abstract class JumpEvent<JumpTarget> {

    private JumpTarget target;

    public JumpEvent(JumpTarget target) {

        this.target = target;
    }
}
