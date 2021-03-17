package com.example.mvp_bill.view;

import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvp_bill.R;
import com.example.mvp_bill.model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText new_user_name;
    private EditText new_user_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        new_user_name = findViewById(R.id.new_user_name);
        new_user_pwd = findViewById(R.id.new_user_pwd);
        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(v -> {
            User user = new User();
            user.setUsername(new_user_name.getText().toString());
            user.setPassword(new_user_pwd.getText().toString());
            user.signUp(new SaveListener<User>() {
                @Override
                public void done(User bmobUser, BmobException e) {
                    if (e == null) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "注册失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}
