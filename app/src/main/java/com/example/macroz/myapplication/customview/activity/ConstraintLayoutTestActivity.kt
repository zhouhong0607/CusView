package com.example.macroz.myapplication.customview.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.constraint.*
import android.support.v7.widget.CardView
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.macroz.myapplication.R
import com.example.macroz.myapplication.mainactivity.BaseActivity

class ConstraintLayoutTestActivity : BaseActivity() {

    lateinit var cons: ConstraintLayout
    var old = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraint_set_test_1)

        cons = findViewById(R.id.constraint_set_layout)
        val set1 = ConstraintSet();
        val set2 = ConstraintSet();
        set1.clone(cons);
        set2.clone(this@ConstraintLayoutTestActivity, R.layout.constraint_set_test_2);


        cons.setOnConstraintsChanged(object : ConstraintsChangedListener() {
            override fun preLayoutChange(stateId: Int, constraintId: Int) {
                super.preLayoutChange(stateId, constraintId)
                Log.d(tag, "preLayoutChange   stateId:$stateId  , constraintId: $constraintId")
            }

            override fun postLayoutChange(stateId: Int, constraintId: Int) {
                super.postLayoutChange(stateId, constraintId)
                Log.d(tag, "postLayoutChange   stateId: $stateId , constraintId: $constraintId")
            }
        })


        cons.setOnClickListener {
            TransitionManager.beginDelayedTransition(cons)
            if (old) {
                set1.applyTo(cons)
            } else {
                set2.applyTo(cons);
            }
            old = !old

        }

        val button = findViewById<Button>(R.id.button10)
        val pro = ConstraintProperties(button)

        findViewById<Button>(R.id.button7).setOnClickListener {
            pro.removeFromHorizontalChain()
        }

    }


}
