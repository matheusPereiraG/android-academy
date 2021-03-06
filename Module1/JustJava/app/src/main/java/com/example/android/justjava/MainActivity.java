/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    private CheckBox chocolate;
    private EditText nameHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init variables
        quantityTextView = findViewById(R.id.quantity_text_view);
        quantity = Integer.parseInt(quantityTextView.getText().toString());
        cookieBtn = findViewById(R.id.btn_cookie);
        whippedCream = findViewById(R.id.whipped_cream_checkbox);
        chocolate = findViewById(R.id.chocolate_checkbox);
        nameHolder = findViewById(R.id.edit_name);

        cookieBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, CookieActivity.class);
            startActivity(intent);
        });
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        boolean hasWhippedCream = whippedCream.isChecked();
        boolean hasChocolate = chocolate.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String name = nameHolder.getText().toString();
        String messageBody = createOrderSummary(price, hasWhippedCream, hasChocolate,name);
        String subject = getApplicationName(this) + " order for " + name;
        composeEmail(subject, messageBody);
    }

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        return name
                + "\nAdd whipped cream? "
                + (hasWhippedCream ? "true" : "false")
                + "\nAdd chocolate? "
                + (hasChocolate ? "true" : "false")
                + "\nQuantity: " + quantity
                + "\nTotal: $" + price
                + "\nThank you!";
    }

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        return quantity * 5
                + (hasWhippedCream ? quantity : 0)
                + (hasChocolate ? quantity * 2 : 0);
    }

    public void composeEmail(String subject, String messageBody) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_TEXT, messageBody);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity() {
        quantityTextView.setText(String.valueOf(quantity));
    }

    public void incQuantity(View view) {
        quantity = quantity < 100 ? quantity +1 : quantity;
        displayQuantity();
    }

    public void decQuantity(View view) {
        quantity = (quantity) == 1 ? 1 : quantity - 1;
        displayQuantity();
    }

    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }
}