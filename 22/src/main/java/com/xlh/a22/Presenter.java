package com.xlh.a22;

/**
 * Date: 20-6-17
 * Time: 下午1:29
 * Author: dain
 */
class Presenter
{
    MainActivity activity;

    public Presenter(MainActivity activity) {
        this.activity = activity;
    }

    void load(){
        MainData.getData();
    }
}
