package com.example.fys.fylightweightappframe.mvp;

import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.fys.fylightweightappframe.R;

import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by fengyu on 16/7/13.
 */
@EFragment(R.layout.examplemvpfragment)
public class MvpExFragment extends Fragment implements ExampleContract.View {

    @ViewById
    ProgressBar mProgressBar;
    @ViewById
    FloatingActionButton fab;


    private ExampleContract.Presenter presenter;


    @AfterViews
    void afterViews() {
        mProgressBar.setMax(100);
    }

    @Click({R.id.fab})
    void onClick(View view) {
        presenter.startRequestImage();
    }


    @Override
    public void updateProgress(int progress, long total) {
        mProgressBar.setProgress((int) (100 * progress));

        Log.e("ss", "inProgress :" + (int) (100 * progress));
    }

    @Override
    public void setPresenter(ExampleContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
