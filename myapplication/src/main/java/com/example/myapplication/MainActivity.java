package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout leftFixedView;
    private Button button;
    private WeakReference<Listener> innerListenerWeak = new WeakReference<>(new Listener() {
        @Override
        void onFinish() {
            Log.d("zjq","jjj");
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizaontal_scroll);

        recyclerView = findViewById(R.id.recyclerView);
        leftFixedView = findViewById(R.id.leftFixedView);
        button = findViewById(R.id.button);

//        innerListenerWeak = new WeakReference<>(listener);
        realSetListener(button, innerListenerWeak);

        // 设置 RecyclerView 布局管理器（水平布局）
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new SimpleAdapter());

//        // 设置横向滚动和吸附效果：确保滑动行为
//        CoordinatorLayout.LayoutParams appBarParams = (CoordinatorLayout.LayoutParams) leftFixedView.getLayoutParams();
//        appBarParams.setBehavior(new HorizontalAppBarBehavior());
//        leftFixedView.setLayoutParams(appBarParams);
//
//        CoordinatorLayout.LayoutParams recyclerParams = (CoordinatorLayout.LayoutParams) recyclerView.getLayoutParams();
//        recyclerParams.setBehavior(new HorizontalRecyclerViewBehavior());
//        recyclerView.setLayoutParams(recyclerParams);
    }

    private void realSetListener(View view, WeakReference<Listener> listener) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Listener listener1 = listener.get();
                if (listener1 != null) listener1.onFinish();
//                System.gc();
                Log.d("zjq", String.valueOf(listener1 == null));
            }
        });
    }



    abstract class Listener {
        Listener listener;
        abstract void onFinish();

        @Override
        protected void finalize() throws Throwable {
            Log.d("zjq", "release");
            super.finalize();
        }
    }

    // 简单的 RecyclerView 适配器
    private class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText("Item " + position);
        }

        @Override
        public int getItemCount() {
            return 20;  // 假设有 20 个项
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            ViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
