package com.example.prac9;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prac9.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fileName", binding.editTextName.getText().toString());
        outState.putString("fileContents", binding.editTextInfo.getText().toString());
        outState.putString("fileContent", binding.textViewFileContent.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        binding.editTextName.setText(savedInstanceState.getString("fileName"));
        binding.editTextInfo.setText(savedInstanceState.getString("fileContents"));
        binding.textViewFileContent.setText(savedInstanceState.getString("fileContent"));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSave.setOnClickListener(v -> {
            String fileName= binding.editTextName.getText().toString();
            String fileContents = binding.editTextInfo.getText().toString();
            Context context = getApplicationContext();
            try (FileOutputStream fos = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE)) {
                fos.write(fileContents.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        binding.buttonEdit.setOnClickListener(v -> {
            String fileName= binding.editTextName.getText().toString();
            String fileContents = binding.editTextInfo.getText().toString();
            Context context = getApplicationContext();
            try (FileOutputStream fos = context.openFileOutput(fileName,
                    Context.MODE_APPEND)) {
                fos.write(fileContents.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        binding.buttonRead.setOnClickListener(v -> {
            String fileName= binding.editTextName.getText().toString();
            Context context = getApplicationContext();
            try {
                FileInputStream fis = context.openFileInput(fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                binding.textViewFileContent.setText(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        binding.buttonDelete.setOnClickListener(v -> {
            String fileName= binding.editTextName.getText().toString();
            Context context = getApplicationContext();
            context.deleteFile(fileName);
        });
    }
}