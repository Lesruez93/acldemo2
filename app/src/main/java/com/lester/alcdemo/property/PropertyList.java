package com.lester.alcdemo.property;


import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lester.alcdemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PropertyList extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    private PropertyApiAdapter storeApiAdapter;
    private List<PropertyApi> propertyApiList;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.property_recycler);


        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());


        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);

    //shimmerFrameLayout = view.findViewById(R.id.shimmerLayout);
        // shimmerFrameLayout.startShimmerAnimation();

        propertyApiList = new ArrayList<>();
        storeApiAdapter = new PropertyApiAdapter(this, propertyApiList);
        RecyclerView rsRecyclerView = findViewById(R.id.shpr);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view1 ->
                this.onBackPressed());
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        rsRecyclerView.setLayoutManager(linearLayout);
        rsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        rsRecyclerView.setHasFixedSize(true);
        rsRecyclerView.setAdapter(storeApiAdapter);




        loadData();

    }


    // Spinner click listener


    private void loadData() {
//


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("pp")//.whereEqualTo("category", "music")

                // .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    //Add each document to chatList

                    if (e != null) {
                        return;
                    }
                    //   prayersList.removeAll(prayersList);

                    assert queryDocumentSnapshots != null;
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                        if (doc != null) {
                            PropertyApi propertyApi = new PropertyApi();

                            try {
                                propertyApi.setItem(doc.get("title").toString());
                                propertyApi.setImage(doc.get("image").toString());
                                propertyApi.setPrice(doc.get("price").toString());
                                propertyApi.setDesc(doc.get("desc").toString());
                                propertyApi.setStoreId(doc.getId());

                            } catch (NullPointerException ignored) {

                            }

                            propertyApiList.add(propertyApi);

                        }


                    }

                    storeApiAdapter.notifyDataSetChanged();

                });
    }

//
//
//


}
