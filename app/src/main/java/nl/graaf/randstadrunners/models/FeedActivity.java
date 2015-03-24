package nl.graaf.randstadrunners.models;

import org.json.JSONArray;

/**
 * Created by Patrick van de Graaf on 24/03/2015.
 */
public class FeedActivity {
    private String type;
    private String start_time;
    private String total_distance;
    private String duration;
    private String source;
    private String entry_mode;
    private boolean has_map;
    private String uri;

    public FeedActivity(JSONArray json){

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getTotal_distance() {
        return total_distance;
    }

    public void setTotal_distance(String total_distance) {
        this.total_distance = total_distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEntry_mode() {
        return entry_mode;
    }

    public void setEntry_mode(String entry_mode) {
        this.entry_mode = entry_mode;
    }

    public boolean isHas_map() {
        return has_map;
    }

    public void setHas_map(boolean has_map) {
        this.has_map = has_map;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
