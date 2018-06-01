package com.starlingsoftware.myexpendable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.expandablelistview.R;

public class MainActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    String s;
    int u = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.0.84/EcomHomeFurniture/api/Categories", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("dxfcgvbhj", response);
                int i, j;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                    int cou = jsonArray.length();

                    for (i = 0; i < jsonArray.length(); i++) {


                        JSONObject object = jsonArray.getJSONObject(i);

                        String catName = object.getString("CatName");
                        listDataHeader.add(catName);

                        JSONArray array = object.getJSONArray("Subcat");
                        List<String> top250 = new ArrayList<String>();
                        final List<String> lis = new ArrayList<String>();

                        for (j = 0; j < array.length(); j++) {
                            if (!(u == cou)) {
                                if (i == u) {
                                    JSONObject object1 = array.getJSONObject(j);
                                    top250.add(object1.getString("SubCatName"));
                                    listDataChild.put(listDataHeader.get(i), top250);

                                }
                            } else {
                                break;
                            }
                            // u++;


                        }
                        u++;
                        listAdapter.notifyDataSetChanged();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.v("dxfcgvbhj", error.toString());
            }
        });
        requestQueue.add(stringRequest);

/*



        {
            "Status": "Success",
                "Data": [
            {
                "Subcat": [
                {
                    "PKSubCatId": 1,
                        "SubCatName": "Bedsheet",
                        "PKCatId": 1,
                        "FKRowStatusId": 1,
                        "TimeStamp": "2018-02-14T13:27:21.083"
                },
                {
                    "PKSubCatId": 2,
                        "SubCatName": "BedCover",
                        "PKCatId": 1,
                        "FKRowStatusId": 1,
                        "TimeStamp": "2018-02-14T13:27:49.593"
                }
            ],
                "PKCatId": 1,
                    "CatName": "Beds",
                    "CatDescp": "Beds",
                    "PKGSTId": 1,
                    "Icon": "/Images/Icon/Beds.png",
                    "ActiveIcon": "/Images/ActiveIcon/active_Beds.png",
                    "File": null,
                    "FKRowStatusId": 1,
                    "TimeStamp": "2018-02-14T12:30:04.323"
            },
            {
                "Subcat": [
                {
                    "PKSubCatId": 4,
                        "SubCatName": "Window curtains",
                        "PKCatId": 3,
                        "FKRowStatusId": 1,
                        "TimeStamp": "2018-03-07T14:05:53.897"
                },
                {
                    "PKSubCatId": 5,
                        "SubCatName": "Door curtains",
                        "PKCatId": 3,
                        "FKRowStatusId": 1,
                        "TimeStamp": "2018-03-07T14:06:05.37"
                }
            ],
                "PKCatId": 3,
                    "CatName": "Curtains",
                    "CatDescp": "Curtains",
                    "PKGSTId": 1,
                    "Icon": "/Images/Icon/Curtains.png",
                    "ActiveIcon": "/Images/ActiveIcon/active_Curtains.png",
                    "File": null,
                    "FKRowStatusId": 1,
                    "TimeStamp": "2018-02-20T12:17:55.397"
            },
            {
                "Subcat": [
                {
                    "PKSubCatId": 7,
                        "SubCatName": "Chair",
                        "PKCatId": 4,
                        "FKRowStatusId": 1,
                        "TimeStamp": "2018-06-01T15:17:54.99"
                }
            ],
                "PKCatId": 4,
                    "CatName": "Cushions",
                    "CatDescp": "Cushions",
                    "PKGSTId": 1,
                    "Icon": "/Images/Icon/Cushions.png",
                    "ActiveIcon": "/Images/ActiveIcon/active_Cushions.png",
                    "File": null,
                    "FKRowStatusId": 1,
                    "TimeStamp": "2018-02-20T12:18:20.457"
            },
            {
                "Subcat": [
                {
                    "PKSubCatId": 6,
                        "SubCatName": "Carpets",
                        "PKCatId": 5,
                        "FKRowStatusId": 1,
                        "TimeStamp": "2018-03-23T13:30:02.343"
                }
            ],
                "PKCatId": 5,
                    "CatName": "Flooring",
                    "CatDescp": "Flooring",
                    "PKGSTId": 1,
                    "Icon": "/Images/Icon/Flooring.png",
                    "ActiveIcon": "/Images/ActiveIcon/active_Flooring.png",
                    "File": null,
                    "FKRowStatusId": 1,
                    "TimeStamp": "2018-02-20T12:18:45.4"
            },
            {
                "Subcat": [
                {
                    "PKSubCatId": 8,
                        "SubCatName": "Cotton",
                        "PKCatId": 6,
                        "FKRowStatusId": 1,
                        "TimeStamp": "2018-06-01T15:18:16.227"
                }
            ],
                "PKCatId": 6,
                    "CatName": "Bath Linens",
                    "CatDescp": "Bath Linens",
                    "PKGSTId": 1,
                    "Icon": "/Images/Icon/BathLinens.png",
                    "ActiveIcon": "/Images/ActiveIcon/active_BathLinens.png",
                    "File": null,
                    "FKRowStatusId": 1,
                    "TimeStamp": "2018-02-20T12:19:20.62"
            },
            {
                "Subcat": [
                {
                    "PKSubCatId": 9,
                        "SubCatName": "Body Lotion",
                        "PKCatId": 7,
                        "FKRowStatusId": 1,
                        "TimeStamp": "2018-06-01T15:18:29.773"
                }
            ],
                "PKCatId": 7,
                    "CatName": "Bath Essentials",
                    "CatDescp": "Bath Essentials",
                    "PKGSTId": 1,
                    "Icon": "/Images/Icon/BathEssentials.png",
                    "ActiveIcon": "/Images/ActiveIcon/active_BathEssentials.png",
                    "File": null,
                    "FKRowStatusId": 1,
                    "TimeStamp": "2018-02-20T12:19:46.147"
            }
    ]
        }*/
    }
}
