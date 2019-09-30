package lt.bit.testcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Modify extends AppCompatActivity {


    public void goModify(View view){
        EditText editText = findViewById(R.id.editnumber);
        Intent intent = new Intent(this, EditForm.class);
        String text = editText.getText().toString();
        intent.putExtra("id", Integer.parseInt(text));
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        TextView tw = findViewById(R.id.modify);
        tw.setText("Choose a question to modify between 1 and " + Test.maxQuestions);
    }
}
