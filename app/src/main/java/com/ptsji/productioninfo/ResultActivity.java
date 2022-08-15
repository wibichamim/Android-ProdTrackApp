package com.ptsji.productioninfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.ptsji.productioninfo.APIService.Adapter.TrackListAdapter;
import com.ptsji.productioninfo.APIService.GetterSetter.TrackListGetSet;
import com.ptsji.productioninfo.APIService.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    TextView code, wos,sku, qty, prodPlan, prodName, color, component, order, production, balance, grade, size;
    RecyclerView recyclerView;
    ImageView back;
    Context context = this;
    String id;
    ArrayList<TrackListGetSet> historyArrayList = new ArrayList<>();
    TrackListAdapter recyclerAdapter = new TrackListAdapter(historyArrayList,context);
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        code = findViewById(R.id.code);
        wos = findViewById(R.id.wos);
        sku = findViewById(R.id.sku);
        qty = findViewById(R.id.qty);
        grade = findViewById(R.id.grade);
        size = findViewById(R.id.size);
        prodPlan = findViewById(R.id.prod_plan);
        prodName = findViewById(R.id.prod_name);
        color = findViewById(R.id.color);
        component = findViewById(R.id.component);
        order = findViewById(R.id.order);
        production = findViewById(R.id.production);
        balance = findViewById(R.id.balance);
        back = findViewById(R.id.back);
        recyclerView = findViewById(R.id.history_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        Intent startingIntent = getIntent();

        if (startingIntent != null) {
            id = startingIntent.getStringExtra("id");
            code.setText(id);
        }
        AndroidNetworking.initialize(getApplicationContext());
        checkQR();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkQR() {
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memproses ...");
        showDialog();
        AndroidNetworking.post(Server.URL + "detail-qr")
                .addBodyParameter("qrcode",id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("STATUS");
                            if (status) {
                                String wosno = response.getString("WOSNO") + "- " + response.getString("LOTNO");
                                wos.setText(wosno);
                                grade.setText(response.getString("GRADE"));
                                sku.setText(response.getString("STYLENO"));
                                qty.setText(response.getString("QTY"));
                                size.setText(response.getString("SIZE"));
                                prodPlan.setText(response.getString("PLAN"));
                                prodName.setText(response.getString("NAME"));
                                color.setText(response.getString("COLOR"));
                                component.setText(response.getString("COMPONENT"));
                                order.setText(response.getString("ORDER"));
                                production.setText(response.getString("PRODUCTION"));
                                balance.setText(response.getString("BALANCE"));

                                JSONArray jsonArray = response.getJSONArray("TRACKING");
                                int index = jsonArray.length();
                                for (int i = 0; i < index; i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String title = jsonObject.getString("TITLE");
                                    String date = jsonObject.getString("SCAN_DATE");
                                    String time = jsonObject.getString("SCAN_TIME");
                                    String scan = jsonObject.getString("SCAN_BY");
                                    historyArrayList.add(new TrackListGetSet(title,date,time,scan));
                                    recyclerAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(context, response.getString("MESSAGE"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hideDialog();
                        Toast.makeText(context, anError.getErrorDetail(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }
}