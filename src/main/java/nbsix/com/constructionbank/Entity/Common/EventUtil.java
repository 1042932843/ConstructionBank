package nbsix.com.constructionbank.Entity.Common;

/**
 * Name: EventUtil
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-12 15:48
 */

public class EventUtil {
    private String msg;

    public String getType() {
        return type;
    }

    private String type;

    public EventUtil(String type) {
        this.type = type;
    }

    public EventUtil(String type,String msg) {
        this.msg = msg;
        this.type = type;
    }

    public String getMsg(){
        return this.msg;
    }
}
