package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();

        List<String> alsoKnownAs = new ArrayList<>();

        List<String> ingredients = new ArrayList<>();

        try {
            JSONObject baseObject = new JSONObject(json);
            JSONObject nameObject = getJsonObject("name", baseObject);

            String mainNameObject = getString("mainName", nameObject);
            sandwich.setMainName(mainNameObject);

            JSONArray jsonArrayAlsoKnownAs = getList("alsoKnownAs", nameObject);
            for(int i = 0; i < jsonArrayAlsoKnownAs.length(); i++) {
                alsoKnownAs.add(jsonArrayAlsoKnownAs.getString(i));
            }
            sandwich.setAlsoKnownAs(alsoKnownAs);

            String description = getString("description", baseObject);
            sandwich.setDescription(description);

            JSONArray jsonArrayIngredients = getList("ingredients", baseObject);
            for(int n = 0; n < jsonArrayIngredients.length(); n++) {
                ingredients.add(jsonArrayIngredients.getString(n));
            }
            sandwich.setIngredients(ingredients);

            String placeOfOrigin = getString("placeOfOrigin", baseObject);
            sandwich.setPlaceOfOrigin(placeOfOrigin);

            String image = getString("image", baseObject);
            sandwich.setImage(image) ;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    private static JSONObject getJsonObject(String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject(tagName);
    }

    private static String getString(String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getString(tagName);
    }

    private static JSONArray getList(String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONArray(tagName);
    }

}
