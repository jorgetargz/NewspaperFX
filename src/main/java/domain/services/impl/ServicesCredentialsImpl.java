package domain.services.impl;

import dao.impl.CredentialsDaoImpl;
import domain.services.ServicesCredentials;
import jakarta.inject.Inject;

public class ServicesCredentialsImpl implements ServicesCredentials {

    private final CredentialsDaoImpl credentialsDao;

    @Inject
    public ServicesCredentialsImpl(CredentialsDaoImpl credentialsDao) {
        this.credentialsDao = credentialsDao;
    }

    @Override
    public boolean scLogin(String username, String password) {
        return credentialsDao.checkCredentials(username, password);
    }

}
