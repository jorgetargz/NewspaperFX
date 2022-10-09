package dao.impl;

import configuration.Credentials;
import dao.CredentialsDao;
import jakarta.inject.Inject;

public class CredentialsDaoImpl implements CredentialsDao {

    private final Credentials credentials;

    @Inject
    public CredentialsDaoImpl(Credentials credentials) {
        this.credentials = credentials;
    }

    public boolean checkCredentials(String username, String password) {
        return username.equals(credentials.getUsername()) &&
                password.equals(credentials.getPassword());
    }
}
