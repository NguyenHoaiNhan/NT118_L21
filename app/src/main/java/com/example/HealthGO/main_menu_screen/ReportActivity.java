package com.example.HealthGO.main_menu_screen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.HealthGO.R;

public class ReportActivity extends AppCompatActivity {
    private EditText etTo, etSubject, etMessage;
    private Button btnSend;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_error);
        getSupportActionBar().hide();

        etMessage = (EditText)findViewById(R.id.et_message);
        btnSend = (Button)findViewById(R.id.btn_sendMail);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = "nhan.nguyenhoai9648@gmail.com";
                String sub = "Phản hồi người dùng";
                String mess = etMessage.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                intent.putExtra(Intent.EXTRA_SUBJECT, sub);
                intent.putExtra(Intent.EXTRA_TEXT, mess);

                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Choose an email client"));
            }
        });
    }
}
