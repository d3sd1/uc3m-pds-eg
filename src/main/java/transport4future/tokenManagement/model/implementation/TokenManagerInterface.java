package transport4future.tokenManagement.model.implementation;

import transport4future.tokenManagement.exception.LMException;

/**
 * The interface Token manager interface.
 */
public interface TokenManagerInterface {
    /**
     * Verify token boolean.
     *
     * @param token the token
     * @return the boolean
     * @throws LMException the lm exception
     */
    boolean VerifyToken(String token) throws LMException;
}
