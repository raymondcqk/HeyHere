package com.raymondqk.raysqlitepractice.fragment.cityselect;

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

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.interfaces.LocationSelectedCallback;
import com.raymondqk.raysqlitepractice.utils.db.DBHelper;

import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/22 0022.
 */
public class CityFragment extends Fragment {

    private ListView mLv;
    private ProgressBar mProgressBar;
    private static LocationSelectedCallback sCallback;
    private List<String> mCityLisy;

    public static CityFragment getInstance(LocationSelectedCallback callback) {
        sCallback = callback;
        return new CityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, null);
        mLv = (ListView) view.findViewById(R.id.lv_weather_city);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_weather_city);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initListView(Context context, String prov) {
        DBHelper dbHelper = DBHelper.getDBHelperInstance(context);
        mCityLisy = dbHelper.queryCity(prov);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
                mCityLisy);
        mProgressBar.setVisibility(View.GONE);
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sCallback.onSelected(mCityLisy.get(position));
            }
        });

    }
}
