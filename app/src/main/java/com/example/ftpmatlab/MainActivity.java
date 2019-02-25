package com.example.ftpmatlab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText host,pass,usr;
    TextView o;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        host=findViewById(R.id.hst);
        usr=findViewById(R.id.usr);
        pass=findViewById(R.id.pass);
        ok=findViewById(R.id.k);
        o=findViewById(R.id.out);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    //creating an intent to move on to another activity
                    Intent intent=new Intent(v.getContext(),DisplayFilesInListView.class);
                    //Storing all credentials in intent so this information is also accessible
                    //in other intent also
                    intent.putExtra("HostName",host.getText().toString());
                    intent.putExtra("UserName",usr.getText().toString());
                    intent.putExtra("Password",pass.getText().toString());
                    startActivity(intent);
                }
                catch(Exception e)
                {
                    o.setText(e.toString());
                }
            }
        });
    }
}
