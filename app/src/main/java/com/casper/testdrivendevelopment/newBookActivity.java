package com.casper.testdrivendevelopment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//Activity之间传递数据
public class newBookActivity extends AppCompatActivity {

    private Button buttonOK,buttonCancle;
    private EditText editTextBookTitle,editTextBookPrice;
    private int insertPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        buttonOK = (Button) findViewById(R.id.button_ok);
        buttonCancle = (Button) findViewById(R.id.button_cancle);
        editTextBookTitle = (EditText) findViewById(R.id.edit_text_book_title);

        editTextBookTitle.setText(getIntent().getStringExtra("title"));
        insertPosition=getIntent().getIntExtra("insert_position",0);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("title",editTextBookTitle.getText().toString());
                intent.putExtra("insert_position",insertPosition);
                setResult(RESULT_OK,intent);
                newBookActivity.this.finish();
            }
        });
        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newBookActivity.this.finish();
            }
        });



    }
    /*public void onBackPressed(){
        Intent intent=new Intent();
        intent.putExtra("title",editTextBookTitle.getText().toString());
        intent.putExtra("insert_position",insertPosition);
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }*/
}
