package nbsix.com.constructionbank.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nbsix.com.constructionbank.R;

/**
 * Name: ucenterAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-14 18:35
 */

public class ucenterAdapter extends RecyclerView.Adapter<ucenterAdapter.MyViewHolder> {

    Context context;

    public ucenterAdapter(Context c) {
        context=c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.ucenter_item
                        , parent,
                        false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view)
        {
            super(view);
        }
    }
}
