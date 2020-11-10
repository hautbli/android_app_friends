package com.example.myfeed;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    // 사진 올릴 때 나오는 갤러리
    // 리사이클러뷰 사용

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ActionBar ab = getSupportActionBar();
        ab.hide();

            final int numberOfColumns = 3;

            // use a linear layout manager

            recyclerView = findViewById(R.id.gallery_recyclerview);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
            mAdapter = new GalleryAdapter(this, getImagesPath(this));
            recyclerView.setAdapter(mAdapter);
        }
        // ...

        public static ArrayList<String> getImagesPath(Activity activity) {
            Uri uri;
            ArrayList<String> listOfAllImages = new ArrayList<String>();

            Cursor cursor;

            int column_index_data, column_index_folder_name;
            String PathOfImage = null;
            uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] projection = { MediaStore.MediaColumns.DATA,  // 메모리에 있는 정보를 제공한다
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

            cursor = activity.getContentResolver().query(uri, projection, null,
                    null, null);

            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA); // 데이터에 해당하는 컬럼 인덱스를 얻는다
            column_index_folder_name = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                PathOfImage = cursor.getString(column_index_data);

                listOfAllImages.add(PathOfImage);
            }
            return listOfAllImages;
        }
    }

