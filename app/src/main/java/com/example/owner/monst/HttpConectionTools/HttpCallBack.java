package com.example.owner.monst.HttpConectionTools;

/**
 * Created by Owner on 2015/07/23.
 */
public interface HttpCallBack {
    void onEndHttpCommunication(String result);
    void onErrorHttpCommunication();
}
