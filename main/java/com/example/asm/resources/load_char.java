package com.example.asm.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class load_char extends ListActivity {

    final private List<String> fileList = new ArrayList<>();
    final private Map<String, Integer> sp_res = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = this;
        File root = new File(context.getFilesDir().getAbsolutePath());
        ListDir(root);

    }

    private void ListDir(File f){
        File[] files = f.listFiles();
        fileList.clear();
        for (File file : files){
            fileList.add(file.getName());
        }

        ArrayAdapter<String> directoryList
                = new ArrayAdapter<>(this, R.layout.activity_load_char, fileList);
        setListAdapter(directoryList);
        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String filename = (String) adapterView.getItemAtPosition(i);
                load_char_file(filename);
            }
        });
    }

    private void load_char_file ( String filename ){
        Context context = this;
        Properties properties = new Properties();
        FileInputStream handler;
        try {
            handler = new FileInputStream( context.getFilesDir().getAbsolutePath() + "/" +  filename);
            properties.load(handler);
            for (String key : properties.stringPropertyNames()) {
                Integer val = Integer.parseInt( properties.get(key).toString() );
                sp_res.put(key, val);
            }
            character char_o = character.getInstance();
            char_o.char_name = filename;
            char_o.set_sp_res( sp_res );

            Intent intent = new Intent(this, main_screen.class);
            startActivity(intent);
        }
        catch ( FileNotFoundException e ) {
            throw new RuntimeException( "File not found" );
        }
        catch ( IOException e ) {
            throw new RuntimeException( "Can't read file" );
        }
    }
}