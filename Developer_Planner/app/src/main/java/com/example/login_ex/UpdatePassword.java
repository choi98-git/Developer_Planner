package com.example.login_ex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String newPassword = "";
    EditText updatePasswordEdit, checkUpdatePasswordEdit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();

        updatePasswordEdit = (EditText) findViewById(R.id.updatePassword);
        checkUpdatePasswordEdit = (EditText) findViewById(R.id.checkUpdatePassword);
        findViewById(R.id.updatePasswordButton).setOnClickListener(onClickListener);


    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.updatePasswordButton:
                    updatePassword();
                    break;
            }
        }
    };
    private void updatePassword(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(updatePasswordEdit.length() > 0 && checkUpdatePasswordEdit.length() > 0){
            if(updatePasswordEdit.length() > 5 && checkUpdatePasswordEdit.length() > 5) {
                if (updatePasswordEdit.getText().toString().equals(checkUpdatePasswordEdit.getText().toString())) {
                    newPassword = updatePasswordEdit.getText().toString();

                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        SharedPreferences sharedPreferences = getSharedPreferences("pFile",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("password",newPassword);
                                        editor.apply();
                                        ToastMessage("??????????????? ??????????????? ?????????????????????!");
                                        Intent intent = new Intent(UpdatePassword.this, MainActivity.class);
                                        intent.putExtra("password",newPassword);
                                        startActivity(intent);
                                    }
                                }
                            });
                } else {
                    ToastMessage("????????? ??????????????? ???????????? ????????????!!");
                }
            }else {
                ToastMessage("??????????????? 6?????? ??????????????? ?????????!!");
            }
        }else {
            ToastMessage("????????? ??????????????? ????????? ?????????!!");
        }
    }
    private void ToastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

}
