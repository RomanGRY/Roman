package com.wvapp.webviewapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wvapp.webviewapp.DB.DaggerPersonComponent;
import com.wvapp.webviewapp.DB.Person;
import com.wvapp.webviewapp.DB.PersonAdapter;
import com.wvapp.webviewapp.DB.PersonComponent;
import com.wvapp.webviewapp.DB.PersonViewModel;
import com.wvapp.webviewapp.MainActivity;
import com.wvapp.webviewapp.R;

import java.util.List;

import javax.inject.Inject;

import dagger.internal.DaggerCollections;


public class VisitorsListFragment extends Fragment {

    private View formFragmentView;
    private PersonViewModel personViewModel;
    private RecyclerView recyclerView;
    private PersonAdapter personAdapter;
    private FloatingActionButton fabForm, fabView;
    private  AppCompatActivity activity;
    private MainActivity mainActivity;
    @Inject
    ViewModelProvider.Factory viewModelProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        formFragmentView=inflater.inflate(R.layout.fragment_visitors_list, container, false);

        recyclerView=formFragmentView.findViewById(R.id.recycler_view);
        fabForm=formFragmentView.findViewById(R.id.back_to_form);
        fabView=formFragmentView.findViewById(R.id.back_to_web);
        activity=(AppCompatActivity)getContext();
        PersonComponent personComponent= DaggerPersonComponent.create();
        personComponent.inject(mainActivity);

        fabForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FormFragment()).commit();
            }
        });
        fabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WebVFragment()).commit();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        personAdapter=new PersonAdapter();

        personViewModel= ViewModelProviders.of(this,viewModelProvider).get(PersonViewModel.class);
        personViewModel.getAllPersons().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> people) {
                personAdapter.submitList(people);
            }
        });
        recyclerView.setAdapter(personAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                personViewModel.delete(personAdapter.getPersonAt(viewHolder.getAdapterPosition()));
                Toast.makeText(activity, "Visitor Deleted !", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        return formFragmentView;
    }
}