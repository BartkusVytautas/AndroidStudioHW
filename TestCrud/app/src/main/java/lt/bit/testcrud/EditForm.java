package lt.bit.testcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class EditForm extends AppCompatActivity {

    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_form);
        Intent intent=getIntent();
        this.id = intent.getIntExtra("id",0);
        loadQuestionById(this.id);

    }

    public void loadQuestionById(Integer id){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams rp=new RequestParams();
        rp.add("id", "" + id);
        client.get("http://poligonas.lt/questions/question.php",
                rp, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            String  q = response.getString("question");
                            String  a1 = response.getString("a1");
                            String  a2 = response.getString("a2");
                            String  a3=response.getString("a3");

                            Integer a = response.getInt("answer");

                            EditText editText = findViewById(R.id.modq);
                            editText.setText(q);

                            editText = findViewById(R.id.moda1);
                            editText.setText(a1);

                            editText = findViewById(R.id.moda2);
                            editText.setText(a2);

                            editText = findViewById(R.id.moda3);
                            editText.setText(a3);

                            editText = findViewById(R.id.modanswer);
                            editText.setText(a+"");

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
    }

    public void updateQuestionById(View view){

        AsyncHttpClient client = new AsyncHttpClient();

        EditText et1 = findViewById(R.id.modq);
        EditText et2 = findViewById(R.id.moda1);
        EditText et3 = findViewById(R.id.moda2);
        EditText et4 = findViewById(R.id.moda3);
        EditText et5 = findViewById(R.id.modanswer);

        String question = et1.getText().toString();
        String a1 = et2.getText().toString();
        String a2 = et3.getText().toString();
        String a3 = et4.getText().toString();
        String a = et5.getText().toString();



        RequestParams rp = new RequestParams();
        rp.add("id", "" + this.id+"");
        rp.add("question", question);
        rp.add("a1", a1);
        rp.add("a2", a2);
        rp.add("a3", a3);
        rp.add("answer", a);
        client.post("http://poligonas.lt/questions/question_update.php",
                rp, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);


                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
    }
}
