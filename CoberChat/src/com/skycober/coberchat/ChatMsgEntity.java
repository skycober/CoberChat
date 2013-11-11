package com.skycober.coberchat;

import java.util.UUID;

public class ChatMsgEntity {
//	private static final String TAG = ChatMsgEntity.class.getSimpleName();
	
	public static final String JsonKeyUserId = "user_id";
	/**
	 * �û�ID
	 */
	private String userId;
	
	public static final String JsonKeyUserName = "user_name";
	/**
	 * �û���
	 */
	private String userName;
	
	public static final String JsonKeyMsgId = "msg_id";
	/**
	 * ��ϢID
	 */
	private String msgId;
	
	public static final String JsonKeyAvatarUrl = "avatar_url";
	/**
	 * ͷ��Url
	 */
	private String avatarUrl;
	
	public static final String JsonKeySendDate = "send_date";
	/**
	 * ��������
	 */
	private long sendDate;
	
	public static final String JsonKeyText = "text";
	/**
	 * �ı�����
	 */
	private String text;
	
	public static final String JsonKeyMsgType = "msg_type";
	/**
	 * ��Ϣ���ͣ����շ���Come��/���ͷ���Go��
	 */
	private MsgType msgType = MsgType.Come;
	
	public static final String JsonKeyMsgSendState = "msg_send_state";
	/**
	 * ��Ϣ�ķ���״̬
	 * Init		��ʼ��
	 * Ready	����
	 * Sending	������
	 * Suc		�ɹ�
	 * Fail		ʧ��
	 */
	private MsgSendState msgSendState = MsgSendState.Init;
	
	public static final String JsonKeyMsgContentType = "msg_content_type";
	/**
	 * ��Ϣ�������ͣ�
	 * Text		����
	 * Pic		ͼƬ
	 * Audio	��Ƶ
	 * Video	��Ƶ
	 */
	private MsgContentType msgContentType = MsgContentType.Text;
	
	public static final String JsonKeyPicUrl = "pic_url";
	/**
	 * ͼƬUrl
	 */
	private String picUrl;
	
	public static final String JsonKeyAudioUrl = "audio_url";
	/**
	 * ��ƵUrl
	 */
	private String audioUrl;
	
	public static final String JsonKeyAudioDuration = "audio_duration";
	/**
	 * ��Ƶ��ʱ��
	 */
	private long audioDuration;
	
	public static final String JsonKeyVideoUrl = "video_url";
	/**
	 * ��ƵUrl
	 */
	private String videoUrl;
	
	public static final String JsonKeyVideoDuration = "video_duration";
	/**
	 * ��Ƶ��ʱ��
	 */
	private long videoDuration;
	
	public static final String JsonKeyVideoSnapShotUrl = "video_snapshot_url";
	/**
	 * ��Ƶ��ͼ
	 */
	private String videoSnapshotUrl;
	
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public long getAudioDuration() {
		return audioDuration;
	}

	public void setAudioDuration(long audioDuration) {
		this.audioDuration = audioDuration;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public long getVideoDuration() {
		return videoDuration;
	}

	public void setVideoDuration(long videoDuration) {
		this.videoDuration = videoDuration;
	}

	public String getVideoSnapshotUrl() {
		return videoSnapshotUrl;
	}

	public void setVideoSnapshotUrl(String videoSnapshotUrl) {
		this.videoSnapshotUrl = videoSnapshotUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getDate() {
		return sendDate;
	}

	public void setSendDate(long sendDate) {
		this.sendDate = sendDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public MsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	public MsgSendState getMsgSendState() {
		return msgSendState;
	}

	public void setMsgSendState(MsgSendState msgSendState) {
		this.msgSendState = msgSendState;
	}

	public long getSendDate() {
		return sendDate;
	}
	
	public String getMsgId() {
		return msgId;
	}
	
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public MsgContentType getMsgContentType() {
		return msgContentType;
	}

	public void setMsgContentType(MsgContentType msgContentType) {
		this.msgContentType = msgContentType;
	}

	public ChatMsgEntity() {
		this.msgId = UUID.randomUUID().toString();
	}

	@Override
	public String toString() {
		return "ChatMsgEntity [userId=" + userId + ", userName=" + userName
				+ ", msgId=" + msgId + ", avatarUrl=" + avatarUrl
				+ ", sendDate=" + sendDate + ", text=" + text + ", msgType="
				+ msgType + ", msgSendState=" + msgSendState
				+ ", msgContentType=" + msgContentType + ", picUrl=" + picUrl
				+ ", audioUrl=" + audioUrl + ", audioDuration=" + audioDuration
				+ ", videoUrl=" + videoUrl + ", videoDuration=" + videoDuration
				+ ", videoSnapshotUrl=" + videoSnapshotUrl + "]";
	}

	public enum MsgType {
		Come(10), Go(11), Other(-1);
		private int value = 0;

	    private MsgType(int value) {    //    ������private�ģ�����������
	        this.value = value;
	    }

	    public static MsgType valueOf(int value) {    //    ��д�Ĵ�int��enum��ת������
	        switch (value) {
	        case 10:
	            return Come;
	        case 11:
	        	return Go;
	        case -1:
	        default:
	            return Other;
	        }
	    }

	    public int value() {
	        return this.value;
	    }
	}
	
	public enum MsgContentType{
		Text(20), Pic(21), Audio(22), Video(23), Other(-1);
		private int value = 0;

	    private MsgContentType(int value) {    //    ������private�ģ�����������
	        this.value = value;
	    }

	    public static MsgContentType valueOf(int value) {    //    ��д�Ĵ�int��enum��ת������
	        switch (value) {
	        case 20:
	            return Text;
	        case 21:
	        	return Pic;
	        case 22:
	        	return Audio;
	        case 23:
	        	return Video;
	        case -1:
	        default:
	            return Other;
	        }
	    }

	    public int value() {
	        return this.value;
	    }
	}
	
	public enum MsgSendState{
		Init(30), Ready(31), Sending(32), Suc(33), Fail(34);
		private int value = 0;

	    private MsgSendState(int value) {    //    ������private�ģ�����������
	        this.value = value;
	    }

	    public static MsgSendState valueOf(int value) {    //    ��д�Ĵ�int��enum��ת������
	        switch (value) {
	        case 20:
	            return Init;
	        case 21:
	        	return Ready;
	        case 22:
	        	return Sending;
	        case 23:
	        	return Suc;
	        case -1:
	        default:
	            return Fail;
	        }
	    }

	    public int value() {
	        return this.value;
	    }
	}
}
