package ae.ac.adu.omar.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class Quiz extends AppCompatActivity {
    public static final String TOTAL_SCORE = "total score";
    private TextView question, questionsremaining, scoreTxt;
    private RadioGroup radio_group;
    private RadioButton choice1,choice2,choice3;
    private Button confirm_button;

    private List<Questions> questionList;

    private int questionCounter = 0;
    private int questionCountTotal;
    private Questions currentQuestion;
    private int score;
    private boolean answered;

    private ColorStateList radioButtonColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        final MediaPlayer start = MediaPlayer.create(this, R.raw.letsplay);
        start.start();
        question = findViewById(R.id.question);
        scoreTxt = findViewById(R.id.score);
        questionsremaining = findViewById(R.id.questionsremaining);
        radio_group = findViewById(R.id.radio_group);
        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);
        confirm_button = findViewById(R.id.confirm_button);
        radioButtonColor = choice1.getTextColors();

        SQLDatabaseHelper dbhelper = new SQLDatabaseHelper(this);

        questionList = dbhelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showQuestions();
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answered){
                    if(choice1.isChecked() || choice2.isChecked() || choice3.isChecked()){
                        checkUserSelection();
                    }else{
                        Toast.makeText(Quiz.this,"Please select an answer before moving to next question",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showQuestions();
                }
            }
        });
    }


    private void checkUserSelection(){
        answered = true;
        RadioButton selectAnswer = findViewById(radio_group.getCheckedRadioButtonId());
        final MediaPlayer wrong = MediaPlayer.create(this, R.raw.wronganswer);
        int useranswer = radio_group.indexOfChild(selectAnswer)+1;
        final MediaPlayer correct = MediaPlayer.create(this, R.raw.correctanswer);
        if (useranswer == currentQuestion.getAnswerNumber()){
            correct.start();
            score++;
            scoreTxt.setText("Score: " + score);
        }else{
            wrong.start();
        }
        showSolution();
    }

    private void showSolution(){
        choice1.setTextColor(Color.RED);
        choice2.setTextColor(Color.RED);
        choice3.setTextColor(Color.RED);
        switch(currentQuestion.getAnswerNumber()){
            case 1:
                choice1.setTextColor(Color.GREEN);
                break;
            case 2:
                choice2.setTextColor(Color.GREEN);
                break;
            case 3:
                choice3.setTextColor(Color.GREEN);
                break;
        }
        setButtonText();

    }

    private void setButtonText(){
        questionsremaining.setText("Questions Remaining " + (questionCountTotal-questionCounter));
        if(questionCounter < questionCountTotal){
            confirm_button.setText("Next");
        }else{
            confirm_button.setText("Finish");
        }
    }

    private void showQuestions(){
        choice1.setTextColor(radioButtonColor);
        choice2.setTextColor(radioButtonColor);
        choice3.setTextColor(radioButtonColor);

        radio_group.clearCheck();

        if (questionCounter < questionCountTotal){
            currentQuestion = questionList.get(questionCounter);
            question.setText(currentQuestion.getQuestion());
            choice1.setText(currentQuestion.getOption1());
            choice2.setText(currentQuestion.getOption2());
            choice3.setText(currentQuestion.getOption3());


            questionsremaining.setText("Questions Remaining " + (questionCountTotal-questionCounter));
            questionCounter++;
            answered = false;
        }else{
            finishQuiz();
        }
    }
    private void finishQuiz(){

        Intent returnedResults = new Intent();
        returnedResults.putExtra(TOTAL_SCORE, score);
        setResult(RESULT_OK,returnedResults);
        this.finish();
    }
}