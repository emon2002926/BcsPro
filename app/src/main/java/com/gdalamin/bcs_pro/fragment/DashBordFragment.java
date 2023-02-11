package com.gdalamin.bcs_pro.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gdalamin.bcs_pro.R;
import com.gdalamin.bcs_pro.adapter.resultAdapter;
import com.gdalamin.bcs_pro.modelClass.resultModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashBordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private  static final String url="http://192.168.0.104/api2/getResult.php?userId=01881492164";
    RecyclerView recview;

    public DashBordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashBordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashBordFragment newInstance(String param1, String param2) {
        DashBordFragment fragment = new DashBordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_bord, container, false);



        recview=view.findViewById(R.id.recview);




        processdata();


        return view;

    }

    public void processdata()
    {


        // Todo got the api url

        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder builder=new GsonBuilder();
                Gson gson=builder.create();
                resultModel data[]=gson.fromJson(response,resultModel[].class);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()
                        ,LinearLayoutManager.VERTICAL,false);

                recview.setLayoutManager(linearLayoutManager);
                resultAdapter adapter=new resultAdapter(data);
                recview.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }
        );


        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);

    }
}