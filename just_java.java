package com.example.ansulsingh.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increament
    (View view) {
        if(quantity == 100) {
            Toast.makeText(this, "Sorry, you cann't order more than 100", Toast.LENGTH_SHORT).show();
            // yha par this ka mtlb uss activity se h jisme ye code likha h i.e. MainActiviy
            return;
        }
        //agar aisa hua to hum immediately increament method ke bahar aa jayenge, quantity++ bhi execute nhi hoga
        quantity++;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decreament
    (View view) {
        if(quantity == 1) {
            Toast.makeText(this, "Sorry, you cann't order less than 1", Toast.LENGTH_SHORT).show();
            return;
        }
        //this means we should exit from method immediately
        quantity--;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder
    (View view) {
        EditText text = (EditText) findViewById(R.id.name_field);
        String name = text.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price, hasWhippedCream, hasChocolate, name));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }

    private String createOrderSummary(int price, boolean haswhippedcream, boolean hasChocolate, String name)
    {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd Whipped Cream? " + haswhippedcream;
        priceMessage += "\nAdd Chocolate? " + hasChocolate;
        priceMessage += "\nquantity " + quantity;
        priceMessage += "\nTotal amount: $" + price;
        priceMessage += "\nThank You!";
        return priceMessage;
    }
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int basePrice = 5;
        if(addWhippedCream)
            basePrice = basePrice+1;
        if(addChocolate)
            basePrice = basePrice+2;
           return basePrice*quantity;
    }


}
