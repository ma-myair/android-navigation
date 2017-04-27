package eu.dozd.navigator.core;


import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.TransitionRes;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Default forward builder for creating a {@link ForwardRequest} used by {@link BaseNavigationController}.
 */
public class ForwardBuilder extends BaseBuilder<ForwardBuilder, ForwardRequest, ForwardMode> {

    final List<Pair<View, String>> sharedElements = new ArrayList<>();

    Object enterTransition;
    Object exitTransition;
    Object reenterTransition;
    Object returnTransition;
    Object sharedElementEnterTransition;
    Object sharedElementReturnTransition;

    boolean allowEnterTransitionOverlap;
    boolean allowReturnTransitionOverlap;
    boolean replaceSameFragment;
    boolean addToBackStack = true;
    ForwardMode mode;
    String root;

    ForwardBuilder(@NonNull final AppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void navigate(ForwardMode mode) {
        if (fragment == null) {
            throw new IllegalStateException("Fragment cannot be null");
        }

        this.mode = mode;

        navigationController.navigate(new ForwardRequest(this));
    }

    @Override
    public void navigate(final NavigationTask<ForwardRequest> navigationTask) {
        if (fragment == null) {
            throw new IllegalStateException("Fragment cannot be null");
        }

        new Runnable() {
            @Override
            public void run() {
                navigationTask.run(navigationController);
            }
        };
    }

    @Override
    protected ForwardBuilder self() {
        return this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForwardBuilder addSharedElement(View view, String string) {
        this.sharedElements.add(new Pair<>(view, string));
        return self();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForwardBuilder addSharedElements(List<Pair<View, String>> elements) {
        this.sharedElements.addAll(elements);
        return self();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForwardBuilder setEnterTransition(Object enterTransition) {
        this.enterTransition = enterTransition;
        return self();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForwardBuilder setEnterTransition(@TransitionRes int res) {
        if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
            this.enterTransition = TransitionInflater.from(activity).inflateTransition(res);
        }
        return self();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForwardBuilder setExitTransition(Object exitTransition) {
        this.exitTransition = exitTransition;
        return self();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForwardBuilder setExitTransition(@TransitionRes int res) {
        if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
            this.exitTransition = TransitionInflater.from(activity).inflateTransition(res);
        }
        return self();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForwardBuilder setReenterTransition(Object reenterTransition) {
        this.reenterTransition = reenterTransition;
        return self();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForwardBuilder setReenterTransition(@TransitionRes int res) {
        if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
            this.reenterTransition = TransitionInflater.from(activity).inflateTransition(res);
        }
        return self();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForwardBuilder setReturnTransition(Object returnTransition) {
        this.returnTransition = returnTransition;
        return self();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForwardBuilder setReturnTransition(@TransitionRes int res) {
        if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
            this.returnTransition = TransitionInflater.from(activity).inflateTransition(res);
        }
        return self();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForwardBuilder setSharedElementEnterTransition(Object sharedElementEnterTransition) {
        this.sharedElementEnterTransition = sharedElementEnterTransition;
        return self();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForwardBuilder setSharedElementEnterTransition(@TransitionRes int res) {
        if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
            this.sharedElementEnterTransition = TransitionInflater.from(activity).inflateTransition(res);
        }
        return self();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForwardBuilder setSharedElementReturnTransition(Object sharedElementReturnTransition) {
        this.sharedElementReturnTransition = sharedElementReturnTransition;
        return self();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForwardBuilder setSharedElementReturnTransition(@TransitionRes int res) {
        if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
            this.sharedElementReturnTransition = TransitionInflater.from(activity).inflateTransition(res);
        }
        return self();
    }

    public ForwardBuilder setAllowEnterTransitionOverlap(boolean allowEnterTransitionOverlap) {
        this.allowEnterTransitionOverlap = allowEnterTransitionOverlap;
        return self();
    }

    public ForwardBuilder setAllowReturnTransitionOverlap(boolean allowReturnTransitionOverlap) {
        this.allowReturnTransitionOverlap = allowReturnTransitionOverlap;
        return self();
    }

    public ForwardBuilder setReplaceSameFragment(boolean replaceSameFragment) {
        this.replaceSameFragment = replaceSameFragment;
        return self();
    }

    public ForwardBuilder noBackStack() {
        this.addToBackStack = false;
        return self();
    }

    public ForwardBuilder setRoot(Class<?> clazz) {
        this.root = clazz.getClass().getName();
        return self();
    }

    public ForwardBuilder setRoot(Fragment fragment) {
        return setRoot(fragment.getClass());
    }
}
