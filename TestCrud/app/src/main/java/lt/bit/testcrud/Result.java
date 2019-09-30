package lt.bit.testcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent=getIntent();
        Integer correct=intent.getIntExtra("correct",0);
        ((TextView) findViewById(R.id.result)).setText(correct + "");
    }


    public void goHome(View view){
        finish();
    }

}
