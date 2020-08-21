package cc.buddies.cleanarch.login.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseFragment;
import cc.buddies.cleanarch.domain.request.RegisterParams;
import cc.buddies.cleanarch.login.viewmodel.RegisterViewModel;

public class RegisterFragment extends BaseFragment {

    private RegisterViewModel mRegisterViewModel;

    public RegisterFragment() {
        this(R.layout.fragment_register);
    }

    public RegisterFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mRegisterViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        this.mRegisterViewModel.registerLiveData.observe(getViewLifecycleOwner(), userModelDataResult -> {
            if (userModelDataResult.throwable != null) {
                String message = userModelDataResult.throwable.getMessage();
                if (message != null) {
                    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Navigation.findNavController(requireView()).navigateUp();
            }
        });
    }

    private void initView(@NonNull View view) {
        EditText editTextAccount = view.findViewById(R.id.edit_account);
        EditText editTextPassword = view.findViewById(R.id.edit_password);
        EditText editTextRepeatPassword = view.findViewById(R.id.edit_repeat_password);

        view.findViewById(R.id.btn_register).setOnClickListener(v -> {
            String account = editTextAccount.getText().toString();
            String password = editTextPassword.getText().toString();
            String repeatPassword = editTextRepeatPassword.getText().toString();

            RegisterParams registerParams = new RegisterParams(account, password, null, null);
            try {
                checkRegisterParams(registerParams, repeatPassword);

                mRegisterViewModel.register(registerParams);
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getMessage() != null) {
                    Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void checkRegisterParams(RegisterParams params, String repeatPassword) throws Exception {
        if (params == null) {
            throw new Exception("参数为空");
        }
        if (TextUtils.isEmpty(params.getAccount())) {
            throw new Exception("用户名为空");
        }
        if (TextUtils.isEmpty(params.getPassword())) {
            throw new Exception("密码为空");
        }
        if (!params.getPassword().equals(repeatPassword)) {
            throw new Exception("两次密码不一致");
        }
    }
}
