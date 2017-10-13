package nbsix.clpays.VersionUpdate.download;

import nbsix.clpays.VersionUpdate.entity.ThreadBean;

/**
 * Name: DownloadCallBack
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-06-27 14:26
 */

public interface DownloadCallBack {
    /**
     * 暂停回调
     * @param threadBean
     */
    void pauseCallBack(ThreadBean threadBean);
    /**
     * 下载进度
     * @param length
     */
    void progressCallBack(int length);

    /**
     * 线程下载完毕
     * @param threadBean
     */
    void threadDownLoadFinished(ThreadBean threadBean);
}
