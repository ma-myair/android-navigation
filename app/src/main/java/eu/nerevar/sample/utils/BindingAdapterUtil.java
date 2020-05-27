package eu.nerevar.sample.utils;


import androidx.databinding.BindingAdapter;
import androidx.core.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

public final class BindingAdapterUtil {

    @BindingAdapter("colorTint")
    public static void setColorTint(ImageView view, int color) {
        DrawableCompat.setTint(view.getDrawable(), color);
    }

}
