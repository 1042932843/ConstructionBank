package com.clpays.tianfugou.Entity.Message;


/**
 * Name: MessageVo
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //item
 * Date: 2017-09-11 14:33
 */
public class MessageVo {
	public static final int MESSAGE_FROM = 0;
	public static final int MESSAGE_TO = 1;
	
	private int direction;    //该变量为消息是收到(MESSAGE_FROM)的还是发送(MESSAGE_TO)的
	private String content;   //内容
	private String time; 	  //时间
	public MessageVo(int direction, String content, String time) {
		super();
		this.direction = direction;
		this.content = content;
		this.time = time;
	}
	public int getDirection() {
		return direction;
	}
	public String getContent() {
		return content;
	}
	public String getTime() {
		return time;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
