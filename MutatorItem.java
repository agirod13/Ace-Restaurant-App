package girod.anthony.acerestaurant.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import girod.anthony.acerestaurant.Order;
import girod.anthony.acerestaurant.Model.ItemLogic;
import girod.anthony.acerestaurant.Model.ItemInfo;
import girod.anthony.acerestaurant.R;
import girod.anthony.acerestaurant.View.CartAction;
import static girod.anthony.acerestaurant.Order.arrayList;

public class MutatorItem extends RecyclerView.Adapter<MutatorItem.ViewHolder> {

    public static ArrayList<ItemInfo> productsArray;
    public static ArrayList<ItemLogic> cartModels = new ArrayList<ItemLogic>();
    public static ItemLogic cartModel;
    Context context;
    private HomeCallBack homeCallBack;

    public MutatorItem(ArrayList<ItemInfo> productsArray, Context context, HomeCallBack mCallBackus) {
        this.productsArray = productsArray;
        this.context = context;
        this.homeCallBack = mCallBackus;
    }

    @NonNull
    @Override
    public MutatorItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_adapter, viewGroup, false);
        return new MutatorItem.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MutatorItem.ViewHolder viewHolder, final int i) {
        viewHolder.productName.setText(productsArray.get(i).productName);
        viewHolder.productImage.setImageDrawable(ContextCompat.getDrawable(context, productsArray.get(i).imagePath));

        // Messages users about errors associated users attempting to add item quantities less than 0
        viewHolder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_item_quantity_update);
                dialog.setTitle("Custom Dialog");
                final ImageView cartDecrement = dialog.findViewById(R.id.cart_decrement);
                ImageView cartIncrement = dialog.findViewById(R.id.cart_increment);
                ImageView closeDialog = dialog.findViewById(R.id.imageView_close_dialog_cart);
                TextView updateQtyDialog = dialog.findViewById(R.id.update_quantity_dialog);
                TextView viewCartDialog = dialog.findViewById(R.id.view_cart_button_dialog);
                final TextView quantity = dialog.findViewById(R.id.cart_product_quantity_tv);
                quantity.setText(String.valueOf(0));
                final int[] cartCounter = {0};
                cartDecrement.setEnabled(false);
                cartDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cartCounter[0] == 1) {
                            Toast.makeText(context, "cant add less than 0", Toast.LENGTH_SHORT).show();
                        } else {
                            cartCounter[0] -= 1;
                            quantity.setText(String.valueOf(cartCounter[0]));
                        }

                    }
                });
                cartIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cartDecrement.setEnabled(true);
                        cartCounter[0] += 1;
                        quantity.setText(String.valueOf(cartCounter[0]));


                    }
                });
                viewCartDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        context.startActivity(new Intent(context, CartAction.class));
                    }
                });

                dialog.show();
                updateQtyDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, String.valueOf(cartCounter[0]) + "", Toast.LENGTH_SHORT).show();

                        // This block adds items to the cart
                        cartModel = new ItemLogic();
                        cartModel.setProductQuantity((cartCounter[0]));
                        cartModel.setProductPrice(arrayList.get(i).getPrice());
                        cartModel.setProductImage(arrayList.get(i).getImagePath());
                        cartModel.setTotalAmount(cartCounter[0] *
                                Integer.parseInt(arrayList.get(i).getPrice()));

                        cartModels.add(cartModel);

                        // This block updates the value held within the shopping cart badge
                        Order.cart_count = 0;
                        for (int i = 0; i < cartModels.size(); i++) {
                            for (int j = i + 1; j < cartModels.size(); j++) {
                                if (cartModels.get(i).getProductImage().equals(cartModels.get(j).getProductImage())) {
                                    cartModels.get(i).setProductQuantity(cartModels.get(j).getProductQuantity());
                                    cartModels.get(i).setTotalAmount(cartModels.get(j).getTotalAmount());
                                    cartModels.remove(j);
                                    j--;
                                }
                            }
                        }
                        Order.cart_count = cartModels.size();

                        // This shows the updated value of the shopping cart badge
                        homeCallBack.updateCartCount(context);
                        dialog.dismiss();
                    }

                });

                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Order.cart_count = 0;
                        for (int i = 0; i < cartModels.size(); i++) {
                            for (int j = i + 1; j < cartModels.size(); j++) {
                                if (cartModels.get(i).getProductImage().equals(cartModels.get(j).getProductImage())) {
                                    cartModels.get(i).setProductQuantity(cartModels.get(j).getProductQuantity());
                                    cartModels.get(i).setTotalAmount(cartModels.get(j).getTotalAmount());
                                    cartModels.remove(j);
                                    j--;
                                }
                            }
                        }
                        Order.cart_count = cartModels.size();
                        homeCallBack.updateCartCount(context);
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsArray.size();
    }

    public interface CallBackUs {
        void addCartItemView();
    }
    // This method creates the call to invalidateOptionsMenu()
    public interface HomeCallBack {
        void updateCartCount(Context context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;

        ViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.android_gridview_image);
            productName = itemView.findViewById(R.id.android_gridview_text);
        }
    }
}
