package com.example.user.coffeeshop;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.coffeeshop.R;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int toppings = 0;
    int price = 0;
    private CheckBox whipped;
    private CheckBox chocolate;
    String topping_taken;
    String orderMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        whipped = findViewById(R.id.whipped);
        chocolate = findViewById(R.id.chocolate);
        topping_taken = new String();
    }

    /**
     * This method is called when the order button is clicked.
     */


    public void increment(View view) {
        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity > 0) {
            --quantity;
            display(quantity);
        } else {
            Toast.makeText(this, "Can't Go Negative", Toast.LENGTH_SHORT).show();
        }

    }

    public void Cream(View view) {
       /* boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.whipped:
                if (checked) {
                    topping_taken = "Yes,Whipped Cream!";
                    toppings = 1;
                } else {
                    toppings = 0;
                    topping_taken = "No Sir!";
                }
                break;
            case R.id.chocolate:
                if (checked) {
                    topping_taken = "Yes,Chocolate Cream!";
                    toppings = 2;
                } else {
                    toppings = 0;
                    displayMessage("No Sir!");
                }
                break;*/

        if (whipped.isChecked() && chocolate.isChecked()) {
            topping_taken = "Both";
            toppings = 1 + 2;
        } else if (chocolate.isChecked()) {
            topping_taken = "Yes,Chocolate Cream!";
            toppings = 2;
        } else if (whipped.isChecked()) {
            toppings = 1;
            topping_taken = "Yes,Whipped Cream!";
        } else {
            topping_taken = "No Sir!";
            toppings = 0;
        }
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(number);
    }

    private void displayMessage(String a) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(a);

    }

    public void OrderSummary() {
        orderMessage = "Name: HawkEye \nQuantity = " + quantity +
                "\nToppings Taken? = " + topping_taken + "\nTotal Payable = " + price + "\nThank You!";
        displayMessage(orderMessage);
    }

    public void submitOrder(View view) {
        Cream(findViewById(R.id.whipped));
        if (toppings == 0) {
            Cream(findViewById(R.id.chocolate));
        }
        price = (quantity * (5 + toppings));
        OrderSummary();
   /*     String priceMessage = "Please pay "+ "₹"  + price +  " at the counter";
        displayMessage(priceMessage);*/
//        displayPrice(quantity*5);

    }

    public void email(View view) {
        Intent mailClient = new Intent(Intent.ACTION_SENDTO);
        mailClient.setData(Uri.parse("mailto:"));
        mailClient.putExtra(Intent.EXTRA_EMAIL, "subhankar.vawsum@gmail.com");
        mailClient.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order Details");
        mailClient.putExtra(Intent.EXTRA_TEXT, orderMessage);
        if (mailClient.resolveActivity(getPackageManager()) != null) {
            startActivity(mailClient);
        }
    }

    public void cls(View view) {
        quantity=0;
        display(quantity);
        if(whipped.isChecked()){
            whipped.toggle();
        }
        if(chocolate.isChecked()){
            chocolate.toggle();
        }
        orderMessage = "You just cleared it bruh!";
        displayMessage(orderMessage);
    }

    public void details(View view) {

    }
}
