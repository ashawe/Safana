package com.ashstudios.safana.activities;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ashstudios.safana.R;

public class LoginActivity extends AppCompatActivity {
    private EditText editText,editText1;
    private Context context;
    private Boolean isSwipe = false;
    private ConstraintLayout constraintLayout;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initialize();
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if((motionEvent.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[2].getBounds().width() - 20)) && editText.getText().length() != 10) {
                        isSwipe = true;
                        final AnimatorSet as = new AnimatorSet();
                        ObjectAnimator animator1 = ObjectAnimator.ofFloat(findViewById(R.id.et_emp_id), "translationX", - constraintLayout.getWidth());
                        ObjectAnimator animator2 = ObjectAnimator.ofFloat(findViewById(R.id.et_pwd), "translationX", 0);
                        animator1.setDuration(500);
                        animator2.setDuration(500);
                        as.play(animator1);
                        as.play(animator2);
                        as.start();
                        return true;
                    }
                }
                return false;
            }
        });

        editText1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if((motionEvent.getRawX() >= (editText1.getRight() - editText1.getCompoundDrawables()[2].getBounds().width() - 20)) && editText1.getText().length() != 10) {
                        Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(!isSwipe)
            super.onBackPressed();
        else {
            isSwipe = false;
            final AnimatorSet as = new AnimatorSet();
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(findViewById(R.id.et_pwd), "translationX", constraintLayout.getWidth());
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(findViewById(R.id.et_emp_id), "translationX", 0);
            animator1.setDuration(500);
            animator2.setDuration(500);
            as.play(animator1);
            as.play(animator2);
            as.start();
        }
    }

    private void initialize() {
        context = LoginActivity.this;
        editText = findViewById(R.id.et_emp_id);
        editText1 = findViewById(R.id.et_pwd);
        constraintLayout = findViewById(R.id.constraint_layout);
    }
}
