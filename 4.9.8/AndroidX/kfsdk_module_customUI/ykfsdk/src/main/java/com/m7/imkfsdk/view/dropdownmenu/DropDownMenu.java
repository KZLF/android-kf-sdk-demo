package com.m7.imkfsdk.view.dropdownmenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.m7.imkfsdk.R;

import java.util.ArrayList;
import java.util.List;

public class DropDownMenu extends LinearLayout {

    // Menu 展开的ListView 的 adapter
    private final List<MenuListAdapter> mMenuAdapters = new ArrayList<MenuListAdapter>();

    // Menu 展开的 list item
    private List<String[]> mMenuItems = new ArrayList<>();

    //菜单 上的文字
    private final List<TextView> mTvMenuTitles = new ArrayList<>();
    //菜单 的背景布局
    private final List<RelativeLayout> mRlMenuBacks = new ArrayList<>();
    //菜单 的箭头
    private final List<ImageView> mIvMenuArrow = new ArrayList<>();

    private Context mContext;

    private PopupWindow mPopupWindow;

    // Menu 展开的ListView
    private ListView mMenuList;
    // Menu 展开的ListView 下部的阴影
    private RelativeLayout mRlShadow;

    // 监听器
    private OnMenuSelectedListener mMenuSelectedListener;

    // 主Menu的个数
    private int mMenuCount;
    // Menu 展开的list 显示数量
    private int mShowCount;

    //选中行数
    private int mRowSelected = 0;
    //选中列数
    private int mColumnSelected = 0;

    //Menu的字体颜色
    private int mMenuTitleTextColor;
    //Menu的字体大小
    private int mMenuTitleTextSize;
    //Menu的按下的字体颜色
    private int mMenuPressedTitleTextColor;
    //Menu的按下背景
    private int mMenuPressedBackColor;
    //Menu的背景
    private int mMenuBackColor;
    //Menu list 的字体大小
    private int mMenuListTextSize;
    //Menu list 的字体颜色
    private int mMenuListTextColor;
    //是否显示选中的对勾
    private boolean mShowCheck;
    //是否现实列表的分割线
    private boolean mShowDivider;
    //列表的背景
    private int mMenuListBackColor;
    //列表的按下效果
    private int mMenuListSelectorRes;
    //箭头距离
    private int mArrowMarginTitle;

    //对勾的图片资源
    private int mCheckIcon;
    //向上的箭头图片资源
    private int mUpArrow;
    //向下的箭头图片资源
    private int mDownArrow;

    private boolean mDrawable = false;

    private String[] mDefaultMenuTitle;

    private boolean isDebug = true;

    private Drawable mMenuListDivider;//分割线

    private int mMenuListTSelectedTextColor;

    private Drawable mMenuListBackDrawable;//menu list bg drawable

    public DropDownMenu(Context mContext) {
        super(mContext);
        init(mContext);

    }

    public DropDownMenu(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        init(mContext);
    }

    private void init(Context mContext) {
        this.mContext = mContext;
        View popWindows = LayoutInflater.from(mContext).inflate(R.layout.ykfsdk_kf_popupwindow_menu, null);
        mPopupWindow = new PopupWindow(popWindows, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        mMenuList = popWindows.findViewById(R.id.lv_menu);
        mRlShadow = popWindows.findViewById(R.id.rl_menu_shadow);

        mMenuCount = 2;
        mShowCount = 5;
        mMenuTitleTextColor = getResources().getColor(R.color.ykfsdk_default_menu_text);
        mMenuPressedBackColor = getResources().getColor(R.color.ykfsdk_default_menu_press_back);
        mMenuPressedTitleTextColor = getResources().getColor(R.color.ykfsdk_default_menu_press_text);
        mMenuBackColor = getResources().getColor(R.color.ykfsdk_default_menu_back);
        mMenuListBackColor = getResources().getColor(R.color.ykfsdk_all_white);
        mMenuListSelectorRes = getResources().getColor(R.color.ykfsdk_all_white);
        mMenuTitleTextSize = 18;
        mArrowMarginTitle = 10;
        mShowCheck = true;
        mShowDivider = true;
        mCheckIcon = R.drawable.ykfsdk_ico_make;
        mUpArrow = R.drawable.ykfsdk_arrow_up;
        mDownArrow = R.drawable.ykfsdk_arrow_down;
    }

    /**
     * 设置 Menu的item
     */
    public void setmMenuItems(List<String[]> menuItems) {
        mMenuItems = menuItems;
        mDrawable = true;
        invalidate();
    }

    /**
     * 获取Menu的datasource
     */
    public List<String[]> getmMenuItems() {
        return mMenuItems;
    }

    /**
     * 获取Menu的Item
     */
    public String getmMenuItem(int columnIndex, int rowIndex) {
        return mMenuItems.get(columnIndex)[rowIndex];
    }


    // 设置 Menu的数量
    public void setmMenuCount(int menuCount) {
        mMenuCount = menuCount;
    }


    public void setShowDivider(boolean mShowDivider) {
        this.mShowDivider = mShowDivider;
    }

    public void setmMenuListBackColor(int menuListBackColor) {
        mMenuListBackColor = menuListBackColor;
    }

    public void setmMenuListSelectorRes(int menuListSelectorRes) {
        mMenuListSelectorRes = menuListSelectorRes;
    }

    public void setmArrowMarginTitle(int arrowMarginTitle) {
        mArrowMarginTitle = arrowMarginTitle;
    }

    public void setmMenuPressedTitleTextColor(int menuPressedTitleTextColor) {
        mMenuPressedTitleTextColor = menuPressedTitleTextColor;
    }

    public void setDefaultMenuTitle(String[] mDefaultMenuTitle) {
        this.mDefaultMenuTitle = mDefaultMenuTitle;
    }

    public void setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    // 设置 show 数量
    public void setmShowCount(int showCount) {
        mShowCount = showCount;
    }

    // 设置 Menu的字体颜色
    public void setmMenuTitleTextColor(int menuTitleTextColor) {
        mMenuTitleTextColor = menuTitleTextColor;
    }

    // 设置 Menu的字体大小
    public void setmMenuTitleTextSize(int menuTitleTextSize) {
        mMenuTitleTextSize = menuTitleTextSize;
    }

    //设置Menu的背景色
    public void setmMenuBackColor(int menuBackColor) {
        mMenuBackColor = menuBackColor;
    }

    //设置Menu的按下背景色
    public void setmMenuPressedBackColor(int menuPressedBackColor) {
        mMenuPressedBackColor = menuPressedBackColor;
    }

    //设置Menu list的字体颜色
    public void setmMenuListTextColor(int menuListTextColor) {
        mMenuListTextColor = menuListTextColor;
        for (int i = 0; i < mMenuAdapters.size(); i++) {
            mMenuAdapters.get(i).setTextColor(mMenuListTextColor);
        }
    }

    //设置Menu list的字体大小
    public void setmMenuListTextSize(int menuListTextSize) {
        mMenuListTextSize = menuListTextSize;
        for (int i = 0; i < mMenuAdapters.size(); i++) {
            mMenuAdapters.get(i).setTextSize(menuListTextSize);
        }
    }

    //设置是否显示对勾
    public void setShowCheck(boolean mShowCheck) {
        this.mShowCheck = mShowCheck;
    }

    //设置对勾的icon
    public void setmCheckIcon(int checkIcon) {
        mCheckIcon = checkIcon;
    }

    public void setmUpArrow(int upArrow) {
        mUpArrow = upArrow;
    }

    public void setmDownArrow(int downArrow) {
        mDownArrow = downArrow;
    }

    public void setMenuSelectedListener(OnMenuSelectedListener menuSelectedListener) {
        mMenuSelectedListener = menuSelectedListener;
    }

    /**
     * 设置分割线
     */
    public void setmMenuListDivider(Drawable divider) {
        this.mMenuListDivider = divider;
    }

    /**
     * 设置List选中的textcolor
     */
    public void setmMenuListSelectedTextColor(int textColor) {
        this.mMenuListTSelectedTextColor = textColor;
    }

    /**
     * 设置背景
     */
    public void setmMenuListBackDrawable(Drawable mMenuListBackDrawable) {
        this.mMenuListBackDrawable = mMenuListBackDrawable;
    }

    //设置默认选中
    public void setSelectIndex(int pos) {
        init();

        if (mMenuListTSelectedTextColor != 0) {
            mMenuAdapters.get(0).setTextColorSelected(mMenuListTextColor);
        }
        mMenuList.setAdapter(mMenuAdapters.get(0));
        if (mMenuAdapters.get(0).getCount() > mShowCount) {
            View childView = mMenuAdapters.get(0).getView(0, null, mMenuList);
            childView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    childView.getMeasuredHeight() * mShowCount);
            mMenuList.setLayoutParams(parms);
        } else {
            View childView = mMenuAdapters.get(0).getView(0, null, mMenuList);
            childView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            mMenuList.setLayoutParams(parms);
        }
        if (!mShowDivider) {
            mMenuList.setDivider(null);
        } else {
            if (null != mMenuListDivider)
                mMenuList.setDivider(mMenuListDivider);
        }
        mMenuList.setBackgroundColor(mMenuListBackColor);

        if (null != mMenuListBackDrawable) {
            mMenuList.setBackgroundDrawable(mMenuListBackDrawable);
        }
        mMenuList.setSelector(mMenuListSelectorRes);
        mColumnSelected = 0;
        mTvMenuTitles.get(0).setTextColor(mMenuPressedTitleTextColor);
//                        mRlMenuBacks.get(index).setBackgroundColor(mMenuPressedBackColor);
        mIvMenuArrow.get(0).setImageResource(mUpArrow);





        mRowSelected = pos;
        mTvMenuTitles.get(mColumnSelected).setText(mMenuItems.get(mColumnSelected)[mRowSelected]);
        mIvMenuArrow.get(mColumnSelected).setImageResource(mDownArrow);
        mMenuAdapters.get(mColumnSelected).setSelectIndex(mRowSelected);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
    }

    private void init(){
        if (mDrawable) {
            mPopupWindow.setTouchable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

            mRlShadow.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });

            mMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mPopupWindow.dismiss();
                    mRowSelected = position;

                    mTvMenuTitles.get(mColumnSelected).setText(mMenuItems.get(mColumnSelected)[mRowSelected]);
                    mIvMenuArrow.get(mColumnSelected).setImageResource(mDownArrow);
                    mMenuAdapters.get(mColumnSelected).setSelectIndex(mRowSelected);
                    if (mMenuSelectedListener == null && isDebug)
                        Toast.makeText(mContext, "MenuSelectedListener is  null", Toast.LENGTH_LONG).show();
                    else
                        mMenuSelectedListener.onSelected(view, mRowSelected, mColumnSelected);
                }
            });

            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    for (int i = 0; i < mMenuCount; i++) {
//                        mIvMenuArrow.get(i).setImageResource(mDownArrow);
                        mIvMenuArrow.get(i).setImageDrawable(ContextCompat.getDrawable(getContext(), mDownArrow));
//                        mRlMenuBacks.get(i).setBackgroundColor(mMenuBackColor);
                        mTvMenuTitles.get(i).setTextColor(mMenuTitleTextColor);
                    }
                }
            });

            if (mMenuItems.size() != mMenuCount) {
                if (isDebug)
                    Toast.makeText(mContext, "Menu item is not setted or incorrect", Toast.LENGTH_LONG).show();
                return;
            }

            if (mMenuAdapters.size() == 0) {
                for (int i = 0; i < mMenuCount; i++) {
                    MenuListAdapter adapter = new MenuListAdapter(mContext, mMenuItems.get(i));
                    adapter.setShowCheck(mShowCheck);
                    adapter.setCheckIcon(mCheckIcon);
                    mMenuAdapters.add(adapter);
                }
            } else if (mMenuAdapters.size() != mMenuCount) {
                if (isDebug) {
                    Toast.makeText(mContext, "If you want set Adapter by yourself,please ensure the number of adpaters equal mMenuCount", Toast.LENGTH_LONG).show();
                }
                return;
            }
            int width = getWidth();

            for (int i = 0; i < mMenuCount; i++) {
                final RelativeLayout v = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.ykfsdk_kf_menu_item, null, false);

                TextView tv = v.findViewById(R.id.tv_menu_title);
                tv.setTextColor(mMenuTitleTextColor);
                tv.setTextSize(mMenuTitleTextSize);
                if (mDefaultMenuTitle == null || mDefaultMenuTitle.length == 0) {
                    tv.setText(mMenuItems.get(i)[0]);
                } else {
//                    tv.setText(mDefaultMenuTitle[i]);
                    tv.setText(mContext.getString(R.string.ykfsdk_ykf_please_choose));
                }
                this.addView(v, i);
                mTvMenuTitles.add(tv);

                RelativeLayout rl = v.findViewById(R.id.rl_menu_head);
                mRlMenuBacks.add(rl);

                ImageView iv = v.findViewById(R.id.iv_menu_arrow);
                mIvMenuArrow.add(iv);
                mIvMenuArrow.get(i).setImageResource(mDownArrow);

                final int index = i;
                v.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mMenuListTSelectedTextColor != 0) {
                            mMenuAdapters.get(index).setTextColorSelected(mMenuListTextColor);
                        }
                        mMenuList.setAdapter(mMenuAdapters.get(index));
                        if (mMenuAdapters.get(index).getCount() > mShowCount) {
                            View childView = mMenuAdapters.get(index).getView(0, null, mMenuList);
                            childView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                                    childView.getMeasuredHeight() * mShowCount);
                            mMenuList.setLayoutParams(parms);
                        } else {
                            View childView = mMenuAdapters.get(index).getView(0, null, mMenuList);
                            childView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                            mMenuList.setLayoutParams(parms);
                        }
                        if (!mShowDivider) {
                            mMenuList.setDivider(null);
                        } else {
                            if (null != mMenuListDivider)
                                mMenuList.setDivider(mMenuListDivider);
                        }
                        mMenuList.setBackgroundColor(mMenuListBackColor);

                        if (null != mMenuListBackDrawable) {
                            mMenuList.setBackgroundDrawable(mMenuListBackDrawable);
                        }
                        mMenuList.setSelector(mMenuListSelectorRes);
                        mColumnSelected = index;
                        mTvMenuTitles.get(index).setTextColor(mMenuPressedTitleTextColor);
//                        mRlMenuBacks.get(index).setBackgroundColor(mMenuPressedBackColor);
                        mIvMenuArrow.get(index).setImageResource(mUpArrow);
                        mPopupWindow.showAsDropDown(v);
                    }
                });
            }
            mDrawable = false;
        }
    }
}
