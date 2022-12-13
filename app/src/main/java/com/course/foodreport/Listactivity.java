package com.course.foodreport;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Listactivity extends AppCompatActivity {

    EditText editText, editText2;
    ListView listView, listView2;

    DBHelper dbHelper;
    SQLiteDatabase db = null;
    Cursor cursor;
    ArrayAdapter adapter, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listactivity);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        listView = findViewById(R.id.listView);
        listView2 = findViewById(R.id.listView2);

        dbHelper = new DBHelper(this, 3);
        db = dbHelper.getWritableDatabase();
    }

    public void insert(View v) {
        String name = editText.getText().toString();
        String info = editText2.getText().toString();
        db.execSQL("INSERT INTO tableName VALUES ('" + name + "', '" + info + "');");

        Toast.makeText(getApplicationContext(), "추가 성공", Toast.LENGTH_SHORT).show();

        editText.setText("");
        editText2.setText("");

        cursor = db.rawQuery("SELECT * FROM tableName", null);
        startManagingCursor(cursor);    //엑티비티의 생명주기와 커서의 생명주기를 같게 한다.

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);
        adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);

        while (cursor.moveToNext()) {
            adapter.add(cursor.getString(0));
            adapter2.add(cursor.getString(1));
        }
        listView.setAdapter(adapter);
        listView2.setAdapter(adapter2);
    }

    public void delete(View v) {
        String name = editText.getText().toString();
        db.execSQL("DELETE FROM tableName WHERE name = '" + name + "';");
    }

}
