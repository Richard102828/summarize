package com.richard.summariesofdyhdm.tenth_service;

/**
 * @author: Richard
 * @date: 2019/7/17
 * @describe: 回调接口，用于对下载过程中的各种状态进行监听和回调
 */
public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void Cancled();

}
