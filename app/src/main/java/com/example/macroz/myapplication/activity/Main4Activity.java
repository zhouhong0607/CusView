package com.example.macroz.myapplication.activity;

import android.support.annotation.NonNull;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.macroz.myapplication.R;
import com.example.macroz.myapplication.Utils;
import com.example.macroz.myapplication.customview.view.MyFoldLayout;
import com.example.macroz.myapplication.customview.view.PolygonView;

public class Main4Activity extends AppCompatActivity {

    private final String TAG="Main4Activity";

    private ViewGroup container;

    private PolygonView mPolygonView;
    private RadioGroup radioGroup;

    private ImageView mImageView;
    private MyFoldLayout myFoldLayout;

    private SlidingPaneLayout mSlidingPaneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
//        startActivity(new Intent(Main4Activity.this,Main5Activity.class));
        initView();

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                float factor=myFoldLayout.getFactor();
//                switch (checkedId){
//                    case R.id.control1:
////                        myView.select(1);
//                        factor=0.8f;
//                        break;
//                    case R.id.control2:
////                        myView.select(2);
//                        factor=0.6f;
//                        break;
//                    case R.id.control3:
////                        myView.select(3);
//                        factor=0.4f;
//                        break;
//                    case R.id.control4:
////                        myView.select(4);
//                        factor=0.1f;
//                        break;
//                }
//                myFoldLayout.setFactor(factor);
//            }
//        });

         setBitmap();



         mSlidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
             @Override
             public void onPanelSlide(@NonNull View panel, float slideOffset) {
                 Log.d(TAG,"slideOffset: " + slideOffset);
                myFoldLayout.setFactor(slideOffset);
             }

             @Override
             public void onPanelOpened(@NonNull View panel) {
                 Log.d(TAG,"onPanelOpened: " );
             }

             @Override
             public void onPanelClosed(@NonNull View panel) {
                 Log.d(TAG,"onPanelClosed: " );
             }
         });

    }

    private void initView(){
//        radioGroup=(RadioGroup)findViewById(R.id.radio_group);
        myFoldLayout=(MyFoldLayout)findViewById(R.id.fold_layout);
        mImageView=(ImageView)findViewById(R.id.image_view);
        //        myView=(MyView) findViewById(R.id.myview);
        mSlidingPaneLayout=(SlidingPaneLayout)findViewById(R.id.sliding_layout);
    }

    private void setBitmap(){
        mImageView.setImageBitmap(Utils.getBitmap(getResources(),R.drawable.xiaoxiao));

    }


}
