package com.example.macroz.myapplication.customview.activity

import android.os.Bundle
import android.support.constraint.ConstraintsChangedListener
import android.util.Log
import com.example.macroz.myapplication.R
import com.example.macroz.myapplication.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_cons_state_test.*

class ConsStateTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cons_state_test)
        cons_state_test.loadLayoutDescription(R.xml.cons_state_switch)
        state_button.setOnClickListener {
            cons_state_test.setState(R.id.loading, 1080, 1920)
            it.postDelayed(object : Runnable {
                override fun run() {
                    cons_state_test.setState(R.id.end, 0, 0)
                    state_button.setText("end")
                }
            }, 5000)
        }


        cons_state_test.setOnConstraintsChanged(object : ConstraintsChangedListener() {

            override fun preLayoutChange(stateId: Int, constraintId: Int) {
                super.preLayoutChange(stateId, constraintId)
                Log.d(tag,"preLayoutChange ,state $stateId , constraintId $constraintId ")

            }

            override fun postLayoutChange(stateId: Int, constraintId: Int) {
                super.postLayoutChange(stateId, constraintId)
                Log.d(tag,"postLayoutChange state $stateId , constraintId $constraintId ")
            }
        })
    }
}
