package eu.dozd.navigator.utils;


import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.dozd.navigator.core.BaseRequest;
import eu.dozd.navigator.core.NavigationController;
import eu.dozd.navigator.core.NavigatorController;

public class Utils {

    @NonNull
    public static <F extends BaseRequest, B extends BaseRequest> NavigationController<F, B> getNavigationController(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be null");
        }

        if (!(activity instanceof NavigatorController)) {
            throw new RuntimeException("Activity must implement NavigatorController");
        }

        return ((NavigatorController) activity).getNavigationController();
    }

    @Nullable
    public static String listToString(@Nullable final List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        final StringBuilder builder = new StringBuilder();

        for (String s : list) {
            builder.append(s);
            if (list.indexOf(s) != list.size() - 1) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }

}
