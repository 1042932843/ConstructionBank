package nbsix.com.VersionUpdate.download;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import nbsix.com.VersionUpdate.entity.Config;
import nbsix.com.VersionUpdate.entity.FileBean;



/**
 * Name: InitThread
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //初始化
 * Date: 2017-09-25 15:20
 */

public class InitThread extends Thread{

    private Context context;
    private FileBean fileBean;

    public InitThread(Context context,FileBean fileBean) {
        this.context = context;
        this.fileBean = fileBean;
    }

    @Override
    public void run() {
        HttpURLConnection connection =null;
        RandomAccessFile randomAccessFile = null;
        try {
            URL url = new URL(fileBean.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            int fileLength = -1;
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                fileLength = connection.getContentLength();
            }
            if(fileLength<=0) return;
            File dir = new File(Config.downLoadPath);
            if(!dir.exists()){
                dir.mkdir();
            }
            File file = new File(dir,fileBean.getFileName());
            randomAccessFile = new RandomAccessFile(file,"rwd");
            randomAccessFile.setLength(fileLength);
            fileBean.setLength(fileLength);

      /*    EventMessage eventMessage = new EventMessage(1,fileBean);
            EventBus.getDefault().post(eventMessage);*/
            Intent intent = new Intent(Config.ACTION_START);
            intent.putExtra("FileBean",fileBean);
            context.sendBroadcast(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
