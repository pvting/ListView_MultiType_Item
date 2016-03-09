package com.pvting.listview_multitype_item;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 在listview的适配器中添加两个关于类型的说明的方法
 * 分别是getItemViewType和getViewTypeCount
 */
public class MainActivity extends ListActivity {

    // item view的类型总数。
    private final int VIEW_TYPE_COUNT = 2;

    private final String DATA = "data";
    private final String TYPE = "type";

    private final int GROUP = -2;
    private final int ITEM = -3;

    private ArrayList<HashMap<String, Object>> items = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = this.getListView();

        BaseAdapter adapter = new MyBaseAdapter();

        items = new ArrayList<>();

        listView.setAdapter(adapter);

        String[] groups = { "A", "B", "C", "D", "E", "F" };
        int count = 0;
        for (int i = 0; i < groups.length; i++) {
            HashMap<String, Object> group_map = new HashMap<>();
            group_map.put(TYPE, GROUP);
            group_map.put(DATA, groups[i]);
            items.add(group_map);

            for (int j = 0; j < 5; j++) {
                HashMap<String, Object> data_map = new HashMap<>();
                data_map.put(TYPE, ITEM);
                data_map.put(DATA, "数据:" + (count++));
                items.add(data_map);
            }
        }
    }

    private class MyBaseAdapter extends BaseAdapter {

        private LayoutInflater inflater = null;

        public MyBaseAdapter() {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            HashMap<String, Object> map = items.get(position);
            return map.get(DATA);
        }

        // 返回的id可以自己定制。
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int type = getItemViewType(position);

            // 根据不同的view type加载不同的布局文件。
            switch (type) {
                case GROUP:
                    convertView = inflater.inflate(android.R.layout.simple_list_item_2, null);
                    TextView tv1 = (TextView) convertView.findViewById(android.R.id.text1);
                    tv1.setText("分组");
                    tv1.setBackgroundColor(Color.RED);
                    TextView tv2 = (TextView) convertView.findViewById(android.R.id.text2);
                    tv2.setText(getItem(position) + "");
                    tv2.setBackgroundColor(Color.GRAY);
                    break;
                case ITEM:
                    convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
                    TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
                    tv.setText(getItem(position) + "");
                    break;
            }

            return convertView;
        }

        // 解析view type。
        // view type值是我们事先埋入到items数据集中的字段值。
        // 注意!此处返回的值不要大于getViewTypeCount()的返回值。
        @Override
        public int getItemViewType(int position) {
            HashMap<String, Object> map = items.get(position);
            return (Integer) map.get(TYPE);
        }

        // 在本例中共计有2个不同类型的view
        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE_COUNT;
        }
    }
}
