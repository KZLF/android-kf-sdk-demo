package com.m7.imkfsdk.chat.holder;

import android.view.View;
import android.widget.TextView;

import com.m7.imkfsdk.R;

/**
 * Created by longwei on 2017/12/11.
 */

public class BreakTipHolder extends BaseHolder{

    private TextView tv_content;

    public BreakTipHolder(int type) {
        super(type);
    }

    public BaseHolder initBaseHolder(View baseView, boolean isReceive) {
        super.initBaseHolder(baseView);

        //通过baseview找到对应组件
        tv_content = baseView.findViewById(R.id.chat_content_tv);
        if(isReceive) {
            type = 11;
        }
        return this;
    }

    public TextView getDescTextView() {
        if(tv_content == null) {
            tv_content = getBaseView().findViewById(R.id.chat_content_tv);
        }
        return tv_content;
    }
}
