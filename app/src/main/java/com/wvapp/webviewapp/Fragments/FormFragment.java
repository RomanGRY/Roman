package com.wvapp.webviewapp.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wvapp.webviewapp.DB.Person;
import com.wvapp.webviewapp.DB.PersonViewModel;
import com.wvapp.webviewapp.R;

public class FormFragment extends Fragment {

    private View formFragmentView;
    private PersonViewModel personViewModel;
    private TextView nameInput, emailInput, phoneInput;
    private AutoCompleteTextView codeInput;
    private Button startBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        formFragmentView= inflater.inflate(R.layout.fragment_form,container,false);
        personViewModel= ViewModelProviders.of(this).get(PersonViewModel.class);
       initFields();
       startBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String name=nameInput.getText().toString();
               String email=emailInput.getText().toString();
               String phone=codeInput.getText().toString()+phoneInput.getText().toString();
               if (name.trim().isEmpty()||email.trim().isEmpty()||phoneInput.getText().toString().trim().isEmpty()){
                   Toast.makeText(getContext(), "Please, complete all the fields...", Toast.LENGTH_SHORT).show();
               }
               else {
                   personViewModel.insert(new Person(name,email,phone));
                   AppCompatActivity activity=(AppCompatActivity) view.getContext();
                   activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WebVFragment()).commit();
               }
           }
       });

        return formFragmentView;
    }

    private void initFields(){
        nameInput=formFragmentView.findViewById(R.id.name_input_field);
        emailInput=formFragmentView.findViewById(R.id.email_input_field);
        phoneInput=formFragmentView.findViewById(R.id.phone_input_field);
        codeInput=formFragmentView.findViewById(R.id.country_digits_field);
        startBtn=formFragmentView.findViewById(R.id.start_button);
    }
}