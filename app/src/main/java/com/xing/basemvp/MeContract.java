package com.xing.basemvp;


import com.xing.basemvp.mvp.IView;

public interface MeContract {
    interface View extends IView {
        void showCount();
    }

    interface Presenter {
        void getCount();
    }
}
