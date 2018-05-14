package com.example.mauri.intergrami.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mauri.intergrami.Models.Mis_productos;
import com.example.mauri.intergrami.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapterMisproductoscomprados extends RecyclerView.Adapter<MyAdapterMisproductoscomprados.ViewHolder>{
    List<Mis_productos> misproductos;
    private int layout;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public MyAdapterMisproductoscomprados(List<Mis_productos> misproductos, int layout, OnItemClickListener onItemClickListener) {
        this.misproductos = misproductos;
        this.layout = layout;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder vh= new ViewHolder(v);
        context=parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(misproductos.get(position),onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return misproductos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        ImageView imageViewFoto;
        Button btnDetalles,btnShare;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle=(TextView)itemView.findViewById(R.id.cardview_miscompras_textViewNombreProd);
            imageViewFoto=(ImageView)itemView.findViewById(R.id.cardview_miscompras_ImageViewProducto);
            btnDetalles=(Button)itemView.findViewById(R.id.cardview_miscompras_btnDetalles);
            btnShare=(Button)itemView.findViewById(R.id.cardview_miscompras_btnCompartir);
        }
        public void bind(final Mis_productos producto,final OnItemClickListener listener){
            tvTitle.setText(producto.getNombre().toString());
            Picasso.with(context).load(producto.getUrlfoto()).into(imageViewFoto);
            btnDetalles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "Click Detalles", Toast.LENGTH_SHORT).show();
                }
            });
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "Click Share", Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnItemClick(producto,getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(Mis_productos miproducto, int position);
    }
}
