package com.instafeed.ui.base;

import android.os.Bundle;

public interface BaseActivityContract {

    interface View {
        void setFragment(BaseFragment fragment, Bundle bundle);
    }

    interface Presenter {
    }
}
