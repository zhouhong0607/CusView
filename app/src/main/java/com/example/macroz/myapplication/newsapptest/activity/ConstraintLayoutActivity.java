package com.example.macroz.myapplication.newsapptest.activity;

import android.support.annotation.NonNull;
import android.support.constraint.motion.MotionLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.macroz.myapplication.R;

public class ConstraintLayoutActivity extends AppCompatActivity {

    private static final String TAG = "ConstraintLayoutActivit";

    private MotionLayout mMotionLayout;

    private RecyclerView mRecyclerView;

    private int scrollX;
    private int scrollState;
    private int lastVisibleP;
    private int lastCompleteVisibleP;

    private float progress = 0f;

    private LottieAnimationView lottieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout);
        mMotionLayout = findViewById(R.id.motion_layout);
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 1) {
                    return new LottieVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.motion_recycler_lottie_item, parent, false));
                } else {

                    return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.motion_recycler_item, parent, false));
                }
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                if (position == 19) {
                    lottieView = ((LottieVH) holder).mLottieAnimationView;
                    lottieView.setImageAssetsFolder("images/");
                    lottieView.setAnimation("news_reader_viewmore.json");
                    lottieView.loop(true);
                }
            }

            @Override
            public int getItemViewType(int position) {
                if (position == 19) {
                    return 1;
                } else {
                    return 0;
                }
            }

            @Override
            public int getItemCount() {
                return 20;
            }

            class VH extends RecyclerView.ViewHolder {
                ImageView imgView;
                TextView mTextView;

                public VH(View itemView) {
                    super(itemView);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ConstraintLayoutActivity.this, "click", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            class LottieVH extends RecyclerView.ViewHolder {
                LottieAnimationView mLottieAnimationView;

                public LottieVH(View itemView) {
                    super(itemView);
                    mLottieAnimationView = itemView.findViewById(R.id.motion_lottie_item_view);
                }
            }


        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollState = newState;
                Log.i(TAG, "State : " + newState);

                if (lastCompleteVisibleP == 19 && scrollState == 0) {
                    Log.i(TAG, "跳转");
                    Toast.makeText(ConstraintLayoutActivity.this, "跳转", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollX += dx;
//                Log.d(TAG, "scrollX :" + scrollX);

                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();

                lastVisibleP = lm.findLastVisibleItemPosition();
                lastCompleteVisibleP = lm.findLastCompletelyVisibleItemPosition();

                if (lastVisibleP == 19) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(lastVisibleP);
                    View view = viewHolder.itemView;

                    int[] location = new int[2];
                    recyclerView.getLocationOnScreen(location);
                    progress = (float) (recyclerView.getMeasuredWidth() - view.getLeft()) / (float) view.getMeasuredWidth();
                    if (lottieView != null) {
                        lottieView.setProgress(progress);
                    }
                    Log.i(TAG, "progress" + progress);

                }


//                Log.d(TAG, "lastVisibleP :" + lastVisibleP);
//                Log.d(TAG, "lastCompleteVisibleP :" + lastCompleteVisibleP);


            }
        });

        mMotionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                Log.d(TAG, "onTransitionCompleted（）");
            }
        });


    }
}
