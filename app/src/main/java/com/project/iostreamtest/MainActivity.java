package com.project.iostreamtest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

  private TextView textView;

  private EditText editText;

  String inputText = null;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    editText = findViewById(R.id.data_path);
    textView = findViewById(R.id.text_container);

    Button getData = findViewById(R.id.data_load);
    getData.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (editText.getText() != null) {
          inputText = load(editText.getText().toString());
          textView.setText(inputText);
        }
      }
    });

    Button transportData = findViewById(R.id.data_transport);
    transportData.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!TextUtils.isEmpty(inputText)) {
          save(inputText);
        }
      }
    });
  }

  //读取文件
  public String load(String path) {
    if (path == null) {
      Toast.makeText(this, "请输入路径", Toast.LENGTH_LONG).show();
      return null;
    }

    if (!isFileExist(path)) {
      Toast.makeText(this, "路径不存在", Toast.LENGTH_LONG).show();
      return null;
    }

    FileReader fileReader = null;
    BufferedReader reader = null;
    StringBuilder content = new StringBuilder();
    try {
      fileReader = new FileReader(path);
      reader = new BufferedReader(fileReader);
      String line;
      while ((line = reader.readLine()) != null) {
        content.append(line).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
        if (fileReader != null) {
          fileReader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return content.toString();
  }

  //把读取到的内容保存在私有目录
  public void save(String inputText) {

    if (inputText == null) {
      return;
    }

    FileOutputStream out;
    BufferedWriter writer = null;

    try {
      out = openFileOutput("new.txt", Context.MODE_PRIVATE);
      writer = new BufferedWriter(new OutputStreamWriter(out));
      writer.write(inputText);
      Toast.makeText(this, "写入成功", Toast.LENGTH_LONG).show();
    } catch (IOException e) {
      Toast.makeText(this, "写入失败", Toast.LENGTH_LONG).show();
      e.printStackTrace();
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean isFileExist(String path) {
    File file = new File(path);
    return file.exists();
  }
}
