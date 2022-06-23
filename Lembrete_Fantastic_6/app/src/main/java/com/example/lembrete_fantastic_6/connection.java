package com.example.lembrete_fantastic_6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class connection extends SQLiteOpenHelper {
    public static final String CREATE_NOTA = "create table nota ("
            + "id integer primary key autoincrement, "
            + "titulo text, "
            + "descricao text, "
            + "data text, "
            + "time text)";
    private Context mContext;
    public connection(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTA);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
