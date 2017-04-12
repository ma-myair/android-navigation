package eu.dozd.navigator.core;

/**
 * Prescription for navigation controller
 *
 * @param <F> forward request type
 * @param <B> backward request type
 */
public interface NavigationController<F extends BaseRequest, B extends BaseRequest> {

    /**
     * Navigate to fragment according forward request
     */
    void navigate(F request);

    /**
     * Navigate to fragment's specified root
     */
    boolean navigateToFragmentRoot(B request);

    /**
     * Pops whole back stack excluding first fragment
     */
    boolean popWholeBackStack(B request);

    /**
     * Pop back stack by one
     */
    boolean pop(B request);

}
