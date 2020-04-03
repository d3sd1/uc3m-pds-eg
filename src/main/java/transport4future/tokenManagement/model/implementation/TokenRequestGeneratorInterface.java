package transport4future.tokenManagement.model.implementation;

import transport4future.tokenManagement.exception.TokenManagementException;

public interface TokenRequestGeneratorInterface {
    String TokenRequestGeneration (String inputFile) throws TokenManagementException;
}
