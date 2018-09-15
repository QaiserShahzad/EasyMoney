package com.easycoinbudget.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.easycoinbudget.R;

/**
 * Created by Muhammad on 06/04/2018.
 */

public class DataAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resource;

    private String[] data;

    public DataAdapter(Context context, int layoutResource, String[] data) {
        super(context, layoutResource, data);
        this.context = context;
        this.resource = layoutResource;
        this.data = data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        ViewHolder holder = null;

        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(resource, null);
            holder.entryName = (TextView) view.findViewById(R.id.tvEntryName);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        holder.entryName.setText(data[position]);

        return view;
    }

    @Override
    public int getCount(){
        return data.length;
    }

    public class ViewHolder{
        private TextView entryName;
    }

}
