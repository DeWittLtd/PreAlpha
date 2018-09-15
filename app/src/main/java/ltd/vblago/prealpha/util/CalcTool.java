package ltd.vblago.prealpha.util;

import java.text.DecimalFormat;

import ltd.vblago.prealpha.model.Point;

public class CalcTool {

    private Point point1;
    private Point point2;
    private static String unit = "meters";
    private static int zone = 5; // zone 5% to left and right

    public CalcTool(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public String getStrBearing(){
        return String.valueOf(Math.round(getBearing())) + "°";
    }

    public String getStrDistance(){
        return new DecimalFormat("##.#").format(getDistance()) + "m";
    }

    public double getBearing() {
        double longDiff = point2.getLongitude() - point1.getLongitude();
        double y = Math.sin(longDiff) * Math.cos(point2.getLatitude());
        double x = Math.cos(point1.getLatitude()) * Math.sin(point2.getLatitude()) - Math.sin(point1.getLatitude()) * Math.cos(point2.getLatitude()) * Math.cos(longDiff);
        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }

    public double getDistance() {
        double theta = point1.getLongitude() - point2.getLongitude();
        double dist = Math.sin(deg2rad(point1.getLatitude())) * Math.sin(deg2rad(point2.getLatitude())) + Math.cos(deg2rad(point1.getLatitude())) * Math.cos(deg2rad(point2.getLatitude())) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        switch (unit) {
            case "K":  // kilometers
                dist = dist * 1.609344;
                break;
            case "N":  // natural miles
                dist = dist * 0.8684;
                break;
            case "meters":
                dist = dist * 1.609344 * 1000;
                break;
        }
        return (dist); // default miles
    }

    public static boolean isZone(int b1, int b2) {
        int x1 = b1 - 360 * zone / 100;
        int x2 = b1 + 360 * zone / 100;

        return (b2 >= x1 && b2 <= x2) || (b2 > x2 && b2 >= x1 + 360);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }
}
