package gui.screens.login;

import domain.services.ServicesCredentials;
import gui.screens.common.ScreenConstants;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class LoginViewModel {

    private final ServicesCredentials servicesCredentials;
    private final ObjectProperty<LoginState> state;

    @Inject
    public LoginViewModel(ServicesCredentials servicesCredentials) {
        this.servicesCredentials = servicesCredentials;
        state = new SimpleObjectProperty<>(new LoginState(false, null));
    }

    public ReadOnlyObjectProperty<LoginState> getState() {
        return state;
    }

    public void doLogin(String username, String password) {
        if (servicesCredentials.scLogin(username, password)) {
            state.setValue(new LoginState(true, null));
        } else {
            state.setValue(new LoginState(false, ScreenConstants.INVALID_CREDENTIALS));
        }
    }
}
