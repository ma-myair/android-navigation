package eu.dozd.navigator.core;


import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Base class for navigation requests
 */
public abstract class BaseRequest {

    public final AppCompatActivity activity;
    public final Fragment fragment;

    public final boolean immediate;
    public final boolean allowStateLoss;

    public BaseRequest(BaseBuilder builder) {
        this.activity = builder.activity;
        this.fragment = builder.fragment;
        this.immediate = builder.immediate;
        this.allowStateLoss = builder.allowStateLoss;
    }

}
