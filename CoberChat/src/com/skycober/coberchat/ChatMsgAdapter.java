package com.skycober.coberchat;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;

import com.skycober.coberchat.ChatMsgEntity.MsgContentType;
import com.skycober.coberchat.ChatMsgEntity.MsgSendState;
import com.skycober.coberchat.ChatMsgEntity.MsgType;
import com.skycober.coberchat.util.CalendarUtil;
import com.skycober.coberchat.util.ExpressionUtil;
import com.skycober.coberchat.util.CalendarUtil.DateDisplayType;

import android.content.Context;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ChatMsgAdapter extends BaseAdapter {
	
	private static final String TAG = ChatMsgAdapter.class.getSimpleName();

	private List<ChatMsgEntity> coll;

	private Context ctx;

	private LayoutInflater mInflater;

	private FinalBitmap fbPic;
	
	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}

	public ChatMsgAdapter() {
	}

	public ChatMsgAdapter(Context context, List<ChatMsgEntity> list) {
		this.ctx = context;
		this.coll = list;
		mInflater = LayoutInflater.from(context);
		fbPic = FinalBitmap.create(context);
	}
	
	public void appendChatItem(ChatMsgEntity entity){
		if(null == this.coll){
			this.coll = new ArrayList<ChatMsgEntity>();
		}
		this.coll.add(entity);
		notifyDataSetChanged();
	}
	
	public boolean successSendMsg(ChatMsgEntity entity){
		if(null != entity && null != entity.getMsgId() && !"".equalsIgnoreCase(entity.getMsgId()) && null != this.coll && this.coll.size() > 0){
			String modelId = entity.getMsgId();
			for (ChatMsgEntity msg : coll) {
				String msgId = msg.getMsgId();
				if(null != msgId && msgId.equalsIgnoreCase(modelId)){
					msg.setMsgSendState(MsgSendState.Suc);
					notifyDataSetChanged();
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean errorSendMsg(ChatMsgEntity entity){
		if(null != entity && null != entity.getMsgId() && !"".equalsIgnoreCase(entity.getMsgId()) && null != this.coll && this.coll.size() > 0){
			String modelId = entity.getMsgId();
			for (ChatMsgEntity msg : coll) {
				String msgId = msg.getMsgId();
				if(null != msgId && msgId.equalsIgnoreCase(modelId)){
					msg.setMsgSendState(MsgSendState.Fail);
					notifyDataSetChanged();
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public int getCount() {
		return null == coll ? 0 : coll.size();
	}

	@Override
	public Object getItem(int position) {
		return coll.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		ChatMsgEntity entity = coll.get(position);

		if (entity.getMsgType() == MsgType.Come) {
			return IMsgViewType.IMVT_COM_MSG;
		} else {
			return IMsgViewType.IMVT_TO_MSG;
		}
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatMsgEntity entity = coll.get(position);
		MsgType msgType = entity.getMsgType();
		MsgContentType msgContentType = entity.getMsgContentType();
		ViewHolder viewHolder = null;	
	    if (convertView == null)
	    {
	    	viewHolder = new ViewHolder();
	    	switch (msgContentType) {
	    	case Pic:
				if(msgType == MsgType.Come){
					convertView = mInflater.inflate(R.layout.chatting_item_msg_pic_left, null);
				}else if(msgType == MsgType.Go){
					convertView = mInflater.inflate(R.layout.chatting_item_msg_pic_right, null);
				}
				viewHolder.ivPic = (ImageView) convertView.findViewById(R.id.iv_chatPic);
	    		break;
	    	case Text:
			case Audio:
			case Video:
			default:
				if(msgType == MsgType.Come){
					convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
				}else if(msgType == MsgType.Go){
					convertView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
				}
				viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
				break;
			}
			viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
			viewHolder.ivFailIcon = (ImageView) convertView.findViewById(R.id.ivFailIcon);
			viewHolder.pbSend = (ProgressBar) convertView.findViewById(R.id.pbSend);
			viewHolder.msgType = msgType;
			convertView.setTag(viewHolder);
	    }else{
	        viewHolder = (ViewHolder) convertView.getTag();
	    }
	    String sendDate = CalendarUtil.getInstance(ctx).getFormatDate(DateDisplayType.YMDHMS, entity.getDate(), false);
	    viewHolder.tvSendTime.setText(sendDate);
	    viewHolder.tvUserName.setText(entity.getUserName());
	    
	    if(msgContentType == MsgContentType.Pic){
	    	String picUrl = entity.getPicUrl();
	    	fbPic.display(viewHolder.ivPic, picUrl);
	    }else{
	    	String str = entity.getText(); 
	    	String zhengze = "f0[0-9]{2}|f10[0-7]";
	    	try {
	    		SpannableString spannableString = ExpressionUtil.getExpressionString(ctx, str, zhengze);
	    		viewHolder.tvContent.setText(spannableString);
	    	} catch (Exception e) {
	    		Log.e(TAG, "chat parse emoj error", e);
	    	}
	    }
		
		if(msgType == MsgType.Go){
			MsgSendState msgSendState = entity.getMsgSendState();
			switch (msgSendState) {
			case Init:
			case Ready:
			case Sending:
				viewHolder.ivFailIcon.setVisibility(View.GONE);
				viewHolder.pbSend.setVisibility(View.VISIBLE);
				break;
			case Fail:
				viewHolder.pbSend.setVisibility(View.GONE);
				viewHolder.ivFailIcon.setVisibility(View.VISIBLE);
				break;
			case Suc:
			default:
				viewHolder.ivFailIcon.setVisibility(View.GONE);
				viewHolder.pbSend.setVisibility(View.GONE);
				break;
			}
		}
	    return convertView;
	}
	
	static class ViewHolder { 
        public TextView tvSendTime;
        public TextView tvUserName;
        public ImageView ivPic;
        public TextView tvContent;
        public ImageView ivFailIcon;
        public ProgressBar pbSend;
        public MsgType msgType = MsgType.Come;
    }
}
