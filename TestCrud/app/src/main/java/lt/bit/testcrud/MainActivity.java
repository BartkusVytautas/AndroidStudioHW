package lt.bit.testcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public void startTest(View v){
        Intent intent = new Intent(this, Test.class);
        startActivity(intent);
    }

    public void startModify(View v){
        Intent intent = new Intent(this, Modify.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Test.getMaxQuestionNumber();


    }


}
