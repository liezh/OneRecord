package com.liezh.onerecord.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.liezh.onerecord.R;
import com.liezh.onerecord.databinding.ActivityMainBinding;
import com.liezh.onerecord.domian.vo.Note;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private NoteItemAdapter mItemAdapter;

    private ArrayList<Note> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mItems = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Note u = new Note();
            u.setId(i);
            u.setTitle("zhangphil @" + i);
            u.setDesc("blog.csdn.net/zhangphil @" + i);

            mItems.add(u);
            i++;
        }

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mItemAdapter = new NoteItemAdapter(this, mItems);
        mItemAdapter.setmListener((note) -> {
            Toast.makeText(this, note.getTitle(), Toast.LENGTH_SHORT).show();
        });
        mRecyclerView.setAdapter(mItemAdapter);
    }
}
