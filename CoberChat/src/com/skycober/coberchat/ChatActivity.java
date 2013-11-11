package com.skycober.coberchat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.skycober.coberchat.ChatMsgEntity.MsgContentType;
import com.skycober.coberchat.ChatMsgEntity.MsgSendState;
import com.skycober.coberchat.ChatMsgEntity.MsgType;
import com.skycober.coberchat.constant.ConfigConstant;
import com.skycober.coberchat.util.JsonUtil;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class ChatActivity extends FinalActivity {

	@ViewInject(id = R.id.ll_fasong) ViewGroup FaSongGroup;
	@ViewInject(id = R.id.ll_yuyin) ViewGroup YuYinGroup;
	@ViewInject(id = R.id.listview) ListView lvChatMsg;
	@ViewInject(id = R.id.btn_send, click = "onButtonSendClick") Button btnSend;
	@ViewInject(id = R.id.btn_back) Button btnBack;
	@ViewInject(id = R.id.right_btn) ImageButton btnRight;
	@ViewInject(id = R.id.chatting_voice_btn) ImageButton voiceBtn;
	@ViewInject(id = R.id.chatting_keyboard_btn) ImageButton keyboardBtn;
	@ViewInject(id = R.id.chatting_biaoqing_btn,click="onBtnPicSendClick") ImageButton biaoqingBtn;
	@ViewInject(id = R.id.chatting_biaoqing_focuse_btn) ImageButton biaoqingfocuseBtn;
	@ViewInject(id = R.id.et_sendmessage) EditText mEditTextContent;

	private static String default_user_id = null; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		default_user_id = String.valueOf(Calendar.getInstance().getTimeInMillis());
		init();
		initData();
	}

	private Pubnub pubnub;
	private ChatReceiver chatReceiver;

	private void initData() {
		if (null == pubnub) {
			pubnub = new Pubnub(ConfigConstant.PublishKey,
					ConfigConstant.SubscribeKey, "", false);
		}
		if (null == chatReceiver) {
			chatReceiver = new ChatReceiver();
		}

		subscribe();
	}

	@Override
	protected void onStart() {
		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(chatReceiver, filter);
		super.onStart();
	}

	@Override
	protected void onStop() {
		unregisterReceiver(chatReceiver);
		super.onStop();
	}

	private ChatMsgAdapter chatMsgAdapter;

	private void init() {
		List<ChatMsgEntity> chatMsgList = new ArrayList<ChatMsgEntity>();
		chatMsgAdapter = new ChatMsgAdapter(this, chatMsgList);
		lvChatMsg.setAdapter(chatMsgAdapter);
	}

	public void onButtonSendClick(View v) {
		if (!v.isEnabled())
			return;
		v.setEnabled(false);
		try {
			String content = mEditTextContent.getText().toString();
			if (content.length() > 0) {
				ChatMsgEntity entity = new ChatMsgEntity();
				long currDate = Calendar.getInstance().getTimeInMillis() / 1000l;
				entity.setSendDate(currDate);
				entity.setUserId(default_user_id);
				entity.setUserName("cober");
				entity.setMsgType(MsgType.Go);
				entity.setMsgContentType(MsgContentType.Text);
				entity.setText(content);
				entity.setMsgSendState(MsgSendState.Sending);

				chatMsgAdapter.appendChatItem(entity);
				// ¸üÐÂlistview
				mEditTextContent.setText("");
				lvChatMsg.setSelection(lvChatMsg.getCount() - 1);
				lvChatMsg.setSelected(true);
				
				publish(entity, DefaultChannel);
			} else {
				
			}
		} catch (Exception e) {
			Log.e("ChatActivity", "publish msg error.", e);
		}
		v.setEnabled(true);
	}

	public class ChatReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			pubnub.disconnectAndResubscribe();
		}
	}
//
//	private void notifyUser(Object message) {
//		try {
//			if (message instanceof JSONObject) {
//				final JSONObject obj = (JSONObject) message;
//				this.runOnUiThread(new Runnable() {
//					public void run() {
//						Toast.makeText(getApplicationContext(), obj.toString(),
//								Toast.LENGTH_LONG).show();
//
//						Log.i("Received msg : ", String.valueOf(obj));
//					}
//				});
//
//			} else if (message instanceof String) {
//				final String obj = (String) message;
//				this.runOnUiThread(new Runnable() {
//					public void run() {
//						Toast.makeText(getApplicationContext(), obj,
//								Toast.LENGTH_LONG).show();
//						Log.i("Received msg : ", obj.toString());
//					}
//				});
//
//			} else if (message instanceof JSONArray) {
//				final JSONArray obj = (JSONArray) message;
//				this.runOnUiThread(new Runnable() {
//					public void run() {
//						Toast.makeText(getApplicationContext(), obj.toString(),
//								Toast.LENGTH_LONG).show();
//						Log.i("Received msg : ", obj.toString());
//					}
//				});
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	private static final String DefaultChannel = "TestChat";
	private void subscribe() {
		Hashtable<String, String> args = new Hashtable<String, String>(1);
		String message = "TestChat";
		args.put("channel", message);
		try {
			pubnub.subscribe(args, new Callback() {
				@Override
				public void connectCallback(String channel, Object message) {
//					notifyUser("SUBSCRIBE : CONNECT on channel:" + channel
//							+ " : " + message.getClass() + " : "
//							+ message.toString());
					
				}

				@Override
				public void disconnectCallback(String channel, Object message) {
//					notifyUser("SUBSCRIBE : DISCONNECT on channel:" + channel
//							+ " : " + message.getClass() + " : "
//							+ message.toString());
				}

				@Override
				public void reconnectCallback(String channel, Object message) {
//					notifyUser("SUBSCRIBE : RECONNECT on channel:" + channel
//							+ " : " + message.getClass() + " : "
//							+ message.toString());
				}

				@Override
				public void successCallback(String channel, Object message) {
//					notifyUser("SUBSCRIBE : " + channel + " : "
//							+ message.getClass() + " : " + message.toString());
					String msg = message.toString();
					final ChatMsgEntity entity = JsonUtil.getInstance(ChatActivity.this).parseJsonForChatMsgEntity(ChatActivity.this, default_user_id, msg);
					if(entity.getMsgType() == MsgType.Come){
						mHandler.post(new Runnable() {
							
							@Override
							public void run() {
								chatMsgAdapter.appendChatItem(entity);
								lvChatMsg.setSelection(chatMsgAdapter.getCount() - 1);
								lvChatMsg.setSelected(true);
							}
						});
					}
				}

				@Override
				public void errorCallback(String channel, PubnubError error) {
//					notifyUser("SUBSCRIBE : ERROR on channel " + channel
//							+ " : " + error.toString());
				}
			});

		} catch (Exception e) {

		}
	}
	
	private Handler mHandler = new Handler();
	private void publish(final ChatMsgEntity entity, String desChannel) {
		Hashtable<String, Object> args = new Hashtable<String, Object>(2);
		String message = JsonUtil.getInstance(this).GetJsonForChatMsgEntity(this, entity);

		if (args.get("message") == null) {
			try {
				Integer i = Integer.parseInt(message);
				args.put("message", i);
			} catch (Exception e) {

			}
		}
		if (args.get("message") == null) {
			try {
				Double d = Double.parseDouble(message);
				args.put("message", d);
			} catch (Exception e) {

			}
		}
		if (args.get("message") == null) {
			try {
				JSONArray js = new JSONArray(message);
				args.put("message", js);
			} catch (Exception e) {

			}
		}
		if (args.get("message") == null) {
			try {
				JSONObject js = new JSONObject(message);
				args.put("message", js);
			} catch (Exception e) {

			}
		}
		if (args.get("message") == null) {
			args.put("message", message);
		}

		// Publish Message
		args.put("channel", desChannel); // Channel Name

		pubnub.publish(args, new Callback() {
			@Override
			public void successCallback(String channel, Object message) {
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						chatMsgAdapter.successSendMsg(entity);
					}
				});
//				notifyUser("PUBLISH : " + message);
			}

			@Override
			public void errorCallback(String channel, PubnubError error) {
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						chatMsgAdapter.errorSendMsg(entity);
					}
				});
//				notifyUser("PUBLISH : " + error);
			}
		});
	}
}
