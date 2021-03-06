package eu.nerevar.sample.circle;


import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.transition.ChangeBounds;
import android.util.Log;

import java.util.Random;

import eu.nerevar.sample.base.BaseViewModel;
import eu.nerevar.sample.utils.TransitionUtils;
import eu.dozd.navigator.core.ForwardMode;
import eu.dozd.navigator.Navigator;

public class CircleViewModel extends BaseViewModel<CircleView> {

    private static final String ARG_MODEl = "arg_model";

    private CircleModel model;

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);

        if (savedInstanceState != null) {
            model = (CircleModel) savedInstanceState.getSerializable(ARG_MODEl);
        } else if (arguments != null) {
            model = (CircleModel) arguments.getSerializable(ARG_MODEl);
        } else {
            Log.w(CircleViewModel.class.getSimpleName(), "No arguments nor saved instance state found");
        }

        if (model == null) {
            model = new CircleModel();
        }
    }

    @Override
    public void onBindView(@NonNull CircleView view) {
        super.onBindView(view);

        // check if is initialized
        if (model.getColor().get() == 0 && model.getX().get() == 0 && model.getY().get() == 0) {
            final int width = view.getParentWidth();
            final int height = view.getParentHeight();

            final Random r = new Random();

            // set position for view
            model.setX(r.nextInt(width));
            model.setY(r.nextInt(height));

            // set color for view
            model.setColor(randomColor());
        }

        view.moveImage(model.getX().get(), model.getY().get());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(ARG_MODEl, model);
    }

    public void navigateToNextFragment() {
        if (getActivity() == null || getFragment() == null) {
            return;
        }

        final ChangeBounds changeBoundsTransition = TransitionUtils.changeBoundsAnimation(CircleModel.TRANSITION_DURATION);

        Navigator.with(getActivity())
                .forward()
                .setFragment(CircleFragment.newInstance())
                .setSharedElementEnterTransition(changeBoundsTransition)
                .setSharedElementReturnTransition(changeBoundsTransition)
                .addSharedElements(requestSharedElements())
                .setAllowReturnTransitionOverlap(true)
                .setAllowEnterTransitionOverlap(true)
                .noBackStack()
                .navigate(ForwardMode.REPLACEMENT);
    }

    CircleModel getModel() {
        return model;
    }

    private int randomColor() {
        final Random rnd = new Random();

        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
