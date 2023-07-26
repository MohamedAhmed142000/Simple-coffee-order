package com.example.jastjavae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;


import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void increaseQuantityNumber(View view)    {
        displayQuantity(++quantity);


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void OrderSummary(View view) {
        //to check user choose whipped crean or not

        CheckBox whippedcreamCheck =  (CheckBox) findViewById(R.id.whipped_cream_ckeckBox);
        boolean whipped_cream=whippedcreamCheck.isChecked();
        Log.v("MainActivity"," whipped cream checked "+whipped_cream);
        //to check user choose chocalate or not

        CheckBox choclateCheck =  (CheckBox) findViewById(R.id.choclate_ckeckBox);
         boolean   choclate=choclateCheck.isChecked();
        Log.v("MainActivity"," whipped cream checked "+choclate);
        //to user enter the name
        EditText nameclint =(EditText) findViewById(R.id.Edit_name);
        String name =nameclint.getText().toString();
        int price =calculatePrice(whipped_cream,choclate);
        String PriceMessage=MethodSummeryOrder(price,whipped_cream,choclate,name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "jast java from "+name);
        intent.putExtra(Intent.EXTRA_TEXT,PriceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(PriceMessage);


    }

    public void decreaseQuantityNumber(View view) {
        if(quantity > 0)
            displayQuantity(--quantity);
        if(quantity<=1) {
            displayQuantity(quantity = 1);
            Toast.makeText(this, "you cannot have less   1 coffe ", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int  num) {
        TextView quantityTextView = (TextView) findViewById(R.id.textView_quantityNumber);
        quantityTextView.setText("" + num);
    }
    /**
     * Calculates the price of the order.
     */
    private int  calculatePrice( boolean whipped_cream, boolean choclate) {
        if (whipped_cream==false&&choclate==false)
                 price = quantity * 5;
        else if (whipped_cream==true&&choclate==false)
                price =quantity*(5+1);
        else if (whipped_cream==false&&choclate==true)
            price =quantity*(5+2);
        else
            price=quantity*(5+1+2);
        return price;
    }
    /**
     * meassage of summery order
     * @param price of the order
     * @param addwhippedcream to check add whipped cream or not?
     * @param Name to get name from user
     */
    private String MethodSummeryOrder(int price, boolean addwhippedcream, boolean addchoclate, String Name){
       String PriceMessage="Name : "+Name;
        PriceMessage+="\nAdd whipped cream ?"+addwhippedcream;
        PriceMessage+="\nAdd choclate ?  "+addchoclate;
        PriceMessage+="\nQuantity : "+ quantity;
        PriceMessage+= "\nTotal :"+price+"$";
        PriceMessage+="\nThank You !";
        return PriceMessage;

    }
    private void displayMessage(String message) {

        TextView ordersummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        ordersummaryTextView.setText(message);
    }
    public void  onStop() {

        super.onStop();
        Toast.makeText(this, "thank you for user cafe", Toast.LENGTH_SHORT).show();
    }
}