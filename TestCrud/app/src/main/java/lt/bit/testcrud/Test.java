package lt.bit.testcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Test extends AppCompatActivity {

    private Integer questionNo=0;
    private Integer correct=0;
    private Integer a=0;
    public static Integer maxQuestions;
    private List<Integer> answeredQuestions = new LinkedList<>();


    public void setA(Integer a){
        this.a=a;
    }




    public Integer getRandomNumber(){

        Integer randomQuestionId = Math.round((int)(Math.random() * (maxQuestions - 1) + 1 ));
        if(!this.answeredQuestions.isEmpty()) {
            for (Integer i : answeredQuestions) {
                if (randomQuestionId == i) {
                     return getRandomNumber();
                }
            }
        }
        return randomQuestionId;
    }

    public static void getMaxQuestionNumber(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://poligonas.lt/questions/stats.php", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                try {
                      maxQuestions = response.getInt("total");

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }



    public void loadQuestion(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams rp=new RequestParams();
        Integer randomId = getRandomNumber();
        Log.d("Id", randomId+"");
        this.answeredQuestions.add(randomId);
        rp.add("id", "" + randomId);
        client.get("http://poligonas.lt/questions/question.php",
                rp, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("Testas", response.toString() );
                        try {
                            String  q=response.getString("question");
                            String  a1=response.getString("a1");
                            String  a2=response.getString("a2");
                            String  a3=response.getString("a3");

                            Integer a=response.getInt("answer");
                            setA(a);

                            ((TextView) findViewById(R.id.question)).setText(q);
                            ((RadioButton) findViewById(R.id.answer1)).setText(a1);
                            ((RadioButton) findViewById(R.id.answer2)).setText(a2);
                            ((RadioButton) findViewById(R.id.answer3)).setText(a3);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
    }

    public void submitAnswer(View v){

        RadioGroup rg=findViewById(R.id.radioGroup1);
        if (this.a == 1 && R.id.answer1 == rg.getCheckedRadioButtonId()){
            correct++;
        }
        if (this.a == 2&& R.id.answer2 == rg.getCheckedRadioButtonId()){
            correct++;
        }
        if (this.a == 3&& R.id.answer3 == rg.getCheckedRadioButtonId()){
            correct++;
        }

        rg.clearCheck();

        if (this.questionNo == 10){
            Intent intent= new Intent(this, Result.class);
            intent.putExtra("correct", correct);
            startActivity(intent);
            finish();

        }else {
            ((ProgressBar) findViewById(R.id.progressBar)).setProgress(questionNo);
            this.questionNo++;
            this.loadQuestion();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        this.questionNo=1;
        this.correct=0;
        loadQuestion();

    }
}
