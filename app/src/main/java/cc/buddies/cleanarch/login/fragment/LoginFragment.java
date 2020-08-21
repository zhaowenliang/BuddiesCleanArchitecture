package cc.buddies.cleanarch.login.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseFragment;
import cc.buddies.cleanarch.login.viewmodel.LoginViewModel;

public class LoginFragment extends BaseFragment {

    private LoginViewModel mLoginViewModel;

    public LoginFragment() {
        this(R.layout.fragment_login);
    }

    public LoginFragment(int contentLayoutId) {
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
        this.mLoginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        this.mLoginViewModel.authenticationState.observe(getViewLifecycleOwner(), authenticationStateDataResult -> {
            if (authenticationStateDataResult.throwable != null) {
                String message = authenticationStateDataResult.throwable.getMessage();
                if (message != null) {
                    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
                }
                return;
            }

            if (authenticationStateDataResult.data) {
                Navigation.findNavController(requireView()).navigateUp();
            }
        });
    }

    private void initView(@NonNull View view) {
        EditText editTextAccount = view.findViewById(R.id.edit_account);
        EditText editTextPassword = view.findViewById(R.id.edit_password);

        view.findViewById(R.id.btn_login).setOnClickListener(v -> {
            String account = editTextAccount.getText().toString();
            String password = editTextPassword.getText().toString();
            mLoginViewModel.authenticate(account, password);
        });

        view.findViewById(R.id.btn_register).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_to_register));
    }

}
