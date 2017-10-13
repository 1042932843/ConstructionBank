package nbsix.clpays.VersionUpdate.entity;

import java.io.Serializable;

/**
 * Name: FileBean
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //文件信息
 * Date: 2017-09-25 15:48
 */

public class FileBean implements Serializable{

    private int id=0;
    private String fileName="";
    private String url="";
    private int length=0;
    private int finished=0;

    public FileBean(){

    }

    public FileBean(int id, String fileName, String url, int length, int finished) {
        this.id = id;
        this.fileName = fileName;
        this.url = url;
        this.length = length;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }
}
