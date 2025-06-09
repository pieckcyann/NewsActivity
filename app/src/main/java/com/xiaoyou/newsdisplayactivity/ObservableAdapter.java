package com.xiaoyou.newsdisplayactivity;


import android.widget.BaseAdapter;

public abstract class ObservableAdapter extends BaseAdapter {

    private Runnable onDataChangedCallback;

    public void setOnDataChangedCallback(Runnable callback) {
        this.onDataChangedCallback = callback;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (onDataChangedCallback != null) {
            onDataChangedCallback.run();
        }
    }
}
