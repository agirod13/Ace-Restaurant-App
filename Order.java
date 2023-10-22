package girod.anthony.acerestaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import girod.anthony.acerestaurant.Adapter.MutatorItem;
import girod.anthony.acerestaurant.Model.ItemInfo;
import girod.anthony.acerestaurant.View.CartAction;

import java.util.ArrayList;

public class Order extends AppCompatActivity implements MutatorItem.CallBackUs, MutatorItem.HomeCallBack {

    public static ArrayList<ItemInfo> arrayList = new ArrayList<>();
    public static int cart_count = 0;
    MutatorItem mutatorItem;
    RecyclerView itemRecyclerView;

    //Instantiates the method of adding food items to the ordering screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addItem();
        mutatorItem = new MutatorItem(arrayList, this, this);
        itemRecyclerView = findViewById(R.id.product_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        itemRecyclerView.setLayoutManager(gridLayoutManager);
        itemRecyclerView.setAdapter(mutatorItem);
    }

    // Creates an array of food items, their prices, and the image associated with them
    private void addItem() {
        ItemInfo itemInfo = new ItemInfo("The Ace Burger", "12", R.drawable.burger);
        arrayList.add(itemInfo);

        ItemInfo itemInfo1 = new ItemInfo("Steak Burrito", "15", R.drawable.burrito);
        arrayList.add(itemInfo1);

        ItemInfo itemInfo2 = new ItemInfo("House Salad", "10", R.drawable.salad);
        arrayList.add(itemInfo2);

        ItemInfo itemInfo3 = new ItemInfo("BLT", "10", R.drawable.blt);
        arrayList.add(itemInfo3);

        ItemInfo itemInfo12 = new ItemInfo("Fried Fish Sandwich", "12", R.drawable.fish);
        arrayList.add(itemInfo12);

        ItemInfo itemInfo23 = new ItemInfo("Personal Pizza", "13", R.drawable.pizza);
        arrayList.add(itemInfo23);

        ItemInfo itemInfo4 = new ItemInfo("Mozzarella Sticks", "8", R.drawable.mozz);
        arrayList.add(itemInfo4);

        ItemInfo itemInfo14 = new ItemInfo("Calamari", "10", R.drawable.calamari);
        arrayList.add(itemInfo14);

        ItemInfo itemInfo25 = new ItemInfo("Nachos", "12", R.drawable.nachos);
        arrayList.add(itemInfo25);

        ItemInfo itemInfo5 = new ItemInfo("Chicken Marsala", "22", R.drawable.marsala);
        arrayList.add(itemInfo5);

        ItemInfo itemInfo16 = new ItemInfo("Wedge Salad", "17", R.drawable.wedge);
        arrayList.add(itemInfo16);

        ItemInfo itemInfo6 = new ItemInfo("Pasta Carbonaro", "18", R.drawable.carbonara);
        arrayList.add(itemInfo6);

        ItemInfo itemInfo7 = new ItemInfo("Lasagna", "20", R.drawable.lasagna);
        arrayList.add(itemInfo7);

        ItemInfo itemInfo8 = new ItemInfo("Lobster Roll", "25",R.drawable.lobster);
        arrayList.add(itemInfo8);

        ItemInfo itemInfo9 = new ItemInfo("Chocolate Lava Cake", "8", R.drawable.lava);
        arrayList.add(itemInfo9);

        ItemInfo itemInfo10 = new ItemInfo("Tiramisu", "9", R.drawable.tiramisu);
        arrayList.add(itemInfo10);

        ItemInfo itemInfo11 = new ItemInfo("Cookies and Milk", "7", R.drawable.cookies);
        arrayList.add(itemInfo11);

        ItemInfo itemInfo13 = new ItemInfo("Sprite", "2", R.drawable.sprite);
        arrayList.add(itemInfo13);

        ItemInfo itemInfo15 = new ItemInfo("Coke", "2", R.drawable.coke);
        arrayList.add(itemInfo15);

        ItemInfo itemInfo17 = new ItemInfo("Diet Coke", "2", R.drawable.diet);
        arrayList.add(itemInfo17);

        ItemInfo itemInfo18 = new ItemInfo("Ginger Ale", "2", R.drawable.ginger);
        arrayList.add(itemInfo18);

        ItemInfo itemInfo19 = new ItemInfo("Coffee", "3", R.drawable.coffee);
        arrayList.add(itemInfo19);

        ItemInfo itemInfo20 = new ItemInfo("Green Tea", "3", R.drawable.tea);
        arrayList.add(itemInfo20);

        ItemInfo itemInfo21 = new ItemInfo("Mac and Cheese", "6", R.drawable.mac);
        arrayList.add(itemInfo21);

        ItemInfo itemInfo22 = new ItemInfo("Chicken Fingers", "8", R.drawable.fingers);
        arrayList.add(itemInfo22);

        ItemInfo itemInfo24 = new ItemInfo("Little Ace Burger", "10", R.drawable.kidburger);
        arrayList.add(itemInfo24);
    }

    @Override
    public void addCartItemView() {

    }

    //Mutates shopping cart badge as per instructions in Badge Mutator
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);
        menuItem.setIcon(BadgeMutator.convertLayoutToImage(Order.this, cart_count, R.drawable.ic_shopping_cart_white_24dp));
        return true;
    }

    // Allows users to click on shopping card badge to proceed to next screen to place order
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.cart_action:
                if (cart_count < 1) {
                    Toast.makeText(this, "there is no item in cart", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(this, CartAction.class));
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    // Updates cart count
    @Override
    public void updateCartCount(Context context) {
        invalidateOptionsMenu();
    }

    // "Redraws" menu during runtime to accommodate changes that have taken place
    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
    }
}
