/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private TextView quantityTextView;
    private int quantity;
    private Button cookieBtn;
    private CheckBox whippedCream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init variables
        quantityTextView = findViewById(R.id.quantity_text_view);
        quantity = Integer.parseInt(quantityTextView.getText().toString());
        cookieBtn = findViewById(R.id.btn_cookie);
        whippedCream = findViewById(R.id.whipped_cream_checkbox);

        cookieBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, CookieActivity.class);
            startActivity(intent);
        });
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice();
        boolean hasWhippedCream = whippedCream.isChecked();
        String message = createOrderSummary(price, hasWhippedCream);
        displayMessage(message);
    }

    private String createOrderSummary(int price, boolean hasWhippedCream) {
        return "Matheus Gonçalves"
                + "\nAdd whipped cream? "
                + (hasWhippedCream ? "true" : "false")
                + "\nQuantity: " + quantity
                + "\nTotal: $" + price
                + "\nThank you!";
    }

    private int calculatePrice() {
        return quantity * 5;
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity() {
        quantityTextView.setText(String.valueOf(quantity));
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    public void incQuantity(View view) {
        quantity += 1;
        displayQuantity();
    }

    public void decQuantity(View view) {
        quantity = (quantity) == 0 ? 0 : quantity - 1;
        displayQuantity();
    }
}