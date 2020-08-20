package cc.buddies.cleanarch.person.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseActivity;

public class PersonInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
    }
}
