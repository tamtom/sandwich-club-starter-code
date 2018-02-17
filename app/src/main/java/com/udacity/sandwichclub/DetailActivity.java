package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView tvAlsoKnownAs = findViewById(R.id.also_known_tv);
        TextView tvAlsoKnownAsLabel = findViewById(R.id.also_known_label_tv);
        TextView tvPlaceOfOriginLabel = findViewById(R.id.origin_label_tv);
        TextView tvPlaceOfOrigin = findViewById(R.id.origin_tv);
        TextView tvDescription = findViewById(R.id.description_tv);
        TextView tvDescriptionLabel = findViewById(R.id.description_label_tv);
        TextView tvIngredients = findViewById(R.id.ingredients_tv);
        TextView tvIngredientsLabel = findViewById(R.id.ingredients_label_tv);
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            tvAlsoKnownAs.setVisibility(View.GONE);
            tvAlsoKnownAsLabel.setVisibility(View.GONE);
        } else {

            tvAlsoKnownAs.setVisibility(View.VISIBLE);
            tvAlsoKnownAsLabel.setVisibility(View.VISIBLE);
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                tvAlsoKnownAs.append(sandwich.getAlsoKnownAs().get(i) + "\n");
            }
        }
        if (TextUtils.isEmpty(sandwich.getPlaceOfOrigin())) {
            tvPlaceOfOrigin.setVisibility(View.GONE);
            tvPlaceOfOriginLabel.setVisibility(View.GONE);
        } else {

            tvPlaceOfOrigin.setVisibility(View.VISIBLE);
            tvPlaceOfOriginLabel.setVisibility(View.VISIBLE);
            tvPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        }
        if (TextUtils.isEmpty(sandwich.getDescription())) {
            tvDescription.setVisibility(View.GONE);
            tvDescriptionLabel.setVisibility(View.GONE);
        } else {

            tvDescription.setVisibility(View.VISIBLE);
            tvDescriptionLabel.setVisibility(View.VISIBLE);
            tvDescription.setText(sandwich.getDescription());
        }
        if (sandwich.getIngredients().isEmpty()) {
            tvIngredients.setVisibility(View.GONE);
            tvIngredientsLabel.setVisibility(View.GONE);
        } else {

            tvIngredients.setVisibility(View.VISIBLE);
            tvIngredientsLabel.setVisibility(View.VISIBLE);
            for (int n = 0; n < sandwich.getIngredients().size(); n++) {
                tvIngredients.append(sandwich.getIngredients().get(n) + "\n");
            }
        }

    }
}
