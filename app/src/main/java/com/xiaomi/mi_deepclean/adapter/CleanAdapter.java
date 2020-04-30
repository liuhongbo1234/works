package com.xiaomi.mi_deepclean.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.xiaomi.mi_deepclean.R;
import com.xiaomi.mi_deepclean.model.CacheListItem;

import java.util.ArrayList;
import java.util.List;

public class CleanAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    public List<CacheListItem> mlistAppInfo;
    LayoutInflater infater = null;
    private Context mContext;
    public static List<Integer> clearIds;

    public CleanAdapter(Context context, List<CacheListItem> apps) {
        infater = LayoutInflater.from(context);
        mContext = context;
        clearIds = new ArrayList<Integer>();
        this.mlistAppInfo = apps;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mlistAppInfo.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mlistAppInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = infater.inflate(R.layout.listview_rublish_clean,
                    parent, false);
            holder = new ViewHolder();
            holder.appIcon = (ImageView) convertView
                    .findViewById(R.id.app_icon);
            holder.appName = (TextView) convertView
                    .findViewById(R.id.app_name);
            holder.cacheSize = (TextView) convertView
                    .findViewById(R.id.cache_size);
//            holder.appVersion = (TextView) convertView.findViewById(R.id.app_version);
            holder.cb = convertView.findViewById(R.id.choice_radio);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 找到条目
        final CacheListItem item = (CacheListItem) getItem(position);
        if (item != null) {
            holder.appIcon.setImageDrawable(item.getApplicationIcon());
            holder.appName.setText(item.getApplicationName());
            holder.cacheSize.setText("垃圾数据：" + Formatter.formatShortFileSize(mContext, item.getCacheSize()));
            holder.packageName = item.getPackageName();
            if (item.getChecked()) {
                holder.cb.setChecked(true);
            } else {
                holder.cb.setChecked(false);
            }
            holder.cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getChecked()) {
                        item.setChecked(false);
                    } else {
                        item.setChecked(true);
                    }
                    notifyDataSetChanged();
                }
            });
//            holder.appVersion.setText("版本：" + item.getAppVersion());
        }
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (viewHolder != null && viewHolder.packageName != null) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + viewHolder.packageName));
            mContext.startActivity(intent);
        }
    }

    class ViewHolder {
        ImageView appIcon;
        TextView appName;
        TextView cacheSize;
        TextView dataSize;
        TextView codeSize;
        RadioButton cb;
        //        TextView appVersion;
        String packageName;
    }

}
