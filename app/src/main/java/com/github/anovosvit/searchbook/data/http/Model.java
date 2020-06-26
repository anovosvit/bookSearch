package com.github.anovosvit.searchbook.data.http;


public interface Model {

    interface OnFinishedListener {

        void onFinished();

        void onFailure(Throwable t);
    }
}