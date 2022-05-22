package com.example.organiserempty;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogFragment extends AppCompatDialogFragment {
    OpenHelper openHelper;
    EditText header,desc,time;
    Button button;
    SQLiteDatabase db;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public static DialogFragment newInstance(String param1, String param2) {
        DialogFragment fragment = new DialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DialogFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        header=inflater.inflate(R.layout.fragment_dialog,container,false).findViewById(R.id.header);
        desc=inflater.inflate(R.layout.fragment_dialog,container,false).findViewById(R.id.desc);
        time=inflater.inflate(R.layout.fragment_dialog,container,false).findViewById(R.id.time);
        button=inflater.inflate(R.layout.fragment_dialog,container,false).findViewById(R.id.confirm_button);
        openHelper=new OpenHelper(getContext());



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                values.put(OpenHelper.COLUMN_HEADER,header.getText().toString());
                values.put(OpenHelper.COLUMN_DESCRIPTION,desc.getText().toString());
                values.put(OpenHelper.COLUMN_TIME,time.getText().toString());
                db.insert(OpenHelper.TABLE_NAME,null,values);

                DialogFragment.this.dismiss();
            }
        });


        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onResume() {
        db=openHelper.getWritableDatabase();
        super.onResume();
        Window window=getDialog().getWindow();
        window.setLayout(1000,750);
        window.setGravity(Gravity.CENTER);

    }

    @Override
    public void onStop() {
        db.close();
        super.onStop();
    }
}
