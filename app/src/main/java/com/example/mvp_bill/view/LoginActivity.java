package com.example.mvp_bill.view;

import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp_bill.R;
import com.example.mvp_bill.model.User;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText user_name;
    private EditText user_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this,"9741f7e630aefc00a4958af82f592b3d");
        setContentView(R.layout.activity_login);
        user_name = findViewById(R.id.user_name);
        user_pwd = findViewById(R.id.user_pwd);
        Button login = findViewById(R.id.login);
        TextView find_pwd = findViewById(R.id.find_pwd);
        TextView register = findViewById(R.id.register);
        login.setOnClickListener(this);
        find_pwd.setOnClickListener(this);
        register.setOnClickListener(this);
        //登录缓存
        if (BmobUser.isLogin()) {
            User user = BmobUser.getCurrentUser(User.class);
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("userName",user.getUsername());
            startActivity(intent);
            finish();
        } else {
            Snackbar.make(getWindow().getDecorView(), "尚未登录，请先登录", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login:
                User user = new User();
                user.setUsername(user_name.getText().toString());
                user.setPassword(user_pwd.getText().toString());
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if(e == null){
                            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("userName",user.getUsername());
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "登陆失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.find_pwd:
                Toast.makeText(this, "找回密码", Toast.LENGTH_SHORT).show();
                //TODO 找回密码
                break;
            case R.id.register:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
