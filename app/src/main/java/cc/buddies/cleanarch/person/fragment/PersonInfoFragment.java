package cc.buddies.cleanarch.person.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseFragment;

public class PersonInfoFragment extends BaseFragment {

    public PersonInfoFragment() {
        this(R.layout.fragment_person_info);
    }

    public PersonInfoFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.title_bar);
        initTitleBar(toolbar);
        fitTranslucentStatusBar(toolbar);
        translucentStatusBar(true);

        setTitle(R.string.settings_person_info_label);
    }
}
