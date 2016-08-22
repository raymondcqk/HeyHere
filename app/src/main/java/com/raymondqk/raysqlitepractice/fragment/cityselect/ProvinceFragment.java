package com.raymondqk.raysqlitepractice.fragment.cityselect;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.interfaces.LocationSelectedCallback;
import com.raymondqk.raysqlitepractice.utils.db.DBHelper;

import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/22 0022.
 */
public class ProvinceFragment extends Fragment {

    private static LocationSelectedCallback sCallback;
    private static Context sContext;
    private ListView mLv;
    private SimpleAdapter mSimpleAdapter;
    private DBHelper mDbHelper;
    private ProgressBar mProgressBar;
    private List<String> mProvs;


    public static ProvinceFragment getInstance(Context context, LocationSelectedCallback callback) {
        sCallback = callback;
        return new ProvinceFragment();
        //        Bundle args = new Bundle();
        //        args.put
        //        fragment.setArguments(args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        sContext = activity;
        mDbHelper = DBHelper.getDBHelperInstance(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_province, null);
        mLv = (ListView) view.findViewById(R.id.lv_weather_province);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_weather_province);
        sCallback.onInitView();
        return view;
    }

    public void initListView(final Context context, List<String> list) {
        mProvs = list;
        mProgressBar.setVisibility(View.GONE);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
                        list);
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sCallback.onSelected(mProvs.get(position));
            }
        });
    }
}
