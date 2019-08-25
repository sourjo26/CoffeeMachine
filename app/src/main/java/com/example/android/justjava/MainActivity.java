package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the order button is clicked.
     */


    public void increment(View view) {
        if(quantity==100) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        quantity = quantity + 1;
        display(quantity);
    }
    public void decrement(View view) {
        if(quantity==1)
        {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }

        else
        quantity = quantity - 1;
        display(quantity);
    }
    public void submitOrder(View view)
    {
        CheckBox cream=(CheckBox) findViewById(R.id.cream);
        boolean hasWhippedCream= cream.isChecked();

        CheckBox choco=(CheckBox) findViewById(R.id.Choco);
        boolean haschoco= choco.isChecked();

        EditText textname = findViewById(R.id.name_field);
        String name =  textname.getText().toString();

        int price=calculatePrice(hasWhippedCream,haschoco);

        String abc= createOrderSummary(price,hasWhippedCream,haschoco,name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for "+name);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(abc);
    }
    private String createOrderSummary(int price,boolean WhippedCream,boolean choco,String name)
    {
        String priceMessage= "Name:"+ name;
        priceMessage=priceMessage+"\nQuantity "+quantity;
                priceMessage=priceMessage+"\nAdd Whipped Cream?"+WhippedCream;
                priceMessage=priceMessage+"\nAdd Chocolate?"+choco;
                priceMessage=priceMessage+"\nTotal:$" + price ;
                priceMessage=priceMessage+"\n Thank you";
                return (priceMessage);
    }


    private int calculatePrice(boolean WhippedCream,boolean haschoco )
    {
        int price;
        if(WhippedCream==true && haschoco==false)
        {
            price=quantity*6;
        }
        else if (WhippedCream==false && haschoco==true)
            price= quantity*7;
        else if(WhippedCream==true && haschoco==true)
            price= quantity*8;
        else
         price= quantity*5;
        return price;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int num) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        if(num<1 || num>100)
            return;
        else
            quantityTextView.setText("" + num);
    }
    private void displayMessage(String message)
    {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}