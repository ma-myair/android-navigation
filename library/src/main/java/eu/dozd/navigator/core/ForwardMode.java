package eu.dozd.navigator.core;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Default backward navigation mode for {@link BaseNavigationController} built by a {@link ForwardBuilder}.
 */
public enum ForwardMode {

    /**
     * Add fragment to container over the current fragment using {@link FragmentTransaction#add(int, Fragment, String)}
     */
    ADDITION,

    /**
     * Replace fragment in container using {@link FragmentTransaction#replace(int, Fragment, String)} with adding to backstack
     */
    REPLACEMENT,

    /**
     * Pop all fragments and add new fragment using {@link ForwardMode#REPLACEMENT} method
     */
    NEW

}
