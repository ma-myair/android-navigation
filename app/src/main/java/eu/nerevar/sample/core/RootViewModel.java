package eu.nerevar.sample.core;


import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import eu.dozd.navigator.Navigator;
import eu.nerevar.sample.R;
import eu.nerevar.sample.base.BaseViewModel;
import eu.nerevar.sample.circle.CircleFragment;
import eu.dozd.navigator.core.ForwardMode;

public class RootViewModel extends BaseViewModel<RootView> {

    boolean onNavigationDrawerClicked(@IdRes int id) {
        if (getActivity() == null) {
            return false;
        }

        final Fragment fragment;

        switch (id) {
            case R.id.nav_fragment_transitions:
                fragment = CircleFragment.newInstance();
                break;
            default:
                return false;
        }

        // navigate to new root
        Navigator.with(getActivity())
                .forward()
                .setFragment(fragment)
                .navigate(ForwardMode.NEW);


        return true;
    }

}
