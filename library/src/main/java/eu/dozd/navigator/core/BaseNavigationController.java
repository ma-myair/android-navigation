package eu.dozd.navigator.core;


import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.util.Pair;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import eu.dozd.navigator.utils.Utils;

import static androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE;
import static androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;
import static androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
import static androidx.fragment.app.FragmentTransaction.TRANSIT_NONE;

/**
 * Basic implementation of navigation controller.
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseNavigationController implements NavigationController<ForwardRequest, BackwardRequest> {

    @Override
    public void navigate(ForwardRequest request) {
        // log request
        logForwardRequest(request, request.addToBackStack);
        // set animations
        setAnimations(request);
        // make fragment transaction
        if (request.mode == ForwardMode.NEW) {
            final FragmentManager fragmentManager = request.activity.getSupportFragmentManager();

            // pop all fragments
            if (fragmentManager.getBackStackEntryCount() > 0) {
                if (request.immediate) {
                    while (fragmentManager.getBackStackEntryCount() > 0) {
                        fragmentManager.popBackStackImmediate();
                    }
                } else {
                    final int count = fragmentManager.getBackStackEntryCount();

                    for (int i = 0; i < count; i++) {
                        fragmentManager.popBackStack();
                    }
                }
            }

            // replace fragment if allowed and is the same class
            if (request.replaceSameFragment && fragmentManager.findFragmentByTag(getRootTag()) != null) {
                if (!fragmentManager.findFragmentByTag(getRootTag()).getClass().equals(request.fragment.getClass())) {
                    fragmentTransaction(request);
                }
            } else {
                // replace fragment
                fragmentTransaction(request);
            }
        } else {
            fragmentTransaction(request);
        }
    }

    @Override
    public boolean navigateToFragmentRoot(BackwardRequest request) {
        logBackwardRequest(request);

        if (request.root == null) {
            return false;
        }

        final FragmentManager fragmentManager = request.activity.getSupportFragmentManager();

        if (request.immediate) {
            fragmentManager.popBackStackImmediate(request.root, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            fragmentManager.popBackStack(request.root, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        return false;
    }

    /**
     * Pops whole back stack. Can leave first fragment if requested.
     *
     * @return if immediate is false, then return is false because the operation is asynchronous.
     * Otherwise returns if something was popped.
     */
    @Override
    public boolean popWholeBackStack(BackwardRequest request) {
        logBackwardRequest(request);

        final FragmentManager fragmentManager = request.activity.getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() <= 0) {
            return false;
        }

        if (request.immediate) {
            while (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();
            }

            return true;
        } else {
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                fragmentManager.popBackStack();
            }

            return false;
        }
    }

    @Override
    public boolean pop(BackwardRequest request) {
        logBackwardRequest(request);

        final FragmentManager fragmentManager = request.activity.getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 0) {
            if (request.immediate) {
                fragmentManager.popBackStackImmediate();
            } else {
                fragmentManager.popBackStack();
            }
            return true;
        }

        return false;
    }

    protected void fragmentTransaction(final ForwardRequest request) {
        // create fragment transaction
        final FragmentTransaction ft = request.activity.getSupportFragmentManager().beginTransaction();

        // set transition
        if (getFragmentTransition() != TRANSIT_NONE) {
            ft.setTransition(getFragmentTransition());
        }

        // add/replace fragment
        if (request.mode == ForwardMode.ADDITION) {
            ft.add(getContainerId(), request.fragment, getRootTag());
        } else {
            ft.replace(getContainerId(), request.fragment, getRootTag());
        }

        addSharedElements(request, ft);

        // add to backstack ?
        if (request.addToBackStack) {
            ft.addToBackStack(request.root);
        }

        if (request.enterAnimation > 0 && request.exitAnimation > 0) {
            ft.setCustomAnimations(request.enterAnimation, request.exitAnimation);
        }

        // commit
        if (request.immediate) {
            if (request.allowStateLoss) {
                ft.commitNowAllowingStateLoss();
            } else {
                ft.commitNow();
            }
        } else {
            if (request.allowStateLoss) {
                ft.commitAllowingStateLoss();
            } else {
                ft.commit();
            }
        }
    }

    protected void setAnimations(@NonNull final ForwardRequest request) {
        final Fragment fragment = request.fragment;

        fragment.setEnterTransition(request.enterTransition);
        fragment.setReenterTransition(request.reenterTransition);
        fragment.setExitTransition(request.exitTransition);
        fragment.setReturnTransition(request.returnTransition);
        fragment.setSharedElementEnterTransition(request.sharedElementEnterTransition);
        fragment.setSharedElementReturnTransition(request.sharedElementReturnTransition);
        fragment.setAllowEnterTransitionOverlap(request.allowEnterTransitionOverlap);
        fragment.setAllowReturnTransitionOverlap(request.allowReturnTransitionOverlap);
    }

    @Transit
    protected int getFragmentTransition() {
        return TRANSIT_FRAGMENT_FADE;
    }

    /**
     * Returns container id on which to handle the operation.
     */
    @IdRes
    protected abstract int getContainerId();

    @Nullable
    protected abstract String getRootTag();

    private void logForwardRequest(ForwardRequest request, boolean addToBackStack) {
        final List<String> strings = new ArrayList<>(request.sharedElements.size());
        for (Pair<View, String> pair : request.sharedElements) {
            strings.add(pair.second);
        }

        Log.d("Navigator", String.format("Consuming FORWARD request for [%s]. addToBackStack=%b, immediate=%b, allowStateLoss=%b, sharedElements=[%s]",
                request.fragment.getClass().getName(),
                addToBackStack,
                request.immediate,
                request.allowStateLoss,
                Utils.listToString(strings)));
    }

    private void logBackwardRequest(BackwardRequest request) {
        Log.d("Navigator", String.format("Consuming BACKWARD request. immediate=%b, allowStateLoss=%b",
                request.immediate,
                request.allowStateLoss));
    }

    private void addSharedElements(@NonNull final ForwardRequest request,
                                   @NonNull final FragmentTransaction transaction) {
        for (Pair<View, String> sharedElement : request.sharedElements) {
            transaction.addSharedElement(sharedElement.first, sharedElement.second);
        }
    }

    @IntDef({TRANSIT_NONE, TRANSIT_FRAGMENT_OPEN, TRANSIT_FRAGMENT_CLOSE, TRANSIT_FRAGMENT_FADE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Transit {
    }
}
