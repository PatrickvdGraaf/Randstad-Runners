package nl.graaf.randstadrunners.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fmsirvent.ParallaxEverywhere.PEWImageView;

import java.util.List;

import nl.graaf.randstadrunners.R;

/**
*
 * Created by Patrick van de Graaf on 23/03/2015.
 *
 */

public class EventsListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private List<String> testObjects;

    static class ViewHolder {
        public TextView event_title;
        public PEWImageView event_banner;
        public TextView event_info;
    }

    public EventsListAdapter(Activity context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.testObjects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_item_events, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.event_title = (TextView) rowView.findViewById(R.id.event_title);
            viewHolder.event_banner = (PEWImageView) rowView.findViewById(R.id.event_banner);
            viewHolder.event_info = (TextView) rowView.findViewById(R.id.event_info);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String s = testObjects.get(position);
        holder.event_title.setText(s);

        return rowView;
    }
}
