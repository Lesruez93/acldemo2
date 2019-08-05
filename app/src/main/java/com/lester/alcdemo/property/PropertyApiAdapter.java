package com.lester.alcdemo.property;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lester.alcdemo.R;

import java.util.List;

public class PropertyApiAdapter extends RecyclerView.Adapter<PropertyApiAdapter.MyViewHolder> {

    private List<PropertyApi> propertyApiList;
    private Context context;




  public PropertyApiAdapter(Context context , List<PropertyApi> storeList)

  {
    this.propertyApiList = storeList;
   this.context = context;

  }



  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {

    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_items,parent,false);
    return new MyViewHolder(view);
  }


  @Override
  public void onBindViewHolder(MyViewHolder holder, int position)  {
      Context context = holder.itemView.getContext();

     final PropertyApi propertyApi = propertyApiList.get(position);
     holder.itemTitle.setText(propertyApiList.get(position).getItem());

    holder.itemPrice.setText(propertyApiList.get(position).getPrice());

    Glide.with(context).load(propertyApi.getImageUrl()).into(holder.llHolder);
    holder.llHolder.setOnClickListener(view -> {
       // Toast.makeText(context, "Please wait......", Toast.LENGTH_LONG).show();

    });


//     holder.llHolder.setOnClickListener(view -> {
//         Intent intent = new Intent(context, PropertyView.class);
//         intent.putExtra("title", propertyApi.getItem());
//         intent.putExtra("desc", propertyApi.getDesc());
//         intent.putExtra("image", propertyApi.getImageUrl());
//         intent.putExtra("price", propertyApi.getPrice());
////         intent.putExtra("images", propertyApi.getImage());
//         context.startActivity(intent);
//
//
//     });



//holder.llHolder.set



  }



  /**
   * Click listener for popup menu items
   */



  @Override
  public int getItemCount() {
    return  propertyApiList.size();
  }



    public class MyViewHolder extends RecyclerView.ViewHolder {

    private TextView itemTitle;
    private TextView itemPrice;
    private TextView desc;

    private ImageView llHolder;
      private ImageView  overflow;


      MyViewHolder(View itemView) {
      super(itemView);
      itemTitle = itemView.findViewById(R.id.item_nm);
      itemPrice = itemView.findViewById(R.id.price);
      desc = itemView.findViewById(R.id.desc);
      llHolder = itemView.findViewById(R.id.item_image);

//
//
//
//
//
//
//
//
//      });
    }
  }


}

