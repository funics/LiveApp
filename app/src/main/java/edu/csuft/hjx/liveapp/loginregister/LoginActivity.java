package edu.csuft.hjx.liveapp.loginregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import edu.csuft.hjx.liveapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText mAccountEdt;
    private EditText mPasswordEdt;
    private Button mLoginBtn;
    private Button mRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findAllViews();
        setListeners();
    }

    private void findAllViews() {
        mAccountEdt = (EditText) findViewById(R.id.account);
        mPasswordEdt = (EditText) findViewById(R.id.password);
        mLoginBtn = (Button) findViewById(R.id.login);
        mRegisterBtn = (Button) findViewById(R.id.register);
    }

    private void setListeners() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录操作
                login();
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册的操作
                register();
            }
        });
    }

    private void login() {
        final String userAcountStr = mAccountEdt.getText().toString();
        String userPasswordStr = mPasswordEdt.getText().toString();

        //为空检测
        if (TextUtils.isEmpty(userAcountStr) || TextUtils.isEmpty(userPasswordStr)){
            Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
            return ;
        }
        loginActurally(userAcountStr, userPasswordStr);
    }

    private void loginActurally(final String userAcountStr, String userPasswordStr) {
        ILiveLoginManager.getInstance().tlsLogin( userAcountStr, userPasswordStr, new ILiveCallBack<String>() {
            @Override
            public void onSuccess(String data) {
                ILiveLoginManager.getInstance().iLiveLogin(userAcountStr, data, new ILiveCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        //完全登录成功
                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        Toast.makeText(LoginActivity.this, "登录失败！错误码:" + errCode + " " + errMsg, Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                Toast.makeText(LoginActivity.this, "登录失败！错误码:" + errCode + " " + errMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register() {
        //跳转到注册页面
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        startActivity(intent);
    }
}
