package com.example.atlaspath;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atlaspath.BirthdaysFragment.OnListFragmentInteractionListener;
import com.example.atlaspath.models.Contact;

import java.util.ArrayList;


public class MyBirthdaysRecyclerViewAdapter extends RecyclerView.Adapter<MyBirthdaysRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Contact> contacts = new ArrayList<>();
    private final OnListFragmentInteractionListener mListener;

    public MyBirthdaysRecyclerViewAdapter(ArrayList<Contact> items, OnListFragmentInteractionListener listener) {
        if(items != null) {
            contacts.clear();
            contacts.addAll(items);
        }
        mListener = listener;
    }

    public ArrayList<Contact> contacts() {
        return contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_birthdays, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = contacts.get(position);
        holder.mIdView.setText(contacts.get(position).id);
        holder.mContentView.setText(contacts.get(position).getDisplayName());

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (contacts == null) ? 0 : contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Contact mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
