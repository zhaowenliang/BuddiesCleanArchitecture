package cc.buddies.cleanarch.person.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.io.IOException;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.common.base.BaseFragment;
import cc.buddies.cleanarch.data.files.FileCache;
import cc.buddies.cleanarch.data.manager.UserManager;
import cc.buddies.cleanarch.domain.model.UserModel;
import cc.buddies.cleanarch.person.viewmodel.PersonInfoViewModel;
import cc.buddies.component.common.utils.ToastUtils;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PersonInfoFragment extends BaseFragment {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_ALBUM = 2;
    private static final int REQUEST_CROP = 3;

    private PersonInfoViewModel mPersonInfoViewModel;

    private TextView mTextNickname;
    private ImageView mImageAvatar;

    private File mImageFile;

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

        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mPersonInfoViewModel = new ViewModelProvider(this).get(PersonInfoViewModel.class);

        this.mPersonInfoViewModel.modifyUserNicknameLiveData.observe(getViewLifecycleOwner(), dataResult -> {
            dismissLoadingDialog();

            if (dataResult.throwable != null) {
                ToastUtils.shortToast(requireContext(), dataResult.throwable.getMessage());
            } else {
                ToastUtils.shortToast(requireContext(), "修改成功");

                // 缓存用户数据
                UserModel userInfo = UserManager.getInstance().getUserInfo();
                if (userInfo != null) {
                    userInfo.setNickname(dataResult.data);
                    UserManager.getInstance().saveUserInfo(userInfo);
                }

                mTextNickname.setText(dataResult.data);
            }
        });

        this.mPersonInfoViewModel.modifyUserAvatarLiveData.observe(getViewLifecycleOwner(), dataResult -> {
            dismissLoadingDialog();

            if (dataResult.throwable != null) {
                ToastUtils.shortToast(requireContext(), dataResult.throwable.getMessage());
            } else {
                ToastUtils.shortToast(requireContext(), "修改成功");

                // 缓存用户数据
                UserModel userInfo = UserManager.getInstance().getUserInfo();
                if (userInfo != null) {
                    userInfo.setAvatar(dataResult.data);
                    UserManager.getInstance().saveUserInfo(userInfo);
                }

                // 因为没有接口，所有数据都放到了本地，所以才能把url直接当作本地文件使用。
//                File file = new File(dataResult.data);
//                Uri cropUri = Uri.fromFile(file);
//                mImageAvatar.setImageURI(cropUri);
                Glide.with(mImageAvatar)
                        .load(dataResult.data)
                        .transform(new CircleCrop())
                        .into(mImageAvatar);
            }
        });

        fileData();
    }

    private void initView(@NonNull View view) {
        view.findViewById(R.id.panel_avatar).setOnClickListener(v -> selectAlbum());
        view.findViewById(R.id.panel_nickname).setOnClickListener(v -> showInputDialog());

        mTextNickname = view.findViewById(R.id.text_nickname);
        mImageAvatar = view.findViewById(R.id.image_avatar);
    }

    private void fileData() {
        UserModel userInfo = UserManager.getInstance().getUserInfo();
        if (userInfo != null) {
            String nickname = userInfo.getNickname();
            String avatar = userInfo.getAvatar();

            mTextNickname.setText(nickname);

            Glide.with(mImageAvatar)
                    .load(avatar)
                    .transform(new CircleCrop())
                    .into(mImageAvatar);
        }
    }

    private void selectAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, REQUEST_ALBUM);
    }

    private void cropImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mImageFile));
        startActivityForResult(intent, REQUEST_CROP);
    }

    private void createImageFile() {
        File imagePath = FileCache.getImagePath(requireContext());
        this.mImageFile = new File(imagePath, System.currentTimeMillis() + ".jpg");

        try {
            FileCache.createFile(mImageFile);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "出错啦", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ALBUM:
                createImageFile();
                if (!mImageFile.exists()) {
                    break;
                }

                Uri uri = data != null ? data.getData() : null;
                if (uri != null) {
                    cropImage(uri);
                }
                break;

            case REQUEST_CROP:
                // 修改头像
                modifyAvatar(mImageFile.getAbsolutePath());
                break;
        }
    }

    private void showInputDialog() {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(requireContext());

        EditText editText = new EditText(requireContext());
        editText.setSingleLine();

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(layoutParams);

        materialAlertDialogBuilder
                .setTitle("请输入昵称")
                .setView(editText)
                .setPositiveButton("确定", (dialog, which) -> {
                    String inputContent = editText.getText().toString();
                    if (TextUtils.isEmpty(inputContent.trim())) {
                        ToastUtils.shortToast(requireContext(), "昵称不能为空");
                    } else {
                        modifyNickname(inputContent);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void modifyAvatar(String url) {
        showLoadingDialog();
        mPersonInfoViewModel.modifyUserAvatar(url);
    }

    private void modifyNickname(String nickname) {
        showLoadingDialog();
        mPersonInfoViewModel.modifyUserNickname(nickname);
    }
}
