package com.example.musicsearcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    String BASE_URL="https://itunes.apple.com/search?term=";
    String query;
    List<Result> result;
    RecyclerView recyclerView;
    EditText editSearch;
    ImageView btnSearch;
    TextView textError;
    ProgressDialog progressDialog;
    HashMap<String, List<Result>> GroupedResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(); //Initialize views

        //OnclickListener for search
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userInput=editSearch.getText().toString();

                if(userInput.equals(""))
                    Toast.makeText(MainActivity.this, "Enter Music Name", Toast.LENGTH_SHORT).show();
                else
                {
                    progressDialog.show();
                    textError.setVisibility(View.GONE);  //textView for showing error if Result Not found
                    editSearch.setText("");
                    modifyInput(userInput); //In this function we can change user input to URL form ex: honey singh is changed to honey+singh
                    getData();//this is used to get data from API using volley library

                }

            }
        });


    }

    void init()
    {
        editSearch=findViewById(R.id.editSearch);
        btnSearch=findViewById(R.id.btnSearch);
        textError=findViewById(R.id.textError);
        recyclerView=findViewById(R.id.rView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");

    }

    void modifyInput(String str)
    {
       char[] temp=str.toCharArray();

       for(int i=0;i<str.length();i++)
       {
           if(str.charAt(i)==' ')
                temp[i]='+';
       }

       query=String.valueOf(temp);
    }

    void getData()
    {
        final Gson gson = new Gson();
        RequestQueue queue = Volley.newRequestQueue(this);


        String url= BASE_URL+query+"&entity=musicTrack&limit=25";
        StringRequest request= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Model model= gson.fromJson(response,Model.class);

                result= model.getResults();
                progressDialog.dismiss();
                if(result.size()==0)
                {
                    textError.setVisibility(View.VISIBLE);
                }

                GroupedResult=getGroupedData(result);//This function changes ort list of Result into mapping of artistName and its related data

                List<ListItem> finalDataForRecyclerView = new ArrayList<>(); //Making list with help of map made using getGroupedData

                for (String date : GroupedResult.keySet()) {
                    HeadItem dateItem = new HeadItem();
                    dateItem.setHeader(date);
                    finalDataForRecyclerView.add(dateItem);

                    for (Result pojoOfJsonArray : GroupedResult.get(date)) {
                        GeneralItem generalItem = new GeneralItem();
                        generalItem.setGeneralItem(pojoOfJsonArray);
                        finalDataForRecyclerView.add(generalItem);
                    }
                }

               recyclerView.setAdapter(new GroupedListRecyclerAdapter(finalDataForRecyclerView));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(request);

    }

    HashMap<String, List<Result>>getGroupedData(List<Result> result)
    {
        HashMap<String, List<Result>> groupedHashMap = new HashMap<>();

        for(Result singleResult : result) {

            String hashMapKey = singleResult.getArtistName();

            if(groupedHashMap.containsKey(hashMapKey)) {

                groupedHashMap.get(hashMapKey).add(singleResult);
            } else {

                List<Result> list = new ArrayList<>();
                list.add(singleResult);
                groupedHashMap.put(hashMapKey, list);
            }
        }

        return groupedHashMap;
    }
}