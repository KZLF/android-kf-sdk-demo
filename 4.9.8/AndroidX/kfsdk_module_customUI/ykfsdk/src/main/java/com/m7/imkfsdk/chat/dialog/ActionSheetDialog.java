package com.m7.imkfsdk.chat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.m7.imkfsdk.R;

import java.util.ArrayList;
import java.util.List;


public class ActionSheetDialog {
    private final Context context;
    private Dialog dialog;
    private TextView txt_title;
    private TextView txt_cancel;
    private LinearLayout lLayout_content;
    private ScrollView sLayout_content;
    private boolean showTitle = false;
    private List<SheetItem> sheetItemList;
    private final Display display;
    private dismiss dismissL;

    public ActionSheetDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ActionSheetDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.ykfsdk_pop_actionsheet, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());

        // 获取自定义Dialog布局中的控件
//        sLayout_content = (ScrollView) view.findViewById(R.id.sLayout_content);
        lLayout_content = view.findViewById(R.id.lLayout_content);
        txt_title = view.findViewById(R.id.txt_title);
        txt_cancel = view.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (dismissL != null) {
                    dismissL.dismissListener();
                }
            }
        });

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ykfsdk_ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public ActionSheetDialog setDismissL(dismiss dismissListener) {
        this.dismissL = dismissListener;
        return this;
    }

    public ActionSheetDialog setTitle(String title) {
        showTitle = true;
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(title);
        return this;
    }

    public ActionSheetDialog setCanacleColor(SheetItemColor color) {
        txt_cancel.setTextColor(Color.parseColor(color.getName()));
        return this;
    }

    /**
     * 设置点击返回键或屏幕，dialog消不消失
     * @param cancel
     * @return
     */
    public ActionSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
     * @param cancel
     * @return
     */
    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * @param strItem  条目名称
     * @param color    条目字体颜色，设置null则默认蓝色
     * @param listener
     * @return
     */
    public ActionSheetDialog addSheetItem(String strItem, SheetItemColor color,
                                          OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        sheetItemList.add(new SheetItem(strItem, color, listener));
        return this;
    }

    /**
     * 设置条目布局
     */
    private void setSheetItems() {
        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }

        int size = sheetItemList.size();

//        // TODO 高度控制，非最佳解决办法
//        // 添加条目过多的时候控制高度
//        if (size >= 7) {
//            LayoutParams params = (LayoutParams) sLayout_content
//                    .getLayoutParams();
//            params.height = display.getHeight() / 2;
//            sLayout_content.setLayoutParams(params);
//        }

        // 循环添加条目
        for (int i = 1; i <= size; i++) {
            final int index = i;
            SheetItem sheetItem = sheetItemList.get(i - 1);
            String strItem = sheetItem.name;
            SheetItemColor color = sheetItem.color;
            final OnSheetItemClickListener listener = sheetItem.itemClickListener;

            TextView textView = new TextView(context);
            textView.setText(strItem);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);

            // 背景图片
//            if (size == 1) {
//                if (showTitle) {
//                    textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
//                } else {
//                    textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
//                }
//            } else {
                if (showTitle) {
                    if (i >= 1 && i < size) {
                        textView.setBackgroundResource(R.drawable.ykfsdk_white_shape_radius);
                    } else {
//                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                        textView.setBackgroundResource(R.drawable.ykfsdk_white_shape_radius);
                    }
                } else {
                    if (i == 1) {
                        textView.setBackgroundResource(R.drawable.ykfsdk_white_shape_radius);
//                        textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                    } else if (i < size) {
                        textView.setBackgroundResource(R.drawable.ykfsdk_white_shape_radius);
                    } else {
//                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                        textView.setBackgroundResource(R.drawable.ykfsdk_white_shape_radius);
                    }
                }
//            }

            // 字体颜色
            if (color == null) {
                textView.setTextColor(Color.parseColor(SheetItemColor.Blue.getName()));
            } else {
                textView.setTextColor(Color.parseColor(color.getName()));
            }

            // 高度
            float scale = context.getResources().getDisplayMetrics().density;
            int height = (int) (45 * scale + 0.5f);
            textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));

            // 点击事件
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(index);
                    dialog.dismiss();
                }
            });

            lLayout_content.addView(textView);
        }
    }

    public void show() {
        setSheetItems();
        dialog.show();
    }

    public interface dismiss {
        void dismissListener();
    }

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }


    public class SheetItem {
        String name;
        OnSheetItemClickListener itemClickListener;
        SheetItemColor color;

        public SheetItem(String name, SheetItemColor color,
                         OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }

    public enum SheetItemColor {
        Blue("#037BFF"), Red("#FD4A2E"), BLACK("#23252F");

        private String name;

        SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
