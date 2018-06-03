package vyvital.maptest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Context mContext = this;
    private GoogleMap mMap;
    private LatLng point1 = new LatLng(32.060558, 34.777439);
    private LatLng point2 = new LatLng(32.060620, 34.773990);
    private LatLng point3 = new LatLng(32.073984, 34.775845);
    private LatLng point4 = new LatLng(32.074174, 34.791937);
    private LatLng point5 = new LatLng(32.060996, 34.791617);
    private LatLng point6 = new LatLng(32.061559, 34.787826);
    private LatLng point7 = new LatLng(32.070320, 34.788117);
    private LatLng point8 = new LatLng(32.070970, 34.777620);

    private Marker m1;
    private Marker m2;
    private Marker m3;
    private Marker m4;
    private Marker m5;
    private Marker m6;
    private Marker m7;
    private Marker m8;
    private Marker temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final List<LatLng> list = new ArrayList<>();
        list.add(point1);
        list.add(point2);
        list.add(point3);
        list.add(point4);
        list.add(point5);
        list.add(point6);
        list.add(point7);
        list.add(point8);
        m1 = mMap.addMarker(new MarkerOptions().position(point1).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).draggable(true));
        m2 = mMap.addMarker(new MarkerOptions().position(point2).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).draggable(true));
        m3 = mMap.addMarker(new MarkerOptions().position(point3).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).draggable(true));
        m4 = mMap.addMarker(new MarkerOptions().position(point4).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).draggable(true));
        m5 = mMap.addMarker(new MarkerOptions().position(point5).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).draggable(true));
        m6 = mMap.addMarker(new MarkerOptions().position(point6).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).draggable(true));
        m7 = mMap.addMarker(new MarkerOptions().position(point7).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).draggable(true));
        m8 = mMap.addMarker(new MarkerOptions().position(point8).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).draggable(true));
        m1.setTag(1);
        m2.setTag(2);
        m3.setTag(3);
        m4.setTag(4);
        m5.setTag(5);
        m6.setTag(6);
        m7.setTag(7);
        m8.setTag(8);

        // PolygonOptions recOptions = new PolygonOptions().add(point1,point2,point3,point4,point5,point6,point6,point7,point8).strokeColor(Color.parseColor("#1919FF")).fillColor(Color.parseColor("#9999FF")).geodesic(true);
        final PolygonOptions recOptions = new PolygonOptions().addAll(list).strokeColor(Color.parseColor("#1919FF")).fillColor(Color.parseColor("#9999FF")).geodesic(true);

        final Polygon polygon = mMap.addPolygon(recOptions);
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                list.add(((int) marker.getTag()), new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));
                list.remove(((int) marker.getTag()) - 1);
                recOptions.addAll(list);
                polygon.setPoints(list);
                mMap.addPolygon(recOptions);
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (temp != null) {
                    temp.remove();
                }
                temp = mMap.addMarker(new MarkerOptions().position(latLng));
                /**
                 * Computes whether the given point lies inside the specified polygon.
                 * The polygon is always considered closed, regardless of whether the last point equals
                 * the first or not.
                 * Inside is defined as not containing the South Pole -- the South Pole is always outside.
                 * The polygon is formed of great circle segments if geodesic is true, and of rhumb
                 * (loxodromic) segments otherwise. (https://github.com/googlemaps/android-maps-utils/blob/master/library/src/com/google/maps/android/PolyUtil.java)
                 */
                if (PolyUtil.containsLocation(temp.getPosition(), list, false))
                temp.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                else {
                    temp.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    double minDist = PolyUtil.distanceToLine(temp.getPosition(),list.get(0),list.get(7));
                    int start = 7;
                    int end = 0;
                    double tempDist = 0;
                    for (int i = 0 ; i<7 ; i++)
                    {
                        tempDist = PolyUtil.distanceToLine(temp.getPosition(),list.get(i),list.get(i+1));
                        if (tempDist < minDist) {
                            start = i;
                            end = i+1;
                            minDist = tempDist;
                        }
                    }
                    minDist = Math.round(minDist);
                    Log.d("Distance Calculator","Shortest distance is "+minDist+" to P"+(start+1)+"P"+(end+1));
                    temp.setTitle("Shortest distance is "+minDist+" meters to P"+(start+1)+"P"+(end+1));
                    temp.showInfoWindow();
                }
            }
        });
        // Test the given KML files
//        try {
//            KmlLayer layer = new KmlLayer(mMap, R.raw.good, getApplicationContext());
//            layer.addLayerToMap();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
        //mMap.addMarker(new MarkerOptions().position(nirim).title("Marker in Nirim"));
        LatLng nirim = new LatLng(32.06278, 34.79069100000004);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nirim, 14), 5000, null);

    }
}
