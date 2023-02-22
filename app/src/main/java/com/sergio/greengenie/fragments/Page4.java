package com.sergio.greengenie.Fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sergio.greengenie.UI.Main.PageViewModel;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sergio.greengenie.Bill;

import com.sergio.greengenie.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Page4 extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // Graphic graphic = new Graphic();
    Button btn_done, btn_edit, btn_cancel, btn_newForm;
    EditText[] editTexts = new EditText[10];
    TextView[] setences = new TextView[11];
    ImageView[] icons = new ImageView[6];
    Spinner formSpinner;
    LinearLayout linearLayout;
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    ArrayAdapter<CharSequence> adapter;
    //public static ArrayList<Bill> bills = new ArrayList<Bill>();
    boolean newform = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Firestore";
    //private ArrayList<Bill> bills = new ArrayList<Bill>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PageViewModel mViewModel;


    public Page4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Page4.
     */
    // TODO: Rename and change types and number of parameters
    public static Page4 newInstance(String param1, String param2) {
        Page4 fragment = new Page4();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mViewModel = new ViewModelProvider(requireActivity()).get(PageViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_page4, container, false);

        btn_done = view.findViewById(R.id.btn_done);
        btn_edit = view.findViewById(R.id.btn_edit);
        btn_cancel = view.findViewById(R.id.btn_cancel);

        for (int x = 0; x < editTexts.length; x++) {
            editTexts[x] = new EditText(getContext());
        }
        for (int x = 0; x < setences.length; x++) {
            setences[x] = new TextView(getContext());
        }
        for (int x = 0; x < icons.length; x++) {
            icons[x] = new ImageView(getContext());
        }

        setences[0] = view.findViewById(R.id.water_sentence);
        setences[1] = view.findViewById(R.id.water_sentence2);
        setences[2] = view.findViewById(R.id.light_sentence);
        setences[3] = view.findViewById(R.id.light_sentence2);
        setences[4] = view.findViewById(R.id.gas_sentence);
        setences[5] = view.findViewById(R.id.gas_sentence2);
        setences[6] = view.findViewById(R.id.petrol_sentence);
        setences[7] = view.findViewById(R.id.petrol_sentence2);
        setences[8] = view.findViewById(R.id.family_sentence);
        setences[9] = view.findViewById(R.id.home_sentence);
        setences[10] = view.findViewById(R.id.txt_form);
        icons[0] = view.findViewById(R.id.water);
        icons[1] = view.findViewById(R.id.light);
        icons[2] = view.findViewById(R.id.gas);
        icons[3] = view.findViewById(R.id.petrol);
        icons[4] = view.findViewById(R.id.family);
        icons[5] = view.findViewById(R.id.home);
        linearLayout = view.findViewById(R.id.linearLayout);

        formSpinner = (Spinner) view.findViewById(R.id.spinnerForm);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        formSpinner.setAdapter(adapter);
        formSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                try {

                    loadbill(mViewModel.getBills().getValue().get(position));
                } catch (java.lang.IndexOutOfBoundsException e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
      //  mViewModel.setBills(new ArrayList<Bill>());
        //  loadfirebase();


        editTexts[0] = view.findViewById(R.id.water_billData);
        editTexts[1] = view.findViewById(R.id.light_billData);
        editTexts[2] = view.findViewById(R.id.gas_billData);
        editTexts[3] = view.findViewById(R.id.petrol_billData);
        editTexts[4] = view.findViewById(R.id.water_data2);
        editTexts[5] = view.findViewById(R.id.light_data2);
        editTexts[6] = view.findViewById(R.id.gas_data2);
        editTexts[7] = view.findViewById(R.id.petrol_data2);
        editTexts[8] = view.findViewById(R.id.house_billData);
        editTexts[9] = view.findViewById(R.id.home_billData);
        btn_newForm = view.findViewById(R.id.btn_newForm);
        fielDisabled();

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBill();


            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldEnabled();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                newform = false;
                fielDisabled();
                if (mViewModel.getBills().getValue().size() == 0) {
                    initialhide();
                }
                try {
                    loadbill(mViewModel.getBills().getValue().get(formSpinner.getSelectedItemPosition()));
                    Log.d(TAG, formSpinner.getSelectedItemPosition() + "");
                } catch (java.lang.IndexOutOfBoundsException e) {
                }


            }
        });
        btn_newForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialshow();
                fieldEnabled();
                newform = true;
                for (int i = 0; i < editTexts.length; i++) {
                    editTexts[i].getText().clear();
                }

            }
        });

        mViewModel.getLoaded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loaded) {
                if (loaded)
                {
                    loadSpinner();
                }else{
                    initialhide();
                }
            }
        });
        Log.d("My App","Page4created");
        return view;
    }

    private void loadbill(Bill bill) {
        editTexts[0].setText(bill.getWater() + "");
        editTexts[1].setText(bill.getLight() + "");
        editTexts[2].setText(bill.getGas() + "");
        editTexts[3].setText(bill.getPetrol() + "");
        editTexts[4].setText(bill.getWater2() + "");
        editTexts[5].setText(bill.getLight2() + "");
        editTexts[6].setText(bill.getGas2() + "");
        editTexts[7].setText(bill.getPetrol2() + "");
        editTexts[8].setText(bill.getHouse() + "");
        editTexts[9].setText(bill.getHome() + "");

    }


    public void createBill() {

        String water = editTexts[0].getText().toString().trim();
        String light = editTexts[1].getText().toString().trim();
        String gas = editTexts[2].getText().toString().trim();
        String petrol = editTexts[3].getText().toString().trim();
        String water2 = editTexts[4].getText().toString().trim();
        String light2 = editTexts[5].getText().toString().trim();
        String gas2 = editTexts[6].getText().toString().trim();
        String petrol2 = editTexts[7].getText().toString().trim();
        String house = editTexts[8].getText().toString().trim();
        String home = editTexts[9].getText().toString().trim();
        try {
            if (newform) {
                Bill bill = new Bill(Float.parseFloat(water), Float.parseFloat(light), Float.parseFloat(gas), Float.parseFloat(petrol), Float.parseFloat(water2), Float.parseFloat(light2), Float.parseFloat(gas2), Float.parseFloat(petrol2), Integer.parseInt(house), Float.parseFloat(home), FirebaseAuth.getInstance().getCurrentUser().getUid(), mViewModel.getBills().getValue().size());
                //     Bill bill =new Bill((float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (int)Math.random()*20, (float)Math.random()*20, FirebaseAuth.getInstance().getCurrentUser().getUid(), mViewModel.getBills().getValue().size());
                mViewModel.addtofirebase(bill);
                mViewModel.getBills().getValue().add(bill);
                mViewModel.setBills(mViewModel.getBills().getValue());
                adapter.add(months[(mViewModel.getBills().getValue().size() - 1) % 12]);
                adapter.notifyDataSetChanged();
                formSpinner.setSelection(mViewModel.getBills().getValue().size() - 1);
                newform = false;
            } else {
                Bill bill = new Bill(Float.parseFloat(water), Float.parseFloat(light), Float.parseFloat(gas), Float.parseFloat(petrol), Float.parseFloat(water2), Float.parseFloat(light2), Float.parseFloat(gas2), Float.parseFloat(petrol2), Integer.parseInt(house), Float.parseFloat(home), FirebaseAuth.getInstance().getCurrentUser().getUid(), formSpinner.getSelectedItemPosition());
                //  Bill bill =new Bill((float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (float)Math.random()*20, (int)Math.random()*20, (float)Math.random()*20), Float.parseFloat(home), FirebaseAuth.getInstance().getCurrentUser().getUid());
                mViewModel.updateFirebase(bill);
                mViewModel.getBills().getValue().set(bill.getIndex(), bill);
                mViewModel.setBills(mViewModel.getBills().getValue());
            }
            Toast.makeText(getActivity(), getString(R.string.createform), Toast.LENGTH_LONG).show();
            fielDisabled();

        } catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.formerror), Toast.LENGTH_LONG).show();
        }
    }

    public void fieldEnabled() {
        btn_done.setVisibility(VISIBLE);
        btn_cancel.setVisibility(VISIBLE);
        btn_edit.setVisibility(GONE);
        btn_newForm.setVisibility(GONE);
        linearLayout.setVisibility(GONE);
        for (int i = 0; i < editTexts.length; i++) {
            editTexts[i].setEnabled(true);
        }
    }

    public void fielDisabled() {
        btn_done.setVisibility(GONE);
        btn_cancel.setVisibility(GONE);
        btn_edit.setVisibility(VISIBLE);
        linearLayout.setVisibility(VISIBLE);
        btn_newForm.setVisibility(VISIBLE);
        for (int i = 0; i < editTexts.length; i++) {
            editTexts[i].setEnabled(false);
        }


    }

    private void initialhide() {
        btn_edit.setVisibility(GONE);
        for (TextView s : setences) {
            s.setVisibility(GONE);

        }
        for (ImageView s : icons) {
            s.setVisibility(GONE);

        }
        for (EditText s : editTexts) {
            s.setVisibility(GONE);

        }
        linearLayout.setVisibility(GONE);
    }

    private void initialshow() {
        //   btn_edit.setVisibility(V);
        for (TextView s : setences) {
            s.setVisibility(VISIBLE);
        }
        for (ImageView s : icons) {
            s.setVisibility(VISIBLE);
        }
        for (EditText s : editTexts) {
            s.setVisibility(VISIBLE);
        }
        linearLayout.setVisibility(VISIBLE);
    }

    public void loadSpinner() {
        Log.d("My App", mViewModel.getBills().getValue().size() + "postload");
        for (int x = 0; x <= mViewModel.getBills().getValue().size() - 1; x++) {
            adapter.insert(months[x % 12], x);
            formSpinner.setSelection(mViewModel.getBills().getValue().size() - 1);
        }
        adapter.notifyDataSetChanged();
    }
}
