package girod.anthony.acerestaurant.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

import girod.anthony.acerestaurant.Adapter.MutatorCart;
import girod.anthony.acerestaurant.Order;
import girod.anthony.acerestaurant.Model.ItemLogic;
import girod.anthony.acerestaurant.R;
import girod.anthony.acerestaurant.Thank;
import static girod.anthony.acerestaurant.Adapter.MutatorItem.cartModels;

public class CartAction extends AppCompatActivity {

    public static TextView grandTotal;
    public static int grandTotalplus;

    // Creates a temporary list and adds cart items list
    public static ArrayList<ItemLogic> temparraylist;
    RecyclerView cartRecyclerView;
    MutatorCart mutatorCart;
    LinearLayout proceedToBook;
    Context context;
    private Toolbar mToolbar;

    // Navigates user to screen after clicking on shopping cart badge
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        context = this;
        temparraylist = new ArrayList<>();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        proceedToBook = findViewById(R.id.proceed_to_book);
        grandTotal = findViewById(R.id.grand_total_cart);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cart");

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grandTotalplus = 0;
                for (int i = 0; i < temparraylist.size(); i++) {

                }
                cartModels.addAll(temparraylist);
                Order.cart_count = (temparraylist.size());
                finish();
            }
        });

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

        temparraylist.addAll(cartModels);
        cartModels.clear();

        // This loop calculates and displays the value of the user's order
        for (int i = 0; i < temparraylist.size(); i++) {
            grandTotalplus = (grandTotalplus + temparraylist.get(i).getTotalAmount());
        }

        grandTotal.setText("$ " + String.valueOf(grandTotalplus));
        cartRecyclerView = findViewById(R.id.recycler_view_cart);
        mutatorCart = new MutatorCart(temparraylist, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        cartRecyclerView.setLayoutManager(mLayoutManager);
        cartRecyclerView.setAdapter(mutatorCart);

        //Opens Thank class to thank user for their purchase after clicking "Order"
        proceedToBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openThank();
            }
        });
    }
    // Method that opens Thank class
    public void openThank() {
        Intent intent = new Intent(this, Thank.class);
        startActivity(intent);
    }

    // Deletes order information (total price, item selection) if back is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        grandTotalplus = 0;
        for (int i = 0; i < temparraylist.size(); i++) {
            Order.cart_count = (temparraylist.size());
        }
        cartModels.addAll(temparraylist);
    }
}
