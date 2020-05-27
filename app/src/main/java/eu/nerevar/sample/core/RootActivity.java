package eu.nerevar.sample.core;


import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import androidx.core.util.Pair;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import eu.dozd.navigator.Navigator;
import eu.dozd.navigator.core.NavigatorController;
import eu.inloop.viewmodel.base.ViewModelBaseActivity;
import eu.nerevar.sample.R;
import eu.nerevar.sample.circle.CircleFragment;
import eu.nerevar.sample.databinding.ActivityRootBinding;
import eu.dozd.navigator.core.ForwardMode;
import eu.dozd.navigator.core.NavigationController;

public class RootActivity extends ViewModelBaseActivity<RootView, RootViewModel>
        implements RootView, NavigatorController {

    private final NavigationController navigationController = new SampleNavigationController(R.id.frameLayout, "root_activity_root_tag");

    private ActivityRootBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_root);
        binding.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                binding.drawer.closeDrawers();
                return getViewModel().onNavigationDrawerClicked(item.getItemId());
            }
        });

        if (savedInstanceState == null) {
            binding.navigation.setCheckedItem(R.id.nav_fragment_transitions);
            Navigator.with(this)
                    .forward()
                    .setFragment(CircleFragment.newInstance())
                    .navigate(ForwardMode.REPLACEMENT);
        }

        setModelView(this);
    }

    @Nullable
    @Override
    public Class<RootViewModel> getViewModelClass() {
        return RootViewModel.class;
    }

    @Override
    public NavigationController getNavigationController() {
        return navigationController;
    }

    @Override
    public List<Pair<View, String>> getSharedElements() {
        return null;
    }
}
