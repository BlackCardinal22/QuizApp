package com.example.quizapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.TintableCompoundDrawablesView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapplication.databinding.ActivityMainBinding
import com.example.quizapplication.databinding.ActivityQuizQuestionsActtivityBinding


class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {


    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName: String? = null
    private lateinit var binding: ActivityQuizQuestionsActtivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsActtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList = Constants.getQuestions()

        setQuestion()
        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)


    }

    private fun setQuestion() = with(binding) {


        val question = mQuestionsList!![mCurrentPosition - 1]


        defaultOptionsView()

        if(mCurrentPosition == mQuestionsList!!.size){
            btnSubmit.text = "FINISH"
        }else{
            btnSubmit.text = "SUBMIT"
        }

        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = "$mCurrentPosition/${binding.progressBar.max}"

        tvQuestion.text = question.question
        ivImage.setImageResource(question.image)
        tvOptionOne.text = question.optionOne
        tvOptionTwo.text = question.optionTwo
        tvOptionThree.text = question.optionThree
        tvOptionFour.text = question.optionFour
    }

    private fun defaultOptionsView() = with(binding) {
        val options = ArrayList<TextView>()
        options.add(0, tvOptionOne)
        options.add(1, tvOptionTwo)
        options.add(2, tvOptionThree)
        options.add(3, tvOptionFour)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this@QuizQuestionsActivity,
                R.drawable.default_option_border_bg
            )
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one -> {
                selectedOptionView(binding.tvOptionOne, selectedOptionNumber = 1)
            }
            R.id.tv_option_two -> {
                selectedOptionView(binding.tvOptionTwo, selectedOptionNumber = 2)
            }
            R.id.tv_option_three -> {
                selectedOptionView(binding.tvOptionThree, selectedOptionNumber = 3)
            }
            R.id.tv_option_four -> {
                selectedOptionView(binding.tvOptionFour, selectedOptionNumber = 4)
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        binding.btnSubmit.text = "FINISH"


                    } else {
                        binding.btnSubmit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0


                }
            }
        }

    }


        private fun answerView(answer: Int, drawableView: Int) = with(binding) {
            when (answer) {
                1 -> {
                    tvOptionOne.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity, drawableView
                    )
                }
                2 -> {
                    tvOptionTwo.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity, drawableView
                    )
                }
                3 -> {
                    tvOptionThree.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity, drawableView
                    )
                }
                4 -> {
                    tvOptionFour.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity, drawableView
                    )
                }
            }

        }

        private fun selectedOptionView(
            tv: TextView,
            selectedOptionNumber: Int
        ) {
            defaultOptionsView()
            mSelectedOptionPosition = selectedOptionNumber

            tv.setTextColor(Color.parseColor("#363A43"))
            tv.setTypeface(tv.typeface, Typeface.BOLD)
            tv.background = ContextCompat.getDrawable(
                this@QuizQuestionsActivity,
                R.drawable.selected_option_border_bg
            )

        }
    }




