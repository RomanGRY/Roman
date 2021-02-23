package com.wvapp.webviewapp.DB;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.wvapp.webviewapp.R;

public class PersonAdapter extends ListAdapter<Person,PersonAdapter.PersonHolder> {

    public PersonAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Person> DIFF_CALLBACK=new DiffUtil.ItemCallback<Person>() {
        @Override
        public boolean areItemsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem.getName().equals(newItem.getName())&&
                    oldItem.getEmail().equals(newItem.getEmail())&&
                    oldItem.getPhoneNumber().equals(newItem.getPhoneNumber());
        }
    };

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View personView= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_person,parent,false);
        return new PersonHolder(personView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
        Person curPerson=getItem(position);
        holder.name.setText(curPerson.getName());
        holder.email.setText(curPerson.getEmail());
        holder.phoneNumber.setText(curPerson.getPhoneNumber());
    }

    public Person getPersonAt(int position){
        return getItem(position);
    }

    class PersonHolder extends RecyclerView.ViewHolder {

        private TextView name, email, phoneNumber;
        public PersonHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_text);
            email=itemView.findViewById(R.id.email_text);
            phoneNumber=itemView.findViewById(R.id.phone_text);
        }
    }
}
