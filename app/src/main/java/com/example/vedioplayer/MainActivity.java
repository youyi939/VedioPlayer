package com.example.vedioplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button play;
    private Button pause;
    private Button replay;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.video);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        replay = findViewById(R.id.replay);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            initVideoPath();
        }                               //初始化权限



    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initVideoPath();
                }else {
                    Toast.makeText(this,"权限拒绝",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
                default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null){
            videoView.suspend();
        }
    }

    private void initVideoPath(){
        File file = new File(Environment.getExternalStorageDirectory(),"环塔.mp4");
        videoView.setVideoPath(file.getPath());                 //指定视频文件路径
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play:
                if (!videoView.isPlaying()){
                    videoView.start();                          //开始播放
                }
                break;
            case R.id.pause:
                if (videoView.isPlaying()){
                    videoView.pause();                          //暂停播放
                }
                break;
            case R.id.replay:
                if (videoView.isPlaying()){
                    videoView.resume();                         //重新播放
                }
                break;
        }
    }
}
