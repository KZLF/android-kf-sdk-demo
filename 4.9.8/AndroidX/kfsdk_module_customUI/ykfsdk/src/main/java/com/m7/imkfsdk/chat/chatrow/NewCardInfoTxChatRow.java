package com.m7.imkfsdk.chat.chatrow;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.m7.imkfsdk.R;
import com.m7.imkfsdk.chat.ChatActivity;
import com.m7.imkfsdk.chat.holder.BaseHolder;
import com.m7.imkfsdk.chat.holder.NewCardInfoTxHolder;
import com.m7.imkfsdk.chat.holder.ViewHolderTag;
import com.moor.imkf.IMChatManager;
import com.moor.imkf.lib.utils.MoorLogUtils;
import com.moor.imkf.model.entity.FromToMessage;
import com.moor.imkf.model.entity.NewCardInfo;

import java.lang.reflect.Type;


/**
 * Description:点击发给坐席卡片类型
 *
 * @Author R-D
 * @Modification Time:2019/12/20
 */
public class NewCardInfoTxChatRow extends BaseChatRow {

    public NewCardInfoTxChatRow(int type) {
        super(type);
    }

    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu, View targetView, FromToMessage detail) {
        return false;
    }

    @Override
    protected void buildChattingData(final Context context, BaseHolder baseHolder, final FromToMessage message, int position) {
        NewCardInfoTxHolder holder = (NewCardInfoTxHolder) baseHolder;
        if (message != null && message.newCardInfo != null) {
            Type token = new TypeToken<NewCardInfo>() {
            }.getType();
            final NewCardInfo newCardInfo = new Gson().fromJson(message.newCardInfo, token);
            holder.tv_order_title.setText(newCardInfo.getTitle());
            holder.tv_order_.setText(newCardInfo.getSub_title());

            String img = newCardInfo.getImg();

            if (IMChatManager.getInstance().getImageLoader() != null) {
                IMChatManager.getInstance().getImageLoader().loadImage(false, false, img,
                        holder.iv_order_img, 0, 0, 5, null, null, null);
            } else {
                MoorLogUtils.eTag("ImageLoader", "ImageLoader is null");
            }

            View.OnClickListener listener = ((ChatActivity) context).getChatAdapter().getOnClickListener();
            ViewHolderTag holderTag = ViewHolderTag.createTag(message, ViewHolderTag.TagType.TAG_SEND_NEW_CARD, position);
            holder.tv_send_order.setTag(holderTag);
            holder.tv_send_order.setOnClickListener(listener);

            ViewHolderTag clickTag = ViewHolderTag.createTag(newCardInfo.getTarget(), ViewHolderTag.TagType.TAG_CLICK_NEW_CARD);
            holder.ll_order_main.setTag(clickTag);
            holder.ll_order_main.setOnClickListener(listener);

        }

    }

    @Override
    public View buildChatView(LayoutInflater inflater, View convertView) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ykfsdk_kf_chat_row_new_card_info_click_tx, null);
            NewCardInfoTxHolder holder = new NewCardInfoTxHolder(mRowType);
            convertView.setTag(holder.initBaseHolder(convertView, false));
        }
        return convertView;
    }

    @Override
    public int getChatViewType() {
        return ChatRowType.ORDER_INFO_ROW_TRANSMIT.ordinal();
    }
}

