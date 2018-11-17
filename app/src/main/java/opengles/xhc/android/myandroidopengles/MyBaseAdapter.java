package opengles.xhc.android.myandroidopengles;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.List;

public abstract class MyBaseAdapter<T, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    protected List<T> list = new ArrayList<T>();
    protected Context context;
    protected OnRecyleItemClick lis ;

    public interface OnRecyleItemClick<T> {
        void onItemClick(View v , T t , int position);
    }

    public MyBaseAdapter(List<T> list, Context context) {
        this.context = context;
        if (list != null) {
            this.list.addAll(list);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnRecyleItemClick(OnRecyleItemClick lis  ){
        this.lis = lis;
    }

    public void refreshAllData(List<T> list) {
        this.list.clear();
        if (list != null) {
            this.list.addAll(list);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public abstract V onCreateViewHolder(ViewGroup container, int type);

    @Override
    public abstract void onBindViewHolder(V holder, int position);





}