package eu.dozd.navigator.core;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import eu.dozd.navigator.utils.Utils;

/**
 * Base class for navigation builder
 *
 * @param <T> type of navigation builder
 * @param <R> type of navigation request
 */
public abstract class BaseBuilder<T extends BaseBuilder<T, R, M>, R extends BaseRequest, M> {

    /**
     * Navigation controller attached to activity to handle the navigation
     */
    protected final NavigationController<R, BaseRequest> navigationController;

    /**
     * Activity upon which to do the navigation
     */
    final AppCompatActivity activity;

    /**
     * Fragment which to handle by navigation controller inside the activity
     */
    Fragment fragment;

    boolean immediate;
    boolean allowStateLoss;

    public BaseBuilder(@NonNull final AppCompatActivity activity) {
        this.activity = activity;
        this.navigationController = Utils.getNavigationController(activity);
    }

    public T setFragment(Fragment fragment) {
        this.fragment = fragment;
        return self();
    }

    public T setImmediate(boolean immediate) {
        this.immediate = immediate;
        return self();
    }

    public T setAllowStateLoss(boolean allowStateLoss) {
        this.allowStateLoss = allowStateLoss;
        return self();
    }

    public abstract void navigate(M mode);

    public abstract void navigate(NavigationTask<R> navigationTask);

    protected abstract T self();

    public interface NavigationTask<F extends BaseRequest> {
        void run(NavigationController<F, ?> navigationController);
    }

}
