package nl.graaf.randstadrunners.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import nl.graaf.randstadrunners.R;
import nl.graaf.randstadrunners.adapters.EventsListAdapter;
import nl.graaf.randstadrunners.activities.DashboardDrawerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsListFragment extends Fragment {

    private ListView events_list;

    private static final String ARG_SECTION_NUMBER = "3";

    public EventsListFragment() {
        // Required empty public constructor
    }

    public static EventsListFragment newInstance(int sectionNumber) {
        EventsListFragment fragment = new EventsListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i <20; ++i) {
            list.add("Event " + i);
        }

        events_list = (ListView) view.findViewById(R.id.events_list);

        EventsListAdapter adapter = new EventsListAdapter(getActivity(), R.layout.list_item_events, list);
        events_list.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity != null){
            ((DashboardDrawerActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
