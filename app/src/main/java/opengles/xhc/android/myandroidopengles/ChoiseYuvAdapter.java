package opengles.xhc.android.myandroidopengles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ChoiseYuvAdapter extends MyBaseAdapter<String ,ChoiseYuvAdapter.MyViewHolder > {


    public ChoiseYuvAdapter(List<String> list, Context context) {
        super(list, context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup container, int type) {
        return new MyViewHolder(LayoutInflater.from(context ).inflate( R.layout.choise_yuv_item , container , false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvName.setText(list.get(position));
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lis != null){
                    lis.onItemClick(view , list.get(position) , position);
                }
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        public MyViewHolder( View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_yuv_name);
        }
    }


}
