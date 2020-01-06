package com.example.atlaspath;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlaspath.models.Contact;
import com.example.atlaspath.models.User;
import com.example.atlaspath.singletons.UserSingleton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Observer;

public class BirthdaysFragment extends Fragment {

    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BirthdaysFragment() {
    }

    /**
     * User Repo Changed
     */
    private Observer userRepoChanged = (observable, o) -> {
        Log.d(MainActivity.class.getSimpleName(), "User Repository Updated with data");
        if (observable == null) {
            Log.d("MAIN", "Observable object is null =(");
        } else {
            User user = ((User) observable);
            // If user loaded, let's update our adapter list contacts
            if(user.isLoaded()) {
                MyBirthdaysRecyclerViewAdapter adapter = ((MyBirthdaysRecyclerViewAdapter)recyclerView.getAdapter());
                adapter.contacts().clear();
                adapter.contacts().addAll(user.contactManager.contacts);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add our observer to changes in our user doc
        User userRepo = UserSingleton.getInstance();
        userRepo.addObserver(userRepoChanged);
        userRepo.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_birthdays_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyBirthdaysRecyclerViewAdapter(null, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Contact contact);
    }
}
