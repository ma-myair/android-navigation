package eu.dozd.navigator.core;

/**
 * An interface which an activity has to implement to be able to use Navigator library.
 * <p>
 * Represents a declaration on how to access navigation controller attached to activity.
 */
public interface NavigatorController {

    /**
     * Returns navigation controller attached to activity.
     */
    <F extends BaseRequest, B extends BaseRequest> NavigationController<F, B> getNavigationController();

}
