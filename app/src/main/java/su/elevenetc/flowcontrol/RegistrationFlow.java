package su.elevenetc.flowcontrol;

/**
 * Created by eugene.levenetc on 02/06/2017.
 */
public class RegistrationFlow extends FlowControl<RegistrationFlow.RegistrationParcel> {

    @Override
    protected void init() {

        final EntryStep entryStep = new EntryStep();
        final SelectLanguage selectLanguage = new SelectLanguage();
        final EnableNetwork enableNetwork = new EnableNetwork();
        final loginOrRegisterOrDemo loginOrRegisterOrDemo = new loginOrRegisterOrDemo();
        final Login login = new Login();
        final Register register = new Register();
        final LoginProgress loginProgress = new LoginProgress();
        final RegisterProgress registerProgress = new RegisterProgress();
        final ErrorProgress errorProgress = new ErrorProgress();
        final Home home = new Home();

        FlowBuilder builder = new FlowBuilder();

        builder.startWith(entryStep)
                .thenOneOf(
                        selectLanguage.once(),
                        home
                )
                .then(loginOrRegisterOrDemo.withBackTo(selectLanguage))
                .then(enableNetwork.ifNeeded())
                .thenOneOf(
                        login.then(
                                loginProgress
                                        .onError(InvalidMailOrPassword.class).backTo(login)
                                        .onError(ServerError.class).backTo(login)
                                        .onSuccess(home)
                        ),
                        register.then(
                                registerProgress
                                        .onError(InvalidMailOrPassword.class).backTo(register)
                                        .onError(ServerError.class).backTo(register)
                                        .onError(RegistrationIsClosedError.class).backTo(loginOrRegisterOrDemo)
                                        .onSuccess(home)
                        ),
                        home.withBackTo(
                                loginOrRegisterOrDemo
                        )
                );
    }

    static class EntryStep extends FlowStep {

    }

    static class SelectLanguage extends FlowStep {

    }

    static class EnableNetwork extends FlowStep {

    }

    static class loginOrRegisterOrDemo extends FlowStep {

    }

    static class Login extends FlowStep {

    }

    static class Register extends FlowStep {

    }

    static class LoginProgress extends FlowStep {
        @Override
        public void doWork() {
            //make request
            boolean isSuccess = true;
            if (isSuccess) {
                //on next
            } else {
                //onError(new RuntimeException(""));
            }
        }
    }

    static class RegisterProgress extends FlowStep {

    }

    static class ErrorProgress extends FlowStep {

    }

    static class Home extends FlowStep {
        public Home ifDemo() {
            return this;
        }
    }

    static class SelectedLanguage {

    }

    static class InvalidMailOrPassword extends Throwable {

    }

    static class ServerError extends Throwable {

    }

    static class RegistrationIsClosedError extends Throwable {

    }

    static class MailAndPass {

        private final String login;
        private final String password;

        public MailAndPass(String login, String password) {

            this.login = login;
            this.password = password;
        }
    }

    static class RegistrationParcel extends FlowParcel {
        String language;
    }
}