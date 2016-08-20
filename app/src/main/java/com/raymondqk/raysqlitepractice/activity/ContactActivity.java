package com.raymondqk.raysqlitepractice.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.adapter.ContactLvAdapter;
import com.raymondqk.raysqlitepractice.model.ContactDetail;
import com.raymondqk.raysqlitepractice.utils.VerifyPermissionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈其康 raymondchan on 2016/8/16 0016.
 */
public class ContactActivity extends AppCompatActivity {

    private ListView mLv_contact;
    private List<ContactDetail> mContactDetails;
    private ContactLvAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact);
        VerifyPermissionUtils.verifyContactPermission(this);
        initView();
        readContacts();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private void readContacts() {

        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = null;

        try {
            cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ContactDetail contactDetail = new ContactDetail();
                    //联系人姓名
                    String displayName = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //手机号
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactDetail.setUsername(displayName);
                    contactDetail.setPhone(number);
                    mContactDetails.add(contactDetail);
                    // TODO: 2016/8/17 0017 性能疑问：这种方式，每一个联系人一个对象，当联系人数量大的时候，会占用大量内存，联系人对象可以只用一个吗？
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }

    }

    private void initView() {
        mLv_contact = (ListView) findViewById(R.id.lv_contact);
        mContactDetails = new ArrayList<ContactDetail>();
        mAdapter = new ContactLvAdapter(this, mContactDetails);
        mLv_contact.setAdapter(mAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_contact);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        readContacts();
        mAdapter.notifyDataSetChanged();
    }
}
