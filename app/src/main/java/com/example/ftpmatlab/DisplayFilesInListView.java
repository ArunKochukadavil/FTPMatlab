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
        fileViewer=findViewById(R.id.fileViewer);
        final Intent intent=getIntent();
        final String hostname=intent.getStringExtra("HostName");
        final String userName=intent.getStringExtra("UserName");
        final String password=intent.getStringExtra("Password");
        fileList=new ArrayList<>();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,R.layout.activity_listview,fileList);
        fileViewer.setAdapter(arrayAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    mFTPClient=new FTPClient();
                    //connects to FTPServer
                   mFTPClient.connect(intent.getStringExtra("HostName"));
                   //Authentication part
                   final boolean status=mFTPClient.login(intent.getStringExtra("UserName"),intent.getStringExtra("Password"));
                   mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                   //FileZilla uses passive mode of communication
                   mFTPClient.enterLocalPassiveMode();
                   //getting the list of files from FTPServer
                   final FTPFile[] ftpFiles=mFTPClient.listFiles();
                    //main thread is also known as UI Thread
                    //we cannot update UI other than Main Thread
                    //so post method will do the job of giving the changes to UI thread i.e Main Thread
                    //main thread is responsible for handling all the events
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
