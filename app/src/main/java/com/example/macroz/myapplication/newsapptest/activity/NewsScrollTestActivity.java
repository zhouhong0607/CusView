package com.example.macroz.myapplication.newsapptest.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.macroz.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class NewsScrollTestActivity extends AppCompatActivity {

    private static final String TAG = "NewsScrollTestActivity";

    private LottieAnimationView lottieView;

    private List<Object> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.news_base_newslist_item_read_horizontal_motifs);
        RecyclerView recyclerView = findViewById(R.id.biz_recommend_check_more_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter() {

            private static final int ITEM_TYPE_SUBJECT = 0;
            private static final int ITEM_TYPE_CHECK_MORE = 1;


            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == ITEM_TYPE_CHECK_MORE) {
                    return new LottieVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.motion_recycler_lottie_item, parent, false));
                } else {

                    return new SubjectVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.motion_recycler_item, parent, false));
                }
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                if (getItemViewType(position) == ITEM_TYPE_CHECK_MORE) {
                    lottieView = ((LottieVH) holder).mLottieAnimationView;
                    lottieView.setImageAssetsFolder("images/");
                    lottieView.setAnimation("news_reader_viewmore.json");
                    lottieView.loop(true);
                }
            }

            @Override
            public int getItemViewType(int position) {
                //todo zh 修改这里
                if (getItemCount() - 1 == position) {
                    return ITEM_TYPE_CHECK_MORE;
                } else {
                    return ITEM_TYPE_SUBJECT;
                }
            }

            @Override
            public int getItemCount() {
                return mDataList.size();
            }

            class SubjectVH extends RecyclerView.ViewHolder {
                ImageView imgView;
                TextView mTextView;

                public SubjectVH(View itemView) {
                    super(itemView);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(NewsScrollTestActivity.this, "click", Toast.LENGTH_SHORT).show();
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


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (lm.findLastCompletelyVisibleItemPosition() == recyclerView.getAdapter().getItemCount() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Toast.makeText(NewsScrollTestActivity.this, "跳转", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                //监控最后一个View漏出的情况，计算progress 控制动画显示
                if (lm.findLastVisibleItemPosition() == recyclerView.getAdapter().getItemCount() - 1) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(lm.findLastVisibleItemPosition());
                    View view = viewHolder.itemView;
                    float progress = (float) (recyclerView.getMeasuredWidth() - view.getLeft()) / (float) view.getMeasuredWidth();
                    if (lottieView != null) {
                        lottieView.setProgress(progress);
                    }
                    Log.i(TAG, "progress" + progress);
                }
            }
        });

    }

    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDataList.add(new Object());
        }
    }

}
