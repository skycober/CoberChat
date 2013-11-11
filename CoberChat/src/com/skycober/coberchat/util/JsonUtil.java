package com.skycober.coberchat.util;

import java.io.IOException;
import java.io.StringReader;

import org.json.JSONObject;

import com.google.gson.stream.JsonReader;
import com.skycober.coberchat.ChatMsgEntity;
import com.skycober.coberchat.ChatMsgEntity.MsgContentType;
import com.skycober.coberchat.ChatMsgEntity.MsgType;

import android.content.Context;
import android.util.Log;

public class JsonUtil {
	private static final String TAG = JsonUtil.class.getSimpleName();
	private static JsonUtil _instance;
	
	public static JsonUtil getInstance(Context context){
		if(null == _instance){
			_instance = new JsonUtil();
		}
		return _instance;
	}
	
	public String GetJsonForChatMsgEntity(Context context, ChatMsgEntity entity){
		String jsonResult = "";
		JSONObject object = new JSONObject();
		try {
			String userId = entity.getUserId();
			if(!StringUtil.getInstance(context).IsEmpty(userId)){
				object.put(ChatMsgEntity.JsonKeyUserId, userId);
			}
			
			String userName = entity.getUserName();
			if(!StringUtil.getInstance(context).IsEmpty(userName)){
				object.put(ChatMsgEntity.JsonKeyUserName, userName);
			}
			
			String msgId = entity.getMsgId();
			if(!StringUtil.getInstance(context).IsEmpty(msgId)){
				object.put(ChatMsgEntity.JsonKeyMsgId, msgId);
			}
			
			MsgType msgType = entity.getMsgType();
			object.put(ChatMsgEntity.JsonKeyMsgType, msgType.value());
			
			String avatarUrl = entity.getAvatarUrl();
			if(!StringUtil.getInstance(context).IsEmpty(avatarUrl)){
				object.put(ChatMsgEntity.JsonKeyAvatarUrl, avatarUrl);
			}
			
			long sendDate = entity.getSendDate();
			object.put(ChatMsgEntity.JsonKeySendDate, sendDate);
			
			MsgContentType msgContentType = entity.getMsgContentType();
			switch (msgContentType) {
			case Pic:
				String picUrl = entity.getPicUrl();
				if(!StringUtil.getInstance(context).IsEmpty(picUrl)){
					object.put(ChatMsgEntity.JsonKeyPicUrl, picUrl);
				}
				break;
			case Audio:
				String audioUrl = entity.getAudioUrl();
				if(!StringUtil.getInstance(context).IsEmpty(audioUrl)){
					object.put(ChatMsgEntity.JsonKeyAudioUrl, audioUrl);
				}
				
				long audioDuration = entity.getAudioDuration();
				object.put(ChatMsgEntity.JsonKeyAudioDuration, audioDuration);
				
				break;
			case Video:
				String videoUrl = entity.getVideoUrl();
				if(!StringUtil.getInstance(context).IsEmpty(videoUrl)){
					object.put(ChatMsgEntity.JsonKeyVideoUrl, videoUrl);
				}
				
				long videoDuration = entity.getVideoDuration();
				object.put(ChatMsgEntity.JsonKeyVideoDuration, videoDuration);
				
				String videoSnapShotUrl = entity.getVideoSnapshotUrl();
				if(!StringUtil.getInstance(context).IsEmpty(videoSnapShotUrl)){
					object.put(ChatMsgEntity.JsonKeyVideoSnapShotUrl, videoSnapShotUrl);
				}
				break;
			case Text:
			default:
				String text = entity.getText();
				if(!StringUtil.getInstance(context).IsEmpty(text)){
					object.put(ChatMsgEntity.JsonKeyText, text);
				}
				break;
			}
		} catch (Exception e) {
			Log.e(TAG, "GetJsonForChatMsgEntity has Exception", e);
		}
		jsonResult = object.toString();
		Log.d(TAG, "GetJsonForChatMsgEntity:requestJson->"+jsonResult);
		return jsonResult; 
	}

	public ChatMsgEntity parseJsonForChatMsgEntity(Context context, String clientUserId, String json){
		ChatMsgEntity entity = new ChatMsgEntity();
		entity.setMsgId(null);
		JsonReader reader = null;
		try {
			reader = new JsonReader(new StringReader(json));
			reader.beginObject();
			while (reader.hasNext()) {
				String tagName = reader.nextName();
				if(tagName.equalsIgnoreCase(ChatMsgEntity.JsonKeyMsgId)){
					try {
						entity.setMsgId(reader.nextString());
					} catch (Exception e) {
						reader.skipValue();
						Log.w(TAG, "parse msgId error");
					}
				} else if(tagName.equalsIgnoreCase(ChatMsgEntity.JsonKeyUserId)){
					try {
						entity.setUserId(reader.nextString());
					} catch (Exception e) {
						reader.skipValue();
						Log.w(TAG, "parse userId error");
					}
					String msgUserId = entity.getUserId();
					if(null != clientUserId && null != msgUserId && msgUserId.equalsIgnoreCase(clientUserId)){
						entity.setMsgType(MsgType.Go);
					}else{
						entity.setMsgType(MsgType.Come);
					}
				} else if(tagName.equalsIgnoreCase(ChatMsgEntity.JsonKeyUserName)){
					try {
						entity.setUserName(reader.nextString());
					} catch (Exception e) {
						reader.skipValue();
						Log.w(TAG, "parse userName error");
					}
				} else if(tagName.equalsIgnoreCase(ChatMsgEntity.JsonKeyAvatarUrl)){
					try {
						entity.setAvatarUrl(reader.nextString());
					} catch (Exception e) {
						reader.skipValue();
						Log.w(TAG, "parse avatarUrl error");
					}
				} else if(tagName.equalsIgnoreCase(ChatMsgEntity.JsonKeySendDate)){
					try {
						entity.setSendDate(Long.parseLong(reader.nextString()));
					}catch(NumberFormatException e){
						Log.w(TAG, "parse sendDate trans error");
					}catch (Exception e) {
						reader.skipValue();
						Log.w(TAG, "parse sendDate error");
					}
				} else if(tagName.equalsIgnoreCase(ChatMsgEntity.JsonKeyMsgContentType)){
					try {
						int contentTypeValue = Integer.parseInt(reader.nextString());
						entity.setMsgContentType(MsgContentType.valueOf(contentTypeValue)); 
					}catch(NumberFormatException e){
						Log.w(TAG, "parse sendDate trans error");
					}catch (Exception e) {
						reader.skipValue();
						Log.w(TAG, "parse sendDate error");
					}
				} else if(tagName.equalsIgnoreCase(ChatMsgEntity.JsonKeyText)){
					try {
						entity.setText(reader.nextString());
					} catch (Exception e) {
						reader.skipValue();
						Log.w(TAG, "parse text error");
					}
				} else if(tagName.equalsIgnoreCase(ChatMsgEntity.JsonKeyPicUrl)){
					try {
						entity.setPicUrl(reader.nextString());
					} catch (Exception e) {
						reader.skipValue();
						Log.w(TAG, "parse picUrl error");
					}
				} else if(tagName.equalsIgnoreCase(ChatMsgEntity.JsonKeyAudioUrl)){
					try {
						entity.setAudioUrl(reader.nextString());
					} catch (Exception e) {
						reader.skipValue();
						Log.w(TAG, "parse audioUrl error");
					}
				} else if(tagName.equalsIgnoreCase(ChatMsgEntity.JsonKeyAudioDuration)){
					try {
						entity.setAudioDuration(Long.parseLong(reader.nextString()));
					}catch(NumberFormatException e){
						Log.w(TAG, "parse audioDuration trans error");
					}catch (Exception e) {
						reader.skipValue();
						Log.w(TAG, "parse audioDuration error");
					}
				} else if(tagName.equalsIgnoreCase(ChatMsgEntity.JsonKeyVideoUrl)){
					try {
						entity.setVideoUrl(reader.nextString());
					} catch (Exception e) {
						reader.skipValue();
						Log.w(TAG, "parse videoUrl error");
					}
				} else if(tagName.equalsIgnoreCase(ChatMsgEntity.JsonKeyVideoSnapShotUrl)){
					try {
						entity.setVideoSnapshotUrl(reader.nextString());
					} catch (Exception e) {
						reader.skipValue();
						Log.w(TAG, "parse videoSnapShotUrl error");
					}
				} else if(tagName.equalsIgnoreCase(ChatMsgEntity.JsonKeyVideoDuration)){
					try {
						entity.setVideoDuration(Long.parseLong(reader.nextString()));
					}catch(NumberFormatException e){
						Log.w(TAG, "parse videoDuration trans error");
					}catch (Exception e) {
						reader.skipValue();
						Log.w(TAG, "parse videoDuration error");
					}
				} else {
					reader.skipValue();
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "parseJsonForChatMsgEntity", e);
			return null;
		} finally{
			if(null != reader){
				try {
					reader.close();
				} catch (IOException e) {
					Log.e(TAG, "reader close Exception.", e);
				}
			}
		}
		return entity;
	}
}
