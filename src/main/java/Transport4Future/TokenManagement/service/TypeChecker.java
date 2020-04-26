package Transport4Future.TokenManagement.service;

/**
 *
 */
public class TypeChecker {
    /**
     *
     * @param input
     * @return
     */
    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }
}
