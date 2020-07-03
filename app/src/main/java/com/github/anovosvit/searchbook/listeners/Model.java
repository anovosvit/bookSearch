package com.github.anovosvit.searchbook.listeners;


public interface Model {

    interface OnFinishedListener {

        void onFinished();

        void onFailure(Throwable t);
    }
}