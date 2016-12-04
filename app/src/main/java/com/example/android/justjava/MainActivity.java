package com.example.android.justjava;
/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
	 * This method is called when the order button is clicked.
	 */
	public void submitOrder(View view) {
		display(quantity);
		displayPrice(quantity * 5);
	}

	/**
	 * This method displays the given quantity value on the screen.
	 */
	private void display(int number) {
		TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
		quantityTextView.setText("" + number);
	}

	private void displayPrice(int number ) {
		CheckBox checkBoxCream = (CheckBox)findViewById(R.id.isAddedCream);
		Boolean isAddedCream = checkBoxCream.isChecked();
		CheckBox checkBoxChoco = (CheckBox)findViewById(R.id.isAddedChoco);
		Boolean isAddedChoco = checkBoxChoco.isChecked();
		EditText name = (EditText)findViewById(R.id.name);
		String guestName = name.getText().toString();
		int total = calculatePrice(isAddedCream,isAddedChoco);

		String priceMessage = createOrderSummary(guestName,isAddedChoco,isAddedCream,total);


		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setData(Uri.parse("mailto:")); // only email apps should handle this
		intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java onrder for "+guestName);
		intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}


//		TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//		priceTextView.setText("Name:"+guestName+
//				"\nAdd whipped cream?"+isAddedCream+
//				"\nAdd chocolate?" +isAddedChoco+
//				"\nQuantity:"+quantity+
//				"\nYou should pay $"+total);
	}

	private String createOrderSummary(String name, Boolean isAddedChoco, Boolean isAddedCream, int total){
		return "Name:"+name+
				"\nAdd whipped cream?"+isAddedCream+
				"\nAdd chocolate?" +isAddedChoco+
				"\nQuantity:"+quantity+
				"\nYou should pay $"+total;
	}

	private int calculatePrice(Boolean isAddedCream, Boolean isAddedChoco){
		int basePrice = 5;

		if (isAddedCream){
			basePrice+=1;
		}
		if (isAddedChoco){
			basePrice+=2;
		}
		return quantity * basePrice;
	}

	public void increment(View view) {
		if (quantity==100) {
			Toast.makeText(this,"You cannot have more than 100 coffee",Toast.LENGTH_SHORT).show();
			return;
		}
		quantity++;
		display(quantity);
	}

	public void decrement(View view) {
		if (quantity==1) {
			Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
			return;
		}
		quantity--;
		display(quantity);
	}
}