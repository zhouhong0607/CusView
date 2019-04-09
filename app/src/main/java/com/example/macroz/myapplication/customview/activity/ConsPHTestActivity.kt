package com.example.macroz.myapplication.customview.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.constraint.ConstraintsChangedListener
import android.transition.TransitionManager
import com.example.macroz.myapplication.R
import com.example.macroz.myapplication.mainactivity.BaseActivity
import kotlinx.android.synthetic.main.activity_cons_phtest.*

class ConsPHTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cons_phtest)
        var select = false
        ph_cons.setOnClickListener {
            TransitionManager.beginDelayedTransition(ph_cons)
            var id = if (select) R.id.imageView4 else R.id.imageView5
            select = !select
            ph_test.setContentId(id)
        }
        ph_cons.setOnConstraintsChanged(object : ConstraintsChangedListener() {
            override fun preLayoutChange(stateId: Int, constraintId: Int) {
                super.preLayoutChange(stateId, constraintId)
                this@ConsPHTestActivity.toast("preLayoutChange()")
            }

            override fun postLayoutChange(stateId: Int, constraintId: Int) {
                super.postLayoutChange(stateId, constraintId)
                this@ConsPHTestActivity.toast("postLayoutChange()")
            }
        })
    }
}
