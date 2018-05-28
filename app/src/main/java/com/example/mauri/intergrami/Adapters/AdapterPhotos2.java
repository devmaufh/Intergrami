package com.example.mauri.intergrami.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mauri.intergrami.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPhotos2 extends RecyclerView.Adapter<AdapterPhotos2.ViewHolder>{
    List<String> urls;
    private Context context;
    private int layout;
    private LongClickListener longClickListener;
    private OnClickListener onClickListener;
    public AdapterPhotos2(List<String> urls, int layout, LongClickListener longClickListener,OnClickListener listener) {
        this.urls = urls;
        this.layout = layout;
        this.longClickListener = longClickListener;
        this.onClickListener=listener;
    }
    public void getItemSelected(MenuItem item){
        //Toast.makeText(context,item.getTitle(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder vh=new ViewHolder(view);
        context=parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(urls.get(position),longClickListener);
        holder.bindOnClick(urls.get(position),onClickListener);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        ImageView foto;

        public ViewHolder(View itemView) {
            super(itemView);
            foto=(ImageView)itemView.findViewById(R.id.cardview_fotosg_imageview);
            itemView.setOnCreateContextMenuListener(this);
        }
        public void bind(final String url,final LongClickListener longClickListener){
            Picasso.with(context).load(url).into(foto);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickListener.onItemLongClick(url,getAdapterPosition());
                    return false;
                }
            });
        }
        public void bindOnClick(final String url,final OnClickListener onClickListener){
            //Picasso.with(context).load(url).into(foto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.OnItemClickListener(url,getAdapterPosition());
                }
            });
        }
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Selec action");
            contextMenu.add(0,R.id.menufoto_eliminar,0,R.string.eliminar);
            contextMenu.add(0,R.id.menufoto_portada,0,R.string.portada_foto);
        }
    }
    public interface LongClickListener{
        void onItemLongClick(String url,int position);
    }
    public interface OnClickListener{
        void OnItemClickListener(String url,int position);
    }
}
