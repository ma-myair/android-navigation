package eu.nerevar.sample.base;

import androidx.core.util.Pair;
import android.view.View;

import java.util.List;

import eu.inloop.viewmodel.IView;


public interface BaseView extends IView {

    List<Pair<View, String>> getSharedElements();

}
