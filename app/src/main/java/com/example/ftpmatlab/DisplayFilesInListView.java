package com.example.ftpmatlab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.util.ArrayList;

public class DisplayFilesInListView extends AppCompatActivity {
    ListView fileViewer;
    FTPClient mFTPClient=null;
    ArrayList<String> fileList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayfilesinlistview_activity);
        fileViewer=(ListView)findViewById(R.id.fileViewer);
        final Intent intent=getIntent();
        final String hostname=intent.getStringExtra("HostName");
        final String userName=intent.getStringExtra("UserName");
        final String password=intent.getStringExtra("Password");
        fileList=new ArrayList<String>();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.activity_listview,fileList);
        fileViewer.setAdapter(arrayAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    mFTPClient=new FTPClient();
                   mFTPClient.connect(intent.getStringExtra("HostName"));
                   final boolean status=mFTPClient.login(intent.getStringExtra("UserName"),intent.getStringExtra("Password"));
                   mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                   mFTPClient.enterLocalPassiveMode();
                   final FTPFile[] ftpFiles=mFTPClient.listFiles();
                    //o.setText("1111");
                    //hrow new Exception();
                    //Handler handler=new Handler();

                    fileViewer.post(new Runnable() {
                        @Override
                        public void run() {
                            fileList.add(hostname+userName+password+status+ftpFiles.length);
                            for (FTPFile fileName: ftpFiles
                                 ) {
                                fileList.add(fileName.getName());
                            }
                        }
                    });
                }
                catch (final Exception e)
                {
                    fileViewer.post(new Runnable() {
                        @Override
                        public void run() { fileList.add(e.toString()+"jnjnkjn");
                        }
                    });
                }
            }
        }).start();
    }
}
