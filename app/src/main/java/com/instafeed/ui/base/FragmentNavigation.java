package com.instafeed.ui.base;

import android.os.Bundle;

public interface FragmentNavigation {

    interface View {
        void attachPresenter(Presenter presenter);

    }

    interface Presenter {
        void addFragment(BaseFragment fragment, Bundle bundle);
    }
}
