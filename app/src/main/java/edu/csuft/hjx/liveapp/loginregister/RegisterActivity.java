package edu.csuft.hjx.liveapp.loginregister;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import edu.csuft.hjx.liveapp.R;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar mTitlebar;
    private EditText mAccountEdt;
    private EditText mPasswordEdt;
    private EditText mConfirmPasswordEt;
    private Button mRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findAllViews();
        setListeners();
        setTitleBar();
    }

    private void findAllViews() {
        mTitlebar = (Toolbar) findViewById(R.id.titlebar);
        mAccountEdt = (EditText) findViewById(R.id.account);
        mPasswordEdt = (EditText) findViewById(R.id.password);
        mConfirmPasswordEt = (EditText) findViewById(R.id.confirm_password);
        mRegisterBtn = (Button) findViewById(R.id.register);
    }

    private void setTitleBar() {
        mTitlebar.setTitle("注册新用户");
        mTitlebar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mTitlebar);
    }

    private void setListeners() {
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册
                register();
            }
        });
    }

    private void register() {
        String userAccountStr = mAccountEdt.getText().toString();
        String userPasswordStr = mPasswordEdt.getText().toString();
        String userConfirmPswStr = mConfirmPasswordEt.getText().toString();

        if (TextUtils.isEmpty(userAccountStr) ||
                TextUtils.isEmpty(userPasswordStr) ||
                TextUtils.isEmpty(userConfirmPswStr)) {
            Toast.makeText(this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!userPasswordStr.equals(userConfirmPswStr)) {
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        registerActurally(userAccountStr, userPasswordStr);
    }

    private void registerActurally(String userAccountStr, String userPasswordStr) {
        ILiveLoginManager.getInstance().tlsRegister(userAccountStr, userPasswordStr, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                Toast.makeText(RegisterActivity.this, "创建用户成功，请登录！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Toast.makeText(RegisterActivity.this, "创建用户失败，错误码："+errCode+errMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
