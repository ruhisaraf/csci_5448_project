package com.example.ruhi.hello_world;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private TrueFalse[] mQuestionBank =  new TrueFalse[] {
            new TrueFalse(R.string.question_africa, true),
            new TrueFalse(R.string.question_americas, false),
            new TrueFalse(R.string.question_asia, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_oceans, false)
    };

    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private static final String TAG = "Main Activity";
    private static final String KEY_INDEX = "index";
    private static final String ANSWER_SHOWN = "answer_shown";
    public static final String EXTRA_ANSWER = "extra_answer";


    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer( boolean userPressedTrue ) {
        boolean result = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageRId;

        if( mIsCheater ) {
            messageRId = R.string.judgement_toast;
        } else {
            if (userPressedTrue == result) {
                messageRId = R.string.correct_toast;
            } else {
                messageRId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(MainActivity.this,
                messageRId,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return; }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(ANSWER_SHOWN, false);
        }

        mQuestionTextView = (TextView)findViewById(R.id.question_textview);
        mQuestionTextView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = ( mCurrentIndex + 1 ) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }

        });
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = ( mCurrentIndex + 1 ) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mPrevButton = (Button)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = ( mCurrentIndex == 0) ? mQuestionBank.length - 1 :
                                    mCurrentIndex - 1;
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent( MainActivity.this, CheatActivity.class );
                boolean answer = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(EXTRA_ANSWER, answer);
                startActivityForResult(i, 0);
            }
        });
        updateQuestion();

    }

    @Override
    public void onSaveInstanceState (Bundle outstate) {
        super.onSaveInstanceState(outstate);
        outstate.putInt(KEY_INDEX, mCurrentIndex);
        outstate.putBoolean(ANSWER_SHOWN, mIsCheater);
    }

}
