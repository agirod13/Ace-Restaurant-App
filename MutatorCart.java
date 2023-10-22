package girod.anthony.acerestaurant.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;

import girod.anthony.acerestaurant.Model.ItemLogic;
import girod.anthony.acerestaurant.R;
import static girod.anthony.acerestaurant.View.CartAction.grandTotal;
import static girod.anthony.acerestaurant.View.CartAction.grandTotalplus;
import static girod.anthony.acerestaurant.View.CartAction.temparraylist;

public class MutatorCart extends RecyclerView.Adapter<MutatorCart.ViewHolder> {
    ArrayList<ItemLogic> cartModelArrayList;
    Context context;

    public MutatorCart(ArrayList<ItemLogic> cartModelArrayList, Context context) {
        this.context = (Context) context;
        this.cartModelArrayList = cartModelArrayList;
    }

    @Override
    public MutatorCart.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        return new MutatorCart.ViewHolder(v);
    }

    // This block shows the process for how data for is held related to each individual food item
    @Override
    public void onBindViewHolder(final MutatorCart.ViewHolder holder, final int position) {
        holder.productCartPrice.setText(String.valueOf(cartModelArrayList.get(position).getTotalAmount()));
        holder.productCartCode.setText(cartModelArrayList.get(position).getProductName());
        holder.productCartQuantity.setText(String.valueOf(cartModelArrayList.get(position).getProductQuantity()));

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.burger);
        requestOptions.error(R.drawable.burger);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(cartModelArrayList.get(position).getProductImage()).into(holder.productCartImage);

        //This block allows for users to delete items from their cart and updates the total order price
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cartModelArrayList.size() == 1) {
                    cartModelArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, cartModelArrayList.size());
                    grandTotalplus = 0;
                    grandTotal.setText(String.valueOf(grandTotalplus));
                }

                if (cartModelArrayList.size() > 0) {
                    cartModelArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, cartModelArrayList.size());
                    grandTotalplus = 0;
                    for (int i = 0; i < temparraylist.size(); i++) {
                        grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalAmount();
                    }

                    grandTotal.setText(String.valueOf(grandTotalplus));

                } else {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // This block updates the quantity of the cart items and total order cost
        holder.cartIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grandTotalplus = 0;
                holder.cartDecrement.setEnabled(true);

                int cartUpdateCounter = (cartModelArrayList.get(position).getProductQuantity());

                holder.cartIncrement.setEnabled(true);
                cartUpdateCounter += 1;

                cartModelArrayList.get(position).setProductQuantity((cartUpdateCounter));
                int amount = (Integer.parseInt(cartModelArrayList.get(position).getProductPrice()) * (cartModelArrayList.get(position).getProductQuantity()));

                holder.productCartQuantity.setText(String.valueOf(cartModelArrayList.get(position).getProductQuantity()));

                cartModelArrayList.get(position).setTotalAmount(amount);
                holder.productCartPrice.setText(String.valueOf(amount));

                for (int i = 0; i < temparraylist.size(); i++) {
                    grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalAmount();
                }
                grandTotal.setText(String.valueOf(grandTotalplus));
            }

        });

        // This block defines the process for decreasing the quantity of items and updating the price of the order
        holder.cartDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grandTotalplus = 0;
                holder.cartIncrement.setEnabled(true);

                int cartUpdateCounter = (cartModelArrayList.get(position).getProductQuantity());

                if (cartUpdateCounter == 1) {
                    holder.cartDecrement.setEnabled(false);
                    Toast.makeText(context, "quantity can't be zero", Toast.LENGTH_SHORT).show();
                } else {
                    holder.cartDecrement.setEnabled(true);
                    cartUpdateCounter -= 1;
                    cartModelArrayList.get(position).setProductQuantity((cartUpdateCounter));
                    holder.productCartQuantity.setText(String.valueOf(cartModelArrayList.get(position).getProductQuantity()));
                    int amount = (Integer.parseInt(cartModelArrayList.get(position).getProductPrice()) * (cartModelArrayList.get(position).getProductQuantity()));

                    cartModelArrayList.get(position).setTotalAmount(amount);
                    holder.productCartPrice.setText(String.valueOf(amount));
                    for (int i = 0; i < temparraylist.size(); i++) {
                        grandTotalplus = grandTotalplus + temparraylist.get(i).getTotalAmount();
                    }

                    grandTotal.setText("$ "+ String.valueOf(grandTotalplus));
                }
            }
        });
    }

    // Returns size of array of items in cart array
    @Override
    public int getItemCount() {
        return cartModelArrayList.size();
    }

    // Defines the view of
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productCartImage, cartIncrement, cartDecrement, deleteItem;
        TextView productCartCode, productCartPrice, productCartQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            productCartImage = itemView.findViewById(R.id.list_image_cart);
            deleteItem = itemView.findViewById(R.id.delete_item_from_cart);
            productCartCode = itemView.findViewById(R.id.product_cart_code);
            productCartPrice = itemView.findViewById(R.id.product_cart_price);
            productCartQuantity = itemView.findViewById(R.id.cart_product_quantity_tv);
            cartDecrement = itemView.findViewById(R.id.cart_decrement);
            cartIncrement = itemView.findViewById(R.id.cart_increment);
        }
    }
}