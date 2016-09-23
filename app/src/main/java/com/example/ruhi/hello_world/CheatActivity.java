package com.example.ruhi.hello_world;

import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private boolean answer;
    private TextView mAnswerTextView;
    private Button mShowAnswer;

    public static final String EXTRA_ANSWER_SHOWN = "answer_shown";
    private boolean answerShown = false;

    private void setAnswerShownResult ( boolean isAnswerShown ) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult( RESULT_OK, data );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if(savedInstanceState != null) {
            answerShown = savedInstanceState.getBoolean(EXTRA_ANSWER_SHOWN, false);
        }

        answer = getIntent().getBooleanExtra(MainActivity.EXTRA_ANSWER, false);
        setAnswerShownResult(answerShown);

        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( answer ) {
                    mShowAnswer.setText(R.string.true_button);
                } else {
                    mShowAnswer.setText(R.string.false_button);
                }
                answerShown = true;
                setAnswerShownResult( true );
            }
        });
    }

    @Override
    public void onSaveInstanceState (Bundle outstate) {
        super.onSaveInstanceState(outstate);
        outstate.putBoolean(EXTRA_ANSWER_SHOWN, answerShown);
    }
}
