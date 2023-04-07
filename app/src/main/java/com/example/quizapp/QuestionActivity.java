package com.example.quizapp;

import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.ArrayList;




public class QuestionActivity extends AppCompatActivity {

    private TextView questionText;
    private RadioGroup answerChoices;
    private Button submitButton;

    private int currentQuestionIndex = 0;
    private int score = 0;

    private List<Question> questions = new ArrayList<Question>() {{
        add(new Question("Which of the following superheroes is not a member of the Avengers?", "Spider-Man", "Captain America", "Thor", "Wolverine", "Wolverine"));
        add(new Question("Which Marvel superhero is also known as the \"Sorcerer Supreme\"?", "Doctor Strange", "Iron Man", "Captain Marvel", "Spider-Man", "Doctor Strange"));
        add(new Question("What is the name of the villain in the film \"Captain America: The Winter Soldier\"?", "Red Skull", "Baron Zemo", "The Winter Soldier", "Crossbones", "The Winter Soldier"));
        add(new Question("What is the name of the fictional African country ruled by T'Challa/Black Panther?", "Wakanda", "Sokovia", "Latveria", "Genosha", "Wakanda"));
        add(new Question("Which of the following actors has not played the role of Spider-Man in a live-action film?", "Andrew Garfield", "Tobey Maguire", "Tom Holland", "Jake Gyllenhaal", "Jake Gyllenhaal"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionText = findViewById(R.id.question_text);
        answerChoices = findViewById(R.id.answer_choices);
        submitButton = findViewById(R.id.submit_button);

        showQuestion();
    }

    private void showQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);

            questionText.setText(question.getQuestion());
            RadioButton answer1 = findViewById(R.id.answer_1);
            answer1.setText(question.getAnswer1());
            RadioButton answer2 = findViewById(R.id.answer_2);
            answer2.setText(question.getAnswer2());
            RadioButton answer3 = findViewById(R.id.answer_3);
            answer3.setText(question.getAnswer3());
            RadioButton answer4 = findViewById(R.id.answer_4);
            answer4.setText(question.getAnswer4());

            answerChoices.clearCheck();
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAnswer(question);
                }
            });
            answerChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId != -1) {
                        submitButton.setEnabled(true);
                    } else {
                        submitButton.setEnabled(false);
                    }
                }
            });
            submitButton.setEnabled(false);
        } else {
            endQuiz();
        }
    }


    private void checkAnswer(Question question) {
        int selectedId = answerChoices.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedAnswer = findViewById(selectedId);
            if (selectedAnswer.getText().equals(question.getCorrectAnswer())) {
                score++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wrong. The correct answer is: " + question.getCorrectAnswer(), Toast.LENGTH_LONG).show();
            }
            currentQuestionIndex++;
            showQuestion();
        } else {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
        }
    }


    private void endQuiz() {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("total", questions.size());
        startActivity(intent);
        finish();
    }
}
