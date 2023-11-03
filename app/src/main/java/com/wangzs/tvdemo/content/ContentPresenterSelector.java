package com.wangzs.tvdemo.content;

import androidx.leanback.widget.ListRow;

import com.wangzs.tvdemo.base.BasePresenterSelector;
import com.wangzs.tvdemo.bean.TypeSeven;
import com.wangzs.tvdemo.presenter.TypeSevenPresenter;
import com.wangzs.tvdemo.presenter.TypeSixContentPresenter;
import com.wangzs.tvdemo.presenter.row.TypeZeroListRowPresenter;


public class ContentPresenterSelector extends BasePresenterSelector {
    public ContentPresenterSelector() {
        TypeZeroListRowPresenter listRowPresenterOne = new TypeZeroListRowPresenter();
        addClassPresenter(TypeSeven.class, new TypeSevenPresenter());
        addClassPresenter(ListRow.class, listRowPresenterOne, TypeSixContentPresenter.class);
    }

}
