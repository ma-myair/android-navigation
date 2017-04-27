package eu.dozd.navigator.core;

import android.support.v4.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Default forward request built by {@link ForwardBuilder} and used by {@link BaseNavigationController}.
 */
public class ForwardRequest extends BaseRequest {

    final List<Pair<View, String>> sharedElements = new ArrayList<>();

    final Object enterTransition;
    final Object exitTransition;
    final Object reenterTransition;
    final Object returnTransition;
    final Object sharedElementEnterTransition;
    final Object sharedElementReturnTransition;
    final boolean allowEnterTransitionOverlap;
    final boolean allowReturnTransitionOverlap;
    final boolean replaceSameFragment;
    final boolean addToBackStack;
    final ForwardMode mode;
    final String root;
    final int exitAnimation;
    final int enterAnimation;

    ForwardRequest(ForwardBuilder builder) {
        super(builder);

        this.sharedElements.addAll(builder.sharedElements);
        this.enterTransition = builder.enterTransition;
        this.exitTransition = builder.exitTransition;
        this.reenterTransition = builder.reenterTransition;
        this.returnTransition = builder.returnTransition;
        this.sharedElementEnterTransition = builder.sharedElementEnterTransition;
        this.sharedElementReturnTransition = builder.sharedElementReturnTransition;
        this.allowEnterTransitionOverlap = builder.allowEnterTransitionOverlap;
        this.allowReturnTransitionOverlap = builder.allowReturnTransitionOverlap;
        this.replaceSameFragment = builder.replaceSameFragment;
        this.addToBackStack = builder.addToBackStack;
        this.mode = builder.mode;
        this.root = builder.root;
        this.enterAnimation = builder.enterAnimation;
        this.exitAnimation = builder.exitAnimation;
    }
}
