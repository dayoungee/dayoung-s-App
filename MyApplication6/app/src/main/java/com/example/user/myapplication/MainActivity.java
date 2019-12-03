package com.example.user.myapplication;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends TabActivity {

    TabHost mTab; //탭 호스트

    DatePicker datePicker; // 달력
    EditText edttext; // 저장공간
    Button btn; //저장버튼
    Button deletebtn; // 삭제 버튼

    String fileName; // 파일이름
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AlertDialog.Builder(this)
                .setTitle("환영합니다")  // 대화상자
                .setMessage("좋은 하루보내세요 윤다영님^^")
                .setIcon(R.drawable.dayoung)
                .setPositiveButton("닫기", null)
                .show();

        TabHost mTab = getTabHost();

        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.activity_main, mTab.getTabContentView(), true);
//탭바 만들기
        TabHost tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("General")
                .setIndicator("가위바위보")
                .setContent(R.id.opt_general));
        tabHost.addTab(tabHost.newTabSpec("Compiler")
                .setIndicator("달력")
                .setContent(R.id.opt_compiler));
        tabHost.addTab(tabHost.newTabSpec("Linker")
                .setIndicator("뭐할지 고민중")
                .setContent(R.id.opt_linker));

        findViewById(R.id.btn1).setOnClickListener(mClickListener);
        findViewById(R.id.btn2).setOnClickListener(mClickListener);
        findViewById(R.id.btn3).setOnClickListener(mClickListener);
        findViewById(R.id.deletebtn).setOnClickListener(mClickListener);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        edttext = (EditText) findViewById(R.id.edttext);
        btn = (Button) findViewById(R.id.btn);
        deletebtn = (Button) findViewById(R.id.deletebtn);



        Calendar c = Calendar.getInstance();

        // datapicker 을 사용하기 위한 기능
        datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker dataPicker, int year, int month, int day) {

                check(year, month, day);
            }
        });

       btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save(fileName);
            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete(fileName);
                deleteFile(fileName);
                Toast.makeText(MainActivity.this, "삭제 되었습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void delete(String fileName){

        String path ="/data/data/com.example.user.myapplication/files/ ";
        File file = new File ( fileName+".txt");
        if(file.isFile()){
            deleteFile(fileName);
            Toast.makeText(MainActivity.this, "삭제 되었습니다", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(MainActivity.this, "삭제 할 내용이 없습니다.", Toast.LENGTH_SHORT).show();

    }


    // 기록 저장
    // 저장
    public void check(int year, int month, int day) {

        fileName = year + "" + month + "" + day + ".txt";

        try {
            FileInputStream fis = openFileInput(fileName);
            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

           String str = new String(fileData);
            edttext.setText(str);
            btn.setText("수정하기");
        } catch (Exception e) {
            edttext.setText("");
            btn.setText("저장하기");
        }

    }


    // 저장된
    public void save(String fileName) {
        try {
            FileOutputStream fos = openFileOutput(fileName,Context.MODE_APPEND);//기존내용에 덮어쓰여지게
            // 기록한 텍스트를 가져오고 문자형으로 전환
            String content = edttext.getText().toString();
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(MainActivity.this, "저장됨", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "오류", Toast.LENGTH_SHORT).show();
        }
    }

    //가위바위보
    public int game(int player ,int compter){
        int result = 0;
        if(player == compter) result = 0;
        else if (player - compter == 1 || player - compter== -2) result = 1;
        else result = 2;
        return result;
    }

    Button.OnClickListener mClickListener = new Button.OnClickListener(){
        public void onClick(View v){

            int player = 0;
            String s = null;
            int compter = new Random().nextInt(3)+1;
            switch(v.getId())
            {
                case R.id.btn1:
                    player = 1;
                    break;
                case R.id.btn2:
                    player = 2;
                    break;
                case R.id.btn3:
                    player = 3;
                    break;
            }
            int result = game(player,compter);
            switch(result)
            {
                case 0:
                    s="비겼습니다";
                    Toast.makeText(MainActivity.this, "아 비겼네~.", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    s="이겼습니다";
                    Toast.makeText(MainActivity.this, "너가 이겼네 축하해 ,, ~.", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    s="졌습니다";
                    Toast.makeText(MainActivity.this, "ㅋㅋㅋ 너 못한다~", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    s="엥? 오류";
                    break;
            }
            ((TextView)findViewById(R.id.TextView01)).setText(s);

        }
    };
}





