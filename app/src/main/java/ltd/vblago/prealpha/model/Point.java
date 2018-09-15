package ltd.vblago.prealpha.model;

import java.text.DecimalFormat;

public class Point {
    private float longitude;
    private float latitude;

    public Point(){
        longitude = 0;
        latitude = 0;
    }

    public Point(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = (float) longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = (float) latitude;
    }

    public String getStrParams() {
        return new DecimalFormat("##.#####").format(longitude) + " " + new DecimalFormat("##.#####").format(latitude);
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public Point copyPoint(){
        return new Point(longitude, latitude);
    }

    public boolean isEmpty(){
        return longitude == 0 && latitude == 0;
    }
}
