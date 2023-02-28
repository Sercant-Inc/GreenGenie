package com.sergio.greengenie.UI.Main;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sergio.greengenie.Bill;

import java.util.ArrayList;


public class PageViewModel extends ViewModel {
    private static final int GOALINDEX = -7;
    private static final String TAG = "My App";
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    //  private ArrayList<Bill> bills = new ArrayList<>();
    private MutableLiveData<ArrayList<Bill>> bills = new MutableLiveData<>();
    private MutableLiveData<Bill> goal = new MutableLiveData<>();
    private MutableLiveData<Boolean> loaded = new MutableLiveData<>();
    private MutableLiveData<Boolean> updated = new MutableLiveData<>();
    private MutableLiveData<Boolean> added = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public MutableLiveData<Boolean> getUpdated() {
        return updated;
    }

    public MutableLiveData<Boolean> getAdded() {
        return added;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<Bill> getGoal() {
        return goal;
    }

    public int getGoalindex() {
        return GOALINDEX;
    }

    public void setBills(ArrayList<Bill> bill) {
        bills.setValue(bill);
    }

    public LiveData<ArrayList<Bill>> getBills() {
        return bills;
    }

    public MutableLiveData<Boolean> getLoaded() {
        return loaded;
    }

    public void loadfirebase() {
        FirebaseFirestore.getInstance().collection("bills")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderBy("index", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<Bill> bills = new ArrayList<>();
                    Bill goal = null;
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Bill bill = document.toObject(Bill.class);
                        Log.d("My App", bill.getIndex() + "index");
                        if (bill.getIndex() == GOALINDEX) {
                            goal = bill;

                        } else {

                            bills.add(bill);
                        }
                        Log.d("Firestore", document.getId() + " => " + document.getData());
                    }
                    Log.d("My app", bills.size() + "");
                    this.bills.setValue(bills);
                    if (goal != null) {
                        getGoal().setValue(goal);
                    }
                    if (bills.isEmpty()) {
                        Log.d("My App", "The bills collection is empty.");
                        loaded.setValue(false);
                    } else {
                        loaded.setValue(true);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "Error getting documents: ", e);
                });
    }

    public void updateFirebase(Bill bill) {
        FirebaseFirestore.getInstance().collection("bills")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .whereEqualTo("index", bill.getIndex())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        document.getReference().set(bill)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "Document successfully updated");
                                    updated.setValue(true);
                                })
                                .addOnFailureListener(e -> {
                                    Log.e(TAG, "Error updating document", e);
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error searching document" + bill.getIndex(), e);
                });
    }

    public void addtofirebase(Bill bill) {
        FirebaseFirestore.getInstance().collection("bills").add(bill)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error adding document", e);
                });
        added.setValue(true);
    }
}