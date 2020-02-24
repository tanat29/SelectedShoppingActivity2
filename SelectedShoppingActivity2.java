package com.timeamp.buy_sell_item;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.timeamp.buy_sell_item.Fragment.MainActivity;
import com.timeamp.buy_sell_item.GroupAlert.GroupAlertActivity;
import com.timeamp.buy_sell_item.Model.Product;
import com.timeamp.buy_sell_item.Model.Selected_Product;
import com.timeamp.buy_sell_item.Model.Selected_Product_Calculate;
import com.timeamp.buy_sell_item.SharedPreferences.AppPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectedShoppingActivity2 extends AppCompatActivity {

    FrameLayout frame_layout1, frame_layout2, frame_layout3, frame_layout4, frame_layout5;
    TextView txt_id1, txt_id2, txt_id3, txt_id4, txt_id5;
    TextView txt_name1, txt_name2, txt_name3, txt_name4, txt_name5;
    TextView txt_cost1, txt_cost2, txt_cost3, txt_cost4, txt_cost5;
    TextView txt_amount1, txt_amount2, txt_amount3, txt_amount4, txt_amount5;
    TextView txt_unit1, txt_unit2, txt_unit3, txt_unit4, txt_unit5, txt_sum_cost;
    TextView txt_detail1, txt_detail2, txt_detail3, txt_detail4, txt_detail5;
    ImageView img_plus1, img_plus2, img_plus3, img_plus4, img_plus5;
    ImageView img_minus1, img_minus2, img_minus3, img_minus4, img_minus5;
    EditText edt_buyer;
    EditText edt_input_amount1, edt_input_amount2, edt_input_amount3, edt_input_amount4, edt_input_amount5;
    String User_id, PurchaseOrderId, Buyer_name;

    private List<Selected_Product_Calculate> DataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_shopping2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        frame_layout1 = findViewById(R.id.frame_layout1);
        frame_layout2 = findViewById(R.id.frame_layout2);
        frame_layout3 = findViewById(R.id.frame_layout3);
        frame_layout4 = findViewById(R.id.frame_layout4);
        frame_layout5 = findViewById(R.id.frame_layout5);
        txt_id1 = findViewById(R.id.txt_id1);
        txt_id2 = findViewById(R.id.txt_id2);
        txt_id3 = findViewById(R.id.txt_id3);
        txt_id4 = findViewById(R.id.txt_id4);
        txt_id5 = findViewById(R.id.txt_id5);
        txt_cost1 = findViewById(R.id.txt_cost1);
        txt_cost2 = findViewById(R.id.txt_cost2);
        txt_cost3 = findViewById(R.id.txt_cost3);
        txt_cost4 = findViewById(R.id.txt_cost4);
        txt_cost5 = findViewById(R.id.txt_cost5);
        txt_name1 = findViewById(R.id.txt_name1);
        txt_name2 = findViewById(R.id.txt_name2);
        txt_name3 = findViewById(R.id.txt_name3);
        txt_name4 = findViewById(R.id.txt_name4);
        txt_name5 = findViewById(R.id.txt_name5);
        txt_amount1 = findViewById(R.id.txt_amount1);
        txt_amount2 = findViewById(R.id.txt_amount2);
        txt_amount3 = findViewById(R.id.txt_amount3);
        txt_amount4 = findViewById(R.id.txt_amount4);
        txt_amount5 = findViewById(R.id.txt_amount5);
        txt_unit1 = findViewById(R.id.txt_unit1);
        txt_unit2 = findViewById(R.id.txt_unit2);
        txt_unit3 = findViewById(R.id.txt_unit3);
        txt_unit4 = findViewById(R.id.txt_unit4);
        txt_unit5 = findViewById(R.id.txt_unit5);
        txt_detail1 = findViewById(R.id.txt_detail1);
        txt_detail2 = findViewById(R.id.txt_detail2);
        txt_detail3 = findViewById(R.id.txt_detail3);
        txt_detail4 = findViewById(R.id.txt_detail4);
        txt_detail5 = findViewById(R.id.txt_detail5);
        img_plus1 = findViewById(R.id.img_plus1);
        img_plus2 = findViewById(R.id.img_plus2);
        img_plus3 = findViewById(R.id.img_plus3);
        img_plus4 = findViewById(R.id.img_plus4);
        img_plus5 = findViewById(R.id.img_plus5);
        img_minus1 = findViewById(R.id.img_minus1);
        img_minus2 = findViewById(R.id.img_minus2);
        img_minus3 = findViewById(R.id.img_minus3);
        img_minus4 = findViewById(R.id.img_minus4);
        img_minus5 = findViewById(R.id.img_minus5);
        edt_input_amount1 = findViewById(R.id.edt_input_amount1);
        edt_input_amount2 = findViewById(R.id.edt_input_amount2);
        edt_input_amount3 = findViewById(R.id.edt_input_amount3);
        edt_input_amount4 = findViewById(R.id.edt_input_amount4);
        edt_input_amount5 = findViewById(R.id.edt_input_amount5);
        txt_sum_cost = findViewById(R.id.txt_sum_cost);
        edt_buyer = findViewById(R.id.edt_buyer);

        AppPreferences appPreferences = new AppPreferences(SelectedShoppingActivity2.this);
        User_id = appPreferences.getStringPrefs(AppPreferences.KEY_USER_ID);

        Intent tt = getIntent();
        PurchaseOrderId = tt.getStringExtra("purchase_order_id");
        Buyer_name = tt.getStringExtra("buyer_name");
        edt_buyer.setText(Buyer_name);

        DataList = new ArrayList<>();
        Load_Data();
    }

    public void Arrow_Back(View view) {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(SelectedShoppingActivity2.this);
        dialog.setTitle("แจ้งเตือน");
        dialog.setIcon(R.drawable.logo);
        dialog.setCancelable(true);
        dialog.setMessage("คุณต้องการย้อนกลับไปหน้าเมนู และยกเลิกรายการที่เลือกไว้ ใช่หรือไม่?");
        dialog.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Ion.with(SelectedShoppingActivity2.this) // ดึงข้อมูลจังหวัดมา
                        .load("http://testingmyproject.com/Buy-Sell/purchase_order/selected_product_user_delete_post.php")
                        .setBodyParameter("user_id", User_id)
                        .asString()
                        .withResponse();

                Intent tt = new Intent(SelectedShoppingActivity2.this, MainActivity.class);
                tt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                tt.putExtra("count_confirm", "0");
                startActivity(tt);
            }
        });
        dialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void Load_Data() {
        DataList.clear();
        Ion.with(this) // ดึงข้อมูลจังหวัดมา
                .load("http://testingmyproject.com/Buy-Sell/selected_product/selected_product_list.php")
                .setBodyParameter("user_id", User_id)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        try {
                            String aa = result.getResult();
                            if (aa.equals("[]")) { // ถ้า aa = [] ไม่มีข้อมูล
                                Toast.makeText(SelectedShoppingActivity2.this, "คุณไม่มีสินค้าในตะกร้า กรุณาเลือกสินค้าก่อน", Toast.LENGTH_SHORT).show();
                                frame_layout1.setVisibility(View.GONE);
                                frame_layout2.setVisibility(View.GONE);
                                frame_layout3.setVisibility(View.GONE);
                                frame_layout4.setVisibility(View.GONE);
                                frame_layout5.setVisibility(View.GONE);
                                Intent tt = new Intent(getApplicationContext(), ShopActivity.class);
                                tt.putExtra("purchase_order_id", PurchaseOrderId);
                                startActivity(tt);
                                return;
                            }
                            JSONArray jsonArray = new JSONArray(aa);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                Selected_Product_Calculate data = new Selected_Product_Calculate(
                                        obj.getString("selected_product_id").trim(),
                                        obj.getString("time").trim(),
                                        obj.getString("product_id").trim(),
                                        obj.getString("product_type_id").trim(),
                                        obj.getString("product_code_id").trim(),
                                        obj.getString("name").trim(), //alert_amount
                                        obj.getString("cost").trim(),
                                        obj.getString("amount").trim(),
                                        obj.getString("detail").trim(),
                                        obj.getString("unit").trim(),
                                        obj.getString("photo").trim()
                                );
                                DataList.add(data);
                            }
                            new SelectedShopping2_Adapter(DataList);

                        } catch (Exception t) {
                            //Toast.makeText(SelectedShoppingActivity2.this, "ไม่สามารถโหลดข้อมูลได้", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private class SelectedShopping2_Adapter {
        @SuppressLint("SetTextI18n")
        public SelectedShopping2_Adapter(final List<Selected_Product_Calculate> dataList) {

            FrameLayout[] frame_layout_array = {frame_layout1, frame_layout2, frame_layout3, frame_layout4, frame_layout5};
            final TextView[] txt_id_array = {txt_id1, txt_id2, txt_id3, txt_id4, txt_id5};
            final TextView[] txt_name_array = {txt_name1, txt_name2, txt_name3, txt_name4, txt_name5};
            final TextView[] txt_amount_array = {txt_amount1, txt_amount2, txt_amount3, txt_amount4, txt_amount5};
            TextView[] txt_cost_array = {txt_cost1, txt_cost2, txt_cost3, txt_cost4, txt_cost5};
            TextView[] txt_unit_array = {txt_unit1, txt_unit2, txt_unit3, txt_unit4, txt_unit5};
            final EditText[] edt_amount_array = {edt_input_amount1, edt_input_amount2, edt_input_amount3, edt_input_amount4, edt_input_amount5};
            final ImageView[] img_minus_array = {img_minus1, img_minus2, img_minus3, img_minus4, img_minus5};
            final TextView[] txt_detail_array = {txt_detail1, txt_detail2, txt_detail3, txt_detail4, txt_detail5};

            //Toast.makeText(SelectedShoppingActivity2.this, String.valueOf(dataList.get(0).getSelected_product_id()), Toast.LENGTH_SHORT).show();

            if (DataList.size() == 1) {
                txt_cost1.setText(NumberFormatString(dataList.get(0).getCost()));
                txt_amount1.setText(dataList.get(0).getAmount());
                txt_id1.setText("1.");
                txt_name1.setText(dataList.get(0).getName());
                txt_unit1.setText(dataList.get(0).getUnit());

                if (!dataList.get(0).getDetail().equals("")) {
                    txt_detail1.setVisibility(View.VISIBLE);
                    txt_detail1.setText(dataList.get(0).getDetail());
                }

                frame_layout_array[1].setVisibility(View.GONE);
                frame_layout_array[2].setVisibility(View.GONE);
                frame_layout_array[3].setVisibility(View.GONE);
                frame_layout_array[4].setVisibility(View.GONE);
            } else if (DataList.size() == 2) {
                for (int i = 0; i < DataList.size(); i++) {
                    txt_id_array[i].setText(i + 1 + ".");
                    txt_name_array[i].setText(dataList.get(i).getName());
                    txt_amount_array[i].setText(dataList.get(i).getAmount());
                    txt_cost_array[i].setText(NumberFormatString(dataList.get(i).getCost()));
                    txt_unit_array[i].setText(dataList.get(i).getUnit());

                    if (!dataList.get(i).getDetail().equals("")) {
                        txt_detail_array[i].setVisibility(View.VISIBLE);
                        txt_detail_array[i].setText(dataList.get(i).getDetail());
                    }
                }

                for (int i = 2; i < 5; i++) {
                    frame_layout_array[i].setVisibility(View.GONE);
                }

            } else if (DataList.size() == 3) {
                for (int i = 0; i < DataList.size(); i++) {
                    txt_id_array[i].setText(i + 1 + ".");
                    txt_name_array[i].setText(dataList.get(i).getName());
                    txt_amount_array[i].setText(dataList.get(i).getAmount());
                    txt_cost_array[i].setText(NumberFormatString(dataList.get(i).getCost()));
                    txt_unit_array[i].setText(dataList.get(i).getUnit());

                    if (!dataList.get(i).getDetail().equals("")) {
                        txt_detail_array[i].setVisibility(View.VISIBLE);
                        txt_detail_array[i].setText(dataList.get(i).getDetail());
                    }
                }

                for (int i = 3; i < 5; i++) {
                    txt_detail_array[i].setVisibility(View.VISIBLE);
                    frame_layout_array[i].setVisibility(View.GONE);
                }
            } else if (DataList.size() == 4) {
                for (int i = 0; i < DataList.size(); i++) {
                    txt_id_array[i].setText(i + 1 + ".");
                    txt_name_array[i].setText(dataList.get(i).getName());
                    txt_amount_array[i].setText(dataList.get(i).getAmount());
                    txt_cost_array[i].setText(NumberFormatString(dataList.get(i).getCost()));
                    txt_unit_array[i].setText(dataList.get(i).getUnit());

                    if (!dataList.get(i).getDetail().equals("")) {
                        txt_detail_array[i].setVisibility(View.VISIBLE);
                        txt_detail_array[i].setText(dataList.get(i).getDetail());
                    }
                }

                for (int i = 4; i < 5; i++) {
                    frame_layout_array[i].setVisibility(View.GONE);
                }
            } else if (DataList.size() == 5) {
                for (int i = 0; i < DataList.size(); i++) {
                    txt_id_array[i].setText(i + 1 + ".");
                    txt_name_array[i].setText(dataList.get(i).getName());
                    txt_amount_array[i].setText(dataList.get(i).getAmount());
                    txt_cost_array[i].setText(NumberFormatString(dataList.get(i).getCost()));
                    txt_unit_array[i].setText(dataList.get(i).getUnit());

                    if (!dataList.get(i).getDetail().equals("")) {
                        txt_detail_array[i].setVisibility(View.VISIBLE);
                        txt_detail_array[i].setText(dataList.get(i).getDetail());
                    }
                }
            }

            for (int i = 0; i < DataList.size(); i++) {
                final int finalI = i;
                frame_layout_array[i].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
                        ad.setTitle("คำเตือน !!! ");
                        ad.setMessage("คุณแน่ใจว่าต้องการลบรายการสินค้านี้ ?");
                        ad.setIcon(android.R.drawable.btn_star_big_on);
                        ad.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Ion.with(SelectedShoppingActivity2.this) // ดึงข้อมูลจังหวัดมา
                                        .load("http://testingmyproject.com/Buy-Sell/selected_product/selected_delete_form_shop.php")
                                        .setBodyParameter("selected_product_id", String.valueOf(dataList.get(finalI).getSelected_product_id()))
                                        .asString()
                                        .withResponse();

                                Intent intent = new Intent(getApplicationContext(), SelectedShoppingActivity2.class);
                                intent.putExtra("purchase_order_id", PurchaseOrderId);
                                intent.putExtra("buyer_name", Buyer_name);
                                startActivity(intent);
                            }
                        });
                        ad.setNegativeButton("ยกเลิก", null); // กดยกเลิกไม่แสดงอะไร
                        ad.show();
                        return false;
                    }
                });

                img_minus_array[i].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
                        ad.setTitle("คำเตือน !!! ");
                        ad.setMessage("คุณแน่ใจว่าต้องการตั้งค่าจำนวนสินค้าเป็นศูนย์ ?");
                        ad.setIcon(android.R.drawable.btn_star_big_on);
                        ad.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Ion.with(SelectedShoppingActivity2.this) // ดึงข้อมูลจังหวัดมา
                                        .load("http://testingmyproject.com/Buy-Sell/selected_product/selected_update.php")
                                        .setBodyParameter("selected_product_id", DataList.get(finalI).getSelected_product_id())
                                        .setBodyParameter("amount", "0")
                                        .asString()
                                        .withResponse()
                                        .setCallback(new FutureCallback<Response<String>>() {
                                            @Override
                                            public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                                                try {
                                                    String aa = result.getResult();

                                                    String strStatusID = "0";
                                                    String strError = "Unknow Status!";

                                                    JsonObject result1 = new JsonParser().parse(aa).getAsJsonObject();

                                                    strStatusID = result1.get("StatusID").getAsString();
                                                    strError = result1.get("Error").getAsString();

                                                    if (strStatusID.equals("0")) { //ถ้าเป็น 0 ให้ pop up
                                                        android.app.AlertDialog.Builder ad = new android.app.AlertDialog.Builder(SelectedShoppingActivity2.this);
                                                        ad.setTitle(strError);
                                                        ad.setPositiveButton("Close", null);
                                                        ad.show();
                                                        return;
                                                    } else { // ถ้าไม่เป้น 0 ผ่าน
                                                        txt_amount_array[finalI].setText("0");
                                                        edt_amount_array[finalI].setText("");
                                                        Calculate_Sum();
                                                    }
                                                } catch (Exception e1) {
                                                    //Toast.makeText(SelectedShoppingActivity2.this, "เกิดข้อผิดพลาด !!!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });
                        ad.setNegativeButton("ยกเลิก", null); // กดยกเลิกไม่แสดงอะไร
                        ad.show();
                        return false;
                    }
                });
            }
            Calculate_Sum();
        }
    }

    public void img_plus1(View view) {
        String SelectedId = DataList.get(0).getSelected_product_id();

        if (edt_input_amount1.getText().toString().trim().isEmpty() || edt_input_amount1.getText().toString().trim().equals("0")) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาใส่จำนวนสินค้าก่อน", Toast.LENGTH_SHORT).show();
            return;
        }

        final Float Input = Float.parseFloat(edt_input_amount1.getText().toString().trim());
        final Float Amount = Float.parseFloat(txt_amount1.getText().toString().trim()) + Input;

        if (Amount <= 0) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาเช็คจำนวนสินค้าใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
            return;
        }

        Ion.with(this) // ดึงข้อมูลจังหวัดมา
                .load("http://testingmyproject.com/Buy-Sell/selected_product/selected_update.php")
                .setBodyParameter("selected_product_id", SelectedId)
                .setBodyParameter("amount", String.valueOf(Amount))
                .setBodyParameter("detail", txt_detail1.getText().toString() + "+" + edt_input_amount1.getText().toString())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        try {
                            String aa = result.getResult();

                            String strStatusID = "0";
                            String strError = "Unknow Status!";

                            JsonObject result1 = new JsonParser().parse(aa).getAsJsonObject();

                            strStatusID = result1.get("StatusID").getAsString();
                            strError = result1.get("Error").getAsString();

                            if (strStatusID.equals("0")) { //ถ้าเป็น 0 ให้ pop up
                                android.app.AlertDialog.Builder ad = new android.app.AlertDialog.Builder(SelectedShoppingActivity2.this);
                                ad.setTitle(strError);
                                ad.setPositiveButton("Close", null);
                                ad.show();
                                return;
                            } else { // ถ้าไม่เป้น 0 ผ่าน
                                txt_amount1.setText(String.format("%.2f", Amount));
                                txt_detail1.setText(txt_detail1.getText().toString() + "+" + edt_input_amount1.getText().toString());
                                txt_detail1.setVisibility(View.VISIBLE);
                                edt_input_amount1.setText("0");
                                Calculate_Sum();
                            }
                        } catch (Exception e1) {
                            //Toast.makeText(SelectedShoppingActivity2.this, "เกิดข้อผิดพลาด !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void img_minus1(View view) {
        String SelectedId = DataList.get(0).getSelected_product_id();

        if (edt_input_amount1.getText().toString().trim().isEmpty() || edt_input_amount1.getText().toString().trim().equals("0")) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาใส่จำนวนสินค้าก่อน", Toast.LENGTH_SHORT).show();
            return;
        }

        final Float Input = Float.parseFloat(edt_input_amount1.getText().toString().trim());
        final Float Amount = Float.parseFloat(txt_amount1.getText().toString().trim()) - Input;

        if (Amount <= 0) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาเช็คจำนวนสินค้าใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
            return;
        }

        Ion.with(this) // ดึงข้อมูลจังหวัดมา
                .load("http://testingmyproject.com/Buy-Sell/selected_product/selected_update.php")
                .setBodyParameter("selected_product_id", SelectedId)
                .setBodyParameter("amount", String.valueOf(Amount))
                .setBodyParameter("detail", txt_detail1.getText().toString() + "-" + edt_input_amount1.getText().toString())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        try {
                            String aa = result.getResult();

                            String strStatusID = "0";
                            String strError = "Unknow Status!";

                            JsonObject result1 = new JsonParser().parse(aa).getAsJsonObject();

                            strStatusID = result1.get("StatusID").getAsString();
                            strError = result1.get("Error").getAsString();

                            if (strStatusID.equals("0")) { //ถ้าเป็น 0 ให้ pop up
                                android.app.AlertDialog.Builder ad = new android.app.AlertDialog.Builder(SelectedShoppingActivity2.this);
                                ad.setTitle(strError);
                                ad.setPositiveButton("Close", null);
                                ad.show();
                                return;
                            } else { // ถ้าไม่เป้น 0 ผ่าน
                                txt_amount1.setText(String.format("%.2f", Amount));
                                txt_detail1.setText(txt_detail1.getText().toString() + "+" + edt_input_amount1.getText().toString());
                                txt_detail1.setVisibility(View.VISIBLE);
                                edt_input_amount1.setText("0");
                                Calculate_Sum();
                            }
                        } catch (Exception e1) {
                            //Toast.makeText(SelectedShoppingActivity2.this, "เกิดข้อผิดพลาด !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void img_plus2(View view) {

        String SelectedId = DataList.get(1).getSelected_product_id();

        if (edt_input_amount2.getText().toString().trim().isEmpty() || edt_input_amount2.getText().toString().trim().equals("0")) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาใส่จำนวนสินค้าก่อน", Toast.LENGTH_SHORT).show();
            return;
        }

        final Float Input = Float.parseFloat(edt_input_amount2.getText().toString().trim());
        final Float Amount = Float.parseFloat(txt_amount2.getText().toString().trim()) + Input;

        if (Amount <= 0) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาเช็คจำนวนสินค้าใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
            return;
        }

        Ion.with(this) // ดึงข้อมูลจังหวัดมา
                .load("http://testingmyproject.com/Buy-Sell/selected_product/selected_update.php")
                .setBodyParameter("selected_product_id", SelectedId)
                .setBodyParameter("amount", String.valueOf(Amount))
                .setBodyParameter("detail", txt_detail2.getText().toString() + "+" + edt_input_amount2.getText().toString())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        try {
                            String aa = result.getResult();

                            String strStatusID = "0";
                            String strError = "Unknow Status!";

                            JsonObject result1 = new JsonParser().parse(aa).getAsJsonObject();

                            strStatusID = result1.get("StatusID").getAsString();
                            strError = result1.get("Error").getAsString();

                            if (strStatusID.equals("0")) { //ถ้าเป็น 0 ให้ pop up
                                android.app.AlertDialog.Builder ad = new android.app.AlertDialog.Builder(SelectedShoppingActivity2.this);
                                ad.setTitle(strError);
                                ad.setPositiveButton("Close", null);
                                ad.show();
                                return;
                            } else { // ถ้าไม่เป้น 0 ผ่าน
                                txt_amount2.setText(String.format("%.2f", Amount));
                                txt_detail2.setText(txt_detail2.getText().toString() + "+" + edt_input_amount2.getText().toString());
                                txt_detail2.setVisibility(View.VISIBLE);
                                edt_input_amount1.setText("0");
                                Calculate_Sum();
                            }
                        } catch (Exception e1) {
                            //Toast.makeText(SelectedShoppingActivity2.this, "เกิดข้อผิดพลาด !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void img_minus2(View view) {
        String SelectedId = DataList.get(1).getSelected_product_id();

        if (edt_input_amount2.getText().toString().trim().isEmpty() || edt_input_amount2.getText().toString().trim().equals("0")) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาใส่จำนวนสินค้าก่อน", Toast.LENGTH_SHORT).show();
            return;
        }

        final Float Input = Float.parseFloat(edt_input_amount2.getText().toString().trim());
        final Float Amount = Float.parseFloat(txt_amount2.getText().toString().trim()) - Input;

        if (Amount <= 0) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาเช็คจำนวนสินค้าใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
            return;
        }

        Ion.with(this) // ดึงข้อมูลจังหวัดมา
                .load("http://testingmyproject.com/Buy-Sell/selected_product/selected_update.php")
                .setBodyParameter("selected_product_id", SelectedId)
                .setBodyParameter("amount", String.valueOf(Amount))
                .setBodyParameter("detail", txt_detail2.getText().toString() + "-" + edt_input_amount2.getText().toString())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        try {
                            String aa = result.getResult();

                            String strStatusID = "0";
                            String strError = "Unknow Status!";

                            JsonObject result1 = new JsonParser().parse(aa).getAsJsonObject();

                            strStatusID = result1.get("StatusID").getAsString();
                            strError = result1.get("Error").getAsString();

                            if (strStatusID.equals("0")) { //ถ้าเป็น 0 ให้ pop up
                                android.app.AlertDialog.Builder ad = new android.app.AlertDialog.Builder(SelectedShoppingActivity2.this);
                                ad.setTitle(strError);
                                ad.setPositiveButton("Close", null);
                                ad.show();
                                return;
                            } else { // ถ้าไม่เป้น 0 ผ่าน
                                txt_amount2.setText(String.format("%.2f", Amount));
                                txt_detail2.setText(txt_detail2.getText().toString() + "+" + edt_input_amount2.getText().toString());
                                txt_detail2.setVisibility(View.VISIBLE);
                                edt_input_amount1.setText("0");
                                Calculate_Sum();
                            }
                        } catch (Exception e1) {
                            //Toast.makeText(SelectedShoppingActivity2.this, "เกิดข้อผิดพลาด !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void img_plus3(View view) {
        String SelectedId = DataList.get(2).getSelected_product_id();

        if (edt_input_amount3.getText().toString().trim().isEmpty() || edt_input_amount3.getText().toString().trim().equals("0")) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาใส่จำนวนสินค้าก่อน", Toast.LENGTH_SHORT).show();
            return;
        }

        final Float Input = Float.parseFloat(edt_input_amount3.getText().toString().trim());
        final Float Amount = Float.parseFloat(txt_amount3.getText().toString().trim()) + Input;

        if (Amount <= 0) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาเช็คจำนวนสินค้าใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
            return;
        }

        Ion.with(this) // ดึงข้อมูลจังหวัดมา
                .load("http://testingmyproject.com/Buy-Sell/selected_product/selected_update.php")
                .setBodyParameter("selected_product_id", SelectedId)
                .setBodyParameter("amount", String.valueOf(Amount))
                .setBodyParameter("detail", txt_detail3.getText().toString() + "+" + edt_input_amount3.getText().toString())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        try {
                            String aa = result.getResult();

                            String strStatusID = "0";
                            String strError = "Unknow Status!";

                            JsonObject result1 = new JsonParser().parse(aa).getAsJsonObject();

                            strStatusID = result1.get("StatusID").getAsString();
                            strError = result1.get("Error").getAsString();

                            if (strStatusID.equals("0")) { //ถ้าเป็น 0 ให้ pop up
                                android.app.AlertDialog.Builder ad = new android.app.AlertDialog.Builder(SelectedShoppingActivity2.this);
                                ad.setTitle(strError);
                                ad.setPositiveButton("Close", null);
                                ad.show();
                                return;
                            } else { // ถ้าไม่เป้น 0 ผ่าน
                                txt_amount3.setText(String.format("%.2f", Amount));
                                txt_detail3.setText(txt_detail3.getText().toString() + "+" + edt_input_amount3.getText().toString());
                                txt_detail3.setVisibility(View.VISIBLE);
                                edt_input_amount3.setText("0");
                                Calculate_Sum();
                            }
                        } catch (Exception e1) {
                            //Toast.makeText(SelectedShoppingActivity2.this, "เกิดข้อผิดพลาด !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void img_minus3(View view) {
        String SelectedId = DataList.get(2).getSelected_product_id();

        if (edt_input_amount3.getText().toString().trim().isEmpty() || edt_input_amount3.getText().toString().trim().equals("0")) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาใส่จำนวนสินค้าก่อน", Toast.LENGTH_SHORT).show();
            return;
        }

        final Float Input = Float.parseFloat(edt_input_amount3.getText().toString().trim());
        final Float Amount = Float.parseFloat(txt_amount3.getText().toString().trim()) - Input;

        if (Amount <= 0) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาเช็คจำนวนสินค้าใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
            return;
        }

        Ion.with(this) // ดึงข้อมูลจังหวัดมา
                .load("http://testingmyproject.com/Buy-Sell/selected_product/selected_update.php")
                .setBodyParameter("selected_product_id", SelectedId)
                .setBodyParameter("amount", String.valueOf(Amount))
                .setBodyParameter("detail", txt_detail3.getText().toString() + "-" + edt_input_amount3.getText().toString())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        try {
                            String aa = result.getResult();

                            String strStatusID = "0";
                            String strError = "Unknow Status!";

                            JsonObject result1 = new JsonParser().parse(aa).getAsJsonObject();

                            strStatusID = result1.get("StatusID").getAsString();
                            strError = result1.get("Error").getAsString();

                            if (strStatusID.equals("0")) { //ถ้าเป็น 0 ให้ pop up
                                android.app.AlertDialog.Builder ad = new android.app.AlertDialog.Builder(SelectedShoppingActivity2.this);
                                ad.setTitle(strError);
                                ad.setPositiveButton("Close", null);
                                ad.show();
                                return;
                            } else { // ถ้าไม่เป้น 0 ผ่าน
                                txt_amount3.setText(String.format("%.2f", Amount));
                                txt_detail3.setText(txt_detail3.getText().toString() + "+" + edt_input_amount3.getText().toString());
                                txt_detail3.setVisibility(View.VISIBLE);
                                edt_input_amount3.setText("0");
                                Calculate_Sum();
                            }
                        } catch (Exception e1) {
                            //Toast.makeText(SelectedShoppingActivity2.this, "เกิดข้อผิดพลาด !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void img_plus4(View view) {
        String SelectedId = DataList.get(3).getSelected_product_id();

        if (edt_input_amount4.getText().toString().trim().isEmpty() || edt_input_amount4.getText().toString().trim().equals("0")) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาใส่จำนวนสินค้าก่อน", Toast.LENGTH_SHORT).show();
            return;
        }

        final Float Input = Float.parseFloat(edt_input_amount4.getText().toString().trim());
        final Float Amount = Float.parseFloat(txt_amount4.getText().toString().trim()) + Input;

        if (Amount <= 0) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาเช็คจำนวนสินค้าใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
            return;
        }

        Ion.with(this) // ดึงข้อมูลจังหวัดมา
                .load("http://testingmyproject.com/Buy-Sell/selected_product/selected_update.php")
                .setBodyParameter("selected_product_id", SelectedId)
                .setBodyParameter("amount", String.valueOf(Amount))
                .setBodyParameter("detail", txt_detail4.getText().toString() + "+" + edt_input_amount4.getText().toString())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        try {
                            String aa = result.getResult();

                            String strStatusID = "0";
                            String strError = "Unknow Status!";

                            JsonObject result1 = new JsonParser().parse(aa).getAsJsonObject();

                            strStatusID = result1.get("StatusID").getAsString();
                            strError = result1.get("Error").getAsString();

                            if (strStatusID.equals("0")) { //ถ้าเป็น 0 ให้ pop up
                                android.app.AlertDialog.Builder ad = new android.app.AlertDialog.Builder(SelectedShoppingActivity2.this);
                                ad.setTitle(strError);
                                ad.setPositiveButton("Close", null);
                                ad.show();
                                return;
                            } else { // ถ้าไม่เป้น 0 ผ่าน
                                txt_amount4.setText(String.format("%.2f", Amount));
                                txt_detail4.setText(txt_detail4.getText().toString() + "+" + edt_input_amount4.getText().toString());
                                txt_detail4.setVisibility(View.VISIBLE);
                                edt_input_amount3.setText("0");
                                Calculate_Sum();
                            }
                        } catch (Exception e1) {
                            //Toast.makeText(SelectedShoppingActivity2.this, "เกิดข้อผิดพลาด !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void img_minus4(View view) {
        String SelectedId = DataList.get(3).getSelected_product_id();
        if (edt_input_amount4.getText().toString().trim().isEmpty() || edt_input_amount4.getText().toString().trim().equals("0")) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาใส่จำนวนสินค้าก่อน", Toast.LENGTH_SHORT).show();
            return;
        }

        final Float Input = Float.parseFloat(edt_input_amount4.getText().toString().trim());
        final Float Amount = Float.parseFloat(txt_amount4.getText().toString().trim()) - Input;

        if (Amount <= 0) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาเช็คจำนวนสินค้าใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
            return;
        }

        Ion.with(this) // ดึงข้อมูลจังหวัดมา
                .load("http://testingmyproject.com/Buy-Sell/selected_product/selected_update.php")
                .setBodyParameter("selected_product_id", SelectedId)
                .setBodyParameter("amount", String.valueOf(Amount))
                .setBodyParameter("detail", txt_detail4.getText().toString() + "-" + edt_input_amount4.getText().toString())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        try {
                            String aa = result.getResult();

                            String strStatusID = "0";
                            String strError = "Unknow Status!";

                            JsonObject result1 = new JsonParser().parse(aa).getAsJsonObject();

                            strStatusID = result1.get("StatusID").getAsString();
                            strError = result1.get("Error").getAsString();

                            if (strStatusID.equals("0")) { //ถ้าเป็น 0 ให้ pop up
                                android.app.AlertDialog.Builder ad = new android.app.AlertDialog.Builder(SelectedShoppingActivity2.this);
                                ad.setTitle(strError);
                                ad.setPositiveButton("Close", null);
                                ad.show();
                                return;
                            } else { // ถ้าไม่เป้น 0 ผ่าน
                                txt_amount4.setText(String.format("%.2f", Amount));
                                txt_detail4.setText(txt_detail4.getText().toString() + "+" + edt_input_amount4.getText().toString());
                                txt_detail4.setVisibility(View.VISIBLE);
                                edt_input_amount3.setText("0");
                                Calculate_Sum();
                            }
                        } catch (Exception e1) {
                            //Toast.makeText(SelectedShoppingActivity2.this, "เกิดข้อผิดพลาด !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void img_plus5(View view) {
        String SelectedId = DataList.get(4).getSelected_product_id();

        if (edt_input_amount5.getText().toString().trim().isEmpty() || edt_input_amount5.getText().toString().trim().equals("0")) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาใส่จำนวนสินค้าก่อน", Toast.LENGTH_SHORT).show();
            return;
        }

        final Float Input = Float.parseFloat(edt_input_amount5.getText().toString().trim());
        final Float Amount = Float.parseFloat(txt_amount5.getText().toString().trim()) + Input;

        if (Amount <= 0) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาเช็คจำนวนสินค้าใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
            return;
        }

        Ion.with(this) // ดึงข้อมูลจังหวัดมา
                .load("http://testingmyproject.com/Buy-Sell/selected_product/selected_update.php")
                .setBodyParameter("selected_product_id", SelectedId)
                .setBodyParameter("amount", String.valueOf(Amount))
                .setBodyParameter("detail", txt_detail5.getText().toString() + "+" + edt_input_amount5.getText().toString())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        try {
                            String aa = result.getResult();

                            String strStatusID = "0";
                            String strError = "Unknow Status!";

                            JsonObject result1 = new JsonParser().parse(aa).getAsJsonObject();

                            strStatusID = result1.get("StatusID").getAsString();
                            strError = result1.get("Error").getAsString();

                            if (strStatusID.equals("0")) { //ถ้าเป็น 0 ให้ pop up
                                android.app.AlertDialog.Builder ad = new android.app.AlertDialog.Builder(SelectedShoppingActivity2.this);
                                ad.setTitle(strError);
                                ad.setPositiveButton("Close", null);
                                ad.show();
                                return;
                            } else { // ถ้าไม่เป้น 0 ผ่าน
                                txt_amount5.setText(String.format("%.2f", Amount));
                                txt_detail5.setText(txt_detail5.getText().toString() + "+" + edt_input_amount5.getText().toString());
                                txt_detail5.setVisibility(View.VISIBLE);
                                edt_input_amount3.setText("0");
                                Calculate_Sum();
                            }
                        } catch (Exception e1) {
                            //Toast.makeText(SelectedShoppingActivity2.this, "เกิดข้อผิดพลาด !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void img_minus5(View view) {
        String SelectedId = DataList.get(4).getSelected_product_id();

        if (edt_input_amount5.getText().toString().trim().isEmpty() || edt_input_amount5.getText().toString().trim().equals("0")) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาใส่จำนวนสินค้าก่อน", Toast.LENGTH_SHORT).show();
            return;
        }

        final Float Input = Float.parseFloat(edt_input_amount5.getText().toString().trim());
        final Float Amount = Float.parseFloat(txt_amount5.getText().toString().trim()) - Input;

        if (Amount <= 0) {
            Toast.makeText(SelectedShoppingActivity2.this, "กรุณาเช็คจำนวนสินค้าใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
            return;
        }

        Ion.with(this) // ดึงข้อมูลจังหวัดมา
                .load("http://testingmyproject.com/Buy-Sell/selected_product/selected_update.php")
                .setBodyParameter("selected_product_id", SelectedId)
                .setBodyParameter("amount", String.valueOf(Amount))
                .setBodyParameter("detail", txt_detail5.getText().toString() + "-" + edt_input_amount5.getText().toString())
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                        try {
                            String aa = result.getResult();

                            String strStatusID = "0";
                            String strError = "Unknow Status!";

                            JsonObject result1 = new JsonParser().parse(aa).getAsJsonObject();

                            strStatusID = result1.get("StatusID").getAsString();
                            strError = result1.get("Error").getAsString();

                            if (strStatusID.equals("0")) { //ถ้าเป็น 0 ให้ pop up
                                android.app.AlertDialog.Builder ad = new android.app.AlertDialog.Builder(SelectedShoppingActivity2.this);
                                ad.setTitle(strError);
                                ad.setPositiveButton("Close", null);
                                ad.show();
                                return;
                            } else { // ถ้าไม่เป้น 0 ผ่าน
                                txt_amount5.setText(String.format("%.2f", Amount));
                                txt_detail5.setText(txt_detail5.getText().toString() + "+" + edt_input_amount5.getText().toString());
                                txt_detail5.setVisibility(View.VISIBLE);
                                edt_input_amount3.setText("0");
                                Calculate_Sum();
                            }
                        } catch (Exception e1) {
                            //Toast.makeText(SelectedShoppingActivity2.this, "เกิดข้อผิดพลาด !!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void Add_Selected_Product(View view) {
        Intent tt = new Intent(SelectedShoppingActivity2.this, ShopActivity.class);
        tt.putExtra("buyer_name", edt_buyer.getText().toString().trim());
        tt.putExtra("purchase_order_id", PurchaseOrderId);
        startActivity(tt);
    }

    @SuppressLint("DefaultLocale")
    private void Calculate_Sum() {
        if (DataList.size() == 1) {
            Float Price1 = Float.parseFloat(txt_cost1.getText().toString().trim().replace(",", ""));
            Float Amount1 = Float.parseFloat(txt_amount1.getText().toString().trim().replace(",", ""));

            txt_sum_cost.setText(NumberFormatFloat(Price1 * Amount1));
            if (txt_sum_cost.getText().toString().equals(".00")) {
                txt_sum_cost.setText("0");
            }
        } else if (DataList.size() == 2) {
            Float Price1 = Float.parseFloat(txt_cost1.getText().toString().trim().replace(",", ""));
            Float Amount1 = Float.parseFloat(txt_amount1.getText().toString().trim().replace(",", ""));

            Float Price2 = Float.parseFloat(txt_cost2.getText().toString().trim().replace(",", ""));
            Float Amount2 = Float.parseFloat(txt_amount2.getText().toString().trim().replace(",", ""));

            txt_sum_cost.setText(NumberFormatFloat((Price1 * Amount1) + (Price2 * Amount2)));
            if (txt_sum_cost.getText().toString().equals(".00")) {
                txt_sum_cost.setText("0");
            }
        } else if (DataList.size() == 3) {
            Float Price1 = Float.parseFloat(txt_cost1.getText().toString().trim().replace(",", ""));
            Float Amount1 = Float.parseFloat(txt_amount1.getText().toString().trim().replace(",", ""));

            Float Price2 = Float.parseFloat(txt_cost2.getText().toString().trim().replace(",", ""));
            Float Amount2 = Float.parseFloat(txt_amount2.getText().toString().trim().replace(",", ""));

            Float Price3 = Float.parseFloat(txt_cost3.getText().toString().trim().replace(",", ""));
            Float Amount3 = Float.parseFloat(txt_amount3.getText().toString().trim().replace(",", ""));

            txt_sum_cost.setText(NumberFormatFloat((Price1 * Amount1) + (Price2 * Amount2) + (Price3 * Amount3)));
            if (txt_sum_cost.getText().toString().equals(".00")) {
                txt_sum_cost.setText("0");
            }
        } else if (DataList.size() == 4) {
            Float Price1 = Float.parseFloat(txt_cost1.getText().toString().trim().replace(",", ""));
            Float Amount1 = Float.parseFloat(txt_amount1.getText().toString().trim().replace(",", ""));

            Float Price2 = Float.parseFloat(txt_cost2.getText().toString().trim().replace(",", ""));
            Float Amount2 = Float.parseFloat(txt_amount2.getText().toString().trim().replace(",", ""));

            Float Price3 = Float.parseFloat(txt_cost3.getText().toString().trim().replace(",", ""));
            Float Amount3 = Float.parseFloat(txt_amount3.getText().toString().trim().replace(",", ""));

            Float Price4 = Float.parseFloat(txt_cost4.getText().toString().trim().replace(",", ""));
            Float Amount4 = Float.parseFloat(txt_amount4.getText().toString().trim().replace(",", ""));

            txt_sum_cost.setText(NumberFormatFloat((Price1 * Amount1) + (Price2 * Amount2) + (Price3 * Amount3) + (Price4 * Amount4)));
            if (txt_sum_cost.getText().toString().equals(".00")) {
                txt_sum_cost.setText("0");
            }
        } else if (DataList.size() == 5) {
            Float Price1 = Float.parseFloat(txt_cost1.getText().toString().trim().replace(",", ""));
            Float Amount1 = Float.parseFloat(txt_amount1.getText().toString().trim().replace(",", ""));

            Float Price2 = Float.parseFloat(txt_cost2.getText().toString().trim().replace(",", ""));
            Float Amount2 = Float.parseFloat(txt_amount2.getText().toString().trim().replace(",", ""));

            Float Price3 = Float.parseFloat(txt_cost3.getText().toString().trim().replace(",", ""));
            Float Amount3 = Float.parseFloat(txt_amount3.getText().toString().trim().replace(",", ""));

            Float Price4 = Float.parseFloat(txt_cost4.getText().toString().trim().replace(",", ""));
            Float Amount4 = Float.parseFloat(txt_amount4.getText().toString().trim().replace(",", ""));

            Float Price5 = Float.parseFloat(txt_cost5.getText().toString().trim().replace(",", ""));
            Float Amount5 = Float.parseFloat(txt_amount5.getText().toString().trim().replace(",", ""));

            txt_sum_cost.setText(NumberFormatFloat((Price1 * Amount1) + (Price2 * Amount2) + (Price3 * Amount3) + (Price4 * Amount4) + (Price5 * Amount5)));
            if (txt_sum_cost.getText().toString().equals(".00")) {
                txt_sum_cost.setText("0");
            }
        }
    }

    public void onBackPressed() {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(SelectedShoppingActivity2.this);
        dialog.setTitle("แจ้งเตือน");
        dialog.setIcon(R.drawable.logo);
        dialog.setCancelable(true);
        dialog.setMessage("คุณต้องการย้อนกลับไปหน้าเมนู และยกเลิกรายการที่เลือกไว้ ใช่หรือไม่?");
        dialog.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Ion.with(SelectedShoppingActivity2.this) // ดึงข้อมูลจังหวัดมา
                        .load("http://testingmyproject.com/Buy-Sell/purchase_order/selected_product_user_delete_post.php")
                        .setBodyParameter("user_id", User_id)
                        .asString()
                        .withResponse();

                Intent tt = new Intent(SelectedShoppingActivity2.this, MainActivity.class);
                tt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                tt.putExtra("count_confirm", "0");
                startActivity(tt);
            }
        });
        dialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void Check_Out(View view) {
        if (DataList.size() == 1) {
            final String Seller = edt_buyer.getText().toString().trim();
            if (edt_buyer.getText().toString().isEmpty()) {
                edt_buyer.setError("กรุณาใส่ชื่อคนซื้อก่อน !!!");
                edt_buyer.requestFocus();
                return;
            }

            if (txt_sum_cost.getText().toString().equals("0")) {
                Toast.makeText(SelectedShoppingActivity2.this, "กรุณาตรวจสอบรายการก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount1.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 1 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            final String Amount1 = txt_amount1.getText().toString().trim();
            final String product_id1 = DataList.get(0).getProduct_id().trim();
            final String product_type_id1 = DataList.get(0).getProduct_type_id().trim();
            Calendar c = Calendar.getInstance();
            int current_day = c.get(Calendar.DAY_OF_MONTH);
            int count_year = c.get(Calendar.YEAR) + 543;
            String Year = String.valueOf(count_year);
            String Month = new SimpleDateFormat("MM").format(new Date());
            String day2 = new SimpleDateFormat("dd").format(new Date());
            String PurchaseCodeId = "PAY" + Year.substring(2, 4) + Month + day2;

            String day = current_day + new SimpleDateFormat("/MM/").format(new Date()) + Year;
            String time = new SimpleDateFormat("HH:mm").format(new Date());

            Calendar cal = Calendar.getInstance();
            String week = String.valueOf(cal.get(Calendar.WEEK_OF_MONTH));
            String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
            String year = String.valueOf(cal.get(Calendar.YEAR));

            Intent tt = new Intent(SelectedShoppingActivity2.this, PaymentActivity.class);
            tt.putExtra("size", String.valueOf(DataList.size()));
            tt.putExtra("purchase_code_id", PurchaseCodeId);
            tt.putExtra("seller", Seller);
            tt.putExtra("product_id1", product_id1);
            tt.putExtra("product_id2", "");
            tt.putExtra("product_id3", "");
            tt.putExtra("product_id4", "");
            tt.putExtra("product_id5", "");
            tt.putExtra("product_type_id1", product_type_id1);
            tt.putExtra("product_type_id2", "");
            tt.putExtra("product_type_id3", "");
            tt.putExtra("product_type_id4", "");
            tt.putExtra("product_type_id5", "");
            tt.putExtra("amount1", Amount1);
            tt.putExtra("amount2", "");
            tt.putExtra("amount3", "");
            tt.putExtra("amount4", "");
            tt.putExtra("amount5", "");
            tt.putExtra("name1", String.valueOf(DataList.get(0).getName()));
            tt.putExtra("name2", "");
            tt.putExtra("name3", "");
            tt.putExtra("name4", "");
            tt.putExtra("name5", "");
            tt.putExtra("price1", txt_cost1.getText().toString().trim());
            tt.putExtra("price2", "");
            tt.putExtra("price3", "");
            tt.putExtra("price4", "");
            tt.putExtra("price5", "");
            tt.putExtra("unit1", String.valueOf(DataList.get(0).getUnit()));
            tt.putExtra("unit2", "");
            tt.putExtra("unit3", "");
            tt.putExtra("unit4", "");
            tt.putExtra("unit5", "");
            tt.putExtra("cost", txt_sum_cost.getText().toString().trim());
            tt.putExtra("time", time);
            tt.putExtra("day", day);
            tt.putExtra("week", week);
            tt.putExtra("month", month);
            tt.putExtra("year", year);
            tt.putExtra("user_id", User_id);
            tt.putExtra("purchase_order_id", PurchaseOrderId);
            startActivity(tt);
        } else if (DataList.size() == 2) {
            final String Seller = edt_buyer.getText().toString().trim();
            if (edt_buyer.getText().toString().isEmpty()) {
                edt_buyer.setError("กรุณาใส่ชื่อคนซื้อก่อน !!!");
                edt_buyer.requestFocus();
                return;
            }

            final String Amount1 = txt_amount1.getText().toString().trim();
            final String Amount2 = txt_amount2.getText().toString().trim();

            String[] Amount_Array = {Amount1, Amount2};
            for (int i = 0; i < Amount_Array.length; i++) {
                if (Amount_Array[i].isEmpty() || Amount_Array.equals("0")) {
                    Toast.makeText(SelectedShoppingActivity2.this, "กรุณาเพิ่มจำนวนสินค้าก่อนให้ครบก่อน", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            final String product_id1 = DataList.get(0).getProduct_id().trim();
            final String product_id2 = DataList.get(1).getProduct_id().trim();
            final String product_type_id1 = DataList.get(0).getProduct_type_id().trim();
            final String product_type_id2 = DataList.get(1).getProduct_type_id().trim();
            if (txt_sum_cost.getText().toString().equals("0")) {
                Toast.makeText(SelectedShoppingActivity2.this, "กรุณาตรวจสอบรายการก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount1.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 1 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount2.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 2 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            Calendar c = Calendar.getInstance();
            int current_day = c.get(Calendar.DAY_OF_MONTH);
            int count_year = c.get(Calendar.YEAR) + 543;
            String Year = String.valueOf(count_year);
            String Month = new SimpleDateFormat("MM").format(new Date());
            String day2 = new SimpleDateFormat("dd").format(new Date());
            String PurchaseCodeId = "PAY" + Year.substring(2, 4) + Month + day2;

            String day = current_day + new SimpleDateFormat("/MM/").format(new Date()) + Year;
            String time = new SimpleDateFormat("HH:mm").format(new Date());

            Calendar cal = Calendar.getInstance();
            String week = String.valueOf(cal.get(Calendar.WEEK_OF_MONTH));
            String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
            String year = String.valueOf(cal.get(Calendar.YEAR));

            Intent tt = new Intent(SelectedShoppingActivity2.this, PaymentActivity.class);
            tt.putExtra("size", String.valueOf(DataList.size()));
            tt.putExtra("purchase_code_id", PurchaseCodeId);
            tt.putExtra("seller", Seller);
            tt.putExtra("product_id1", product_id1);
            tt.putExtra("product_id2", product_id2);
            tt.putExtra("product_id3", "");
            tt.putExtra("product_id4", "");
            tt.putExtra("product_id5", "");
            tt.putExtra("product_type_id1", product_type_id1);
            tt.putExtra("product_type_id2", product_type_id2);
            tt.putExtra("product_type_id3", "");
            tt.putExtra("product_type_id4", "");
            tt.putExtra("product_type_id5", "");
            tt.putExtra("amount1", Amount1);
            tt.putExtra("amount2", Amount2);
            tt.putExtra("amount3", "");
            tt.putExtra("amount4", "");
            tt.putExtra("amount5", "");
            tt.putExtra("name1", String.valueOf(DataList.get(0).getName()));
            tt.putExtra("name2", String.valueOf(DataList.get(1).getName()));
            tt.putExtra("name3", "");
            tt.putExtra("name4", "");
            tt.putExtra("name5", "");
            tt.putExtra("price1", txt_cost1.getText().toString().trim());
            tt.putExtra("price2", txt_cost2.getText().toString().trim());
            tt.putExtra("price3", "");
            tt.putExtra("price4", "");
            tt.putExtra("price5", "");
            tt.putExtra("unit1", String.valueOf(DataList.get(0).getUnit()));
            tt.putExtra("unit2", String.valueOf(DataList.get(1).getUnit()));
            tt.putExtra("unit3", "");
            tt.putExtra("unit4", "");
            tt.putExtra("unit5", "");
            tt.putExtra("cost", txt_sum_cost.getText().toString().trim());
            tt.putExtra("time", time);
            tt.putExtra("day", day);
            tt.putExtra("week", week);
            tt.putExtra("month", month);
            tt.putExtra("year", year);
            tt.putExtra("user_id", User_id);
            tt.putExtra("purchase_order_id", PurchaseOrderId);
            startActivity(tt);
        } else if (DataList.size() == 3) {
            final String Seller = edt_buyer.getText().toString().trim();
            if (edt_buyer.getText().toString().isEmpty()) {
                edt_buyer.setError("กรุณาใส่ชื่อคนซื้อก่อน !!!");
                edt_buyer.requestFocus();
                return;
            }

            final String Amount1 = txt_amount1.getText().toString().trim();
            final String Amount2 = txt_amount2.getText().toString().trim();
            final String Amount3 = txt_amount3.getText().toString().trim();

            String[] Amount_Array = {Amount1, Amount2, Amount3};
            for (int i = 0; i < Amount_Array.length; i++) {
                if (Amount_Array[i].isEmpty() || Amount_Array.equals("0")) {
                    Toast.makeText(SelectedShoppingActivity2.this, "กรุณาเพิ่มจำนวนสินค้าก่อนให้ครบก่อน", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            final String product_id1 = DataList.get(0).getProduct_id().trim();
            final String product_id2 = DataList.get(1).getProduct_id().trim();
            final String product_id3 = DataList.get(2).getProduct_id().trim();
            final String product_type_id1 = DataList.get(0).getProduct_type_id().trim();
            final String product_type_id2 = DataList.get(1).getProduct_type_id().trim();
            final String product_type_id3 = DataList.get(2).getProduct_type_id().trim();
            if (txt_sum_cost.getText().toString().equals("0")) {
                Toast.makeText(SelectedShoppingActivity2.this, "กรุณาตรวจสอบรายการก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount1.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 1 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount2.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 2 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount3.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 3 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            Calendar c = Calendar.getInstance();
            int current_day = c.get(Calendar.DAY_OF_MONTH);
            int count_year = c.get(Calendar.YEAR) + 543;
            String Year = String.valueOf(count_year);
            String Month = new SimpleDateFormat("MM").format(new Date());
            String day2 = new SimpleDateFormat("dd").format(new Date());
            String PurchaseCodeId = "PAY" + Year.substring(2, 4) + Month + day2;

            String day = current_day + new SimpleDateFormat("/MM/").format(new Date()) + Year;
            String time = new SimpleDateFormat("HH:mm").format(new Date());

            Calendar cal = Calendar.getInstance();
            String week = String.valueOf(cal.get(Calendar.WEEK_OF_MONTH));
            String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
            String year = String.valueOf(cal.get(Calendar.YEAR));

            Intent tt = new Intent(SelectedShoppingActivity2.this, PaymentActivity.class);
            tt.putExtra("size", String.valueOf(DataList.size()));
            tt.putExtra("purchase_code_id", PurchaseCodeId);
            tt.putExtra("seller", Seller);
            tt.putExtra("product_id1", product_id1);
            tt.putExtra("product_id2", product_id2);
            tt.putExtra("product_id3", product_id3);
            tt.putExtra("product_id4", "");
            tt.putExtra("product_id5", "");
            tt.putExtra("product_type_id1", product_type_id1);
            tt.putExtra("product_type_id2", product_type_id2);
            tt.putExtra("product_type_id3", product_type_id3);
            tt.putExtra("product_type_id4", "");
            tt.putExtra("product_type_id5", "");
            tt.putExtra("amount1", Amount1);
            tt.putExtra("amount2", Amount2);
            tt.putExtra("amount3", Amount3);
            tt.putExtra("amount4", "");
            tt.putExtra("amount5", "");
            tt.putExtra("name1", String.valueOf(DataList.get(0).getName()));
            tt.putExtra("name2", String.valueOf(DataList.get(1).getName()));
            tt.putExtra("name3", String.valueOf(DataList.get(2).getName()));
            tt.putExtra("name4", "");
            tt.putExtra("name5", "");
            tt.putExtra("price1", txt_cost1.getText().toString().trim());
            tt.putExtra("price2", txt_cost2.getText().toString().trim());
            tt.putExtra("price3", txt_cost3.getText().toString().trim());
            tt.putExtra("price4", "");
            tt.putExtra("price5", "");
            tt.putExtra("unit1", String.valueOf(DataList.get(0).getUnit()));
            tt.putExtra("unit2", String.valueOf(DataList.get(1).getUnit()));
            tt.putExtra("unit3", String.valueOf(DataList.get(2).getUnit()));
            tt.putExtra("unit4", "");
            tt.putExtra("unit5", "");
            tt.putExtra("cost", txt_sum_cost.getText().toString().trim());
            tt.putExtra("time", time);
            tt.putExtra("day", day);
            tt.putExtra("week", week);
            tt.putExtra("month", month);
            tt.putExtra("year", year);
            tt.putExtra("user_id", User_id);
            tt.putExtra("purchase_order_id", PurchaseOrderId);
            startActivity(tt);
        } else if (DataList.size() == 4) {
            final String Seller = edt_buyer.getText().toString().trim();
            if (edt_buyer.getText().toString().isEmpty()) {
                edt_buyer.setError("กรุณาใส่ชื่อคนซื้อก่อน !!!");
                edt_buyer.requestFocus();
                return;
            }

            final String Amount1 = txt_amount1.getText().toString().trim();
            final String Amount2 = txt_amount2.getText().toString().trim();
            final String Amount3 = txt_amount3.getText().toString().trim();
            final String Amount4 = txt_amount4.getText().toString().trim();

            String[] Amount_Array = {Amount1, Amount2, Amount3, Amount4};
            for (int i = 0; i < Amount_Array.length; i++) {
                if (Amount_Array[i].isEmpty() || Amount_Array.equals("0")) {
                    Toast.makeText(SelectedShoppingActivity2.this, "กรุณาเพิ่มจำนวนสินค้าก่อนให้ครบก่อน", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            final String product_id1 = DataList.get(0).getProduct_id().trim();
            final String product_id2 = DataList.get(1).getProduct_id().trim();
            final String product_id3 = DataList.get(2).getProduct_id().trim();
            final String product_id4 = DataList.get(3).getProduct_id().trim();
            final String product_type_id1 = DataList.get(0).getProduct_type_id().trim();
            final String product_type_id2 = DataList.get(1).getProduct_type_id().trim();
            final String product_type_id3 = DataList.get(2).getProduct_type_id().trim();
            final String product_type_id4 = DataList.get(3).getProduct_type_id().trim();
            if (txt_sum_cost.getText().toString().equals("0")) {
                Toast.makeText(SelectedShoppingActivity2.this, "กรุณาตรวจสอบรายการก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount1.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 1 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount2.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 2 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount3.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 3 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount4.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 4 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            Calendar c = Calendar.getInstance();
            int current_day = c.get(Calendar.DAY_OF_MONTH);
            int count_year = c.get(Calendar.YEAR) + 543;
            String Year = String.valueOf(count_year);
            String Month = new SimpleDateFormat("MM").format(new Date());
            String day2 = new SimpleDateFormat("dd").format(new Date());
            String PurchaseCodeId = "PAY" + Year.substring(2, 4) + Month + day2;

            String day = current_day + new SimpleDateFormat("/MM/").format(new Date()) + Year;
            String time = new SimpleDateFormat("HH:mm").format(new Date());

            Calendar cal = Calendar.getInstance();
            String week = String.valueOf(cal.get(Calendar.WEEK_OF_MONTH));
            String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
            String year = String.valueOf(cal.get(Calendar.YEAR));

            Intent tt = new Intent(SelectedShoppingActivity2.this, PaymentActivity.class);
            tt.putExtra("size", String.valueOf(DataList.size()));
            tt.putExtra("purchase_code_id", PurchaseCodeId);
            tt.putExtra("seller", Seller);
            tt.putExtra("product_id1", product_id1);
            tt.putExtra("product_id2", product_id2);
            tt.putExtra("product_id3", product_id3);
            tt.putExtra("product_id4", product_id4);
            tt.putExtra("product_id5", "");
            tt.putExtra("product_type_id1", product_type_id1);
            tt.putExtra("product_type_id2", product_type_id2);
            tt.putExtra("product_type_id3", product_type_id3);
            tt.putExtra("product_type_id4", product_type_id4);
            tt.putExtra("product_type_id5", "");
            tt.putExtra("amount1", Amount1);
            tt.putExtra("amount2", Amount2);
            tt.putExtra("amount3", Amount3);
            tt.putExtra("amount4", Amount4);
            tt.putExtra("amount5", "");
            tt.putExtra("name1", String.valueOf(DataList.get(0).getName()));
            tt.putExtra("name2", String.valueOf(DataList.get(1).getName()));
            tt.putExtra("name3", String.valueOf(DataList.get(2).getName()));
            tt.putExtra("name4", String.valueOf(DataList.get(3).getName()));
            tt.putExtra("name5", "");
            tt.putExtra("price1", txt_cost1.getText().toString().trim());
            tt.putExtra("price2", txt_cost2.getText().toString().trim());
            tt.putExtra("price3", txt_cost3.getText().toString().trim());
            tt.putExtra("price4", txt_cost4.getText().toString().trim());
            tt.putExtra("price5", "");
            tt.putExtra("unit1", String.valueOf(DataList.get(0).getUnit()));
            tt.putExtra("unit2", String.valueOf(DataList.get(1).getUnit()));
            tt.putExtra("unit3", String.valueOf(DataList.get(2).getUnit()));
            tt.putExtra("unit4", String.valueOf(DataList.get(3).getUnit()));
            tt.putExtra("unit5", "");
            tt.putExtra("cost", txt_sum_cost.getText().toString().trim());
            tt.putExtra("time", time);
            tt.putExtra("day", day);
            tt.putExtra("week", week);
            tt.putExtra("month", month);
            tt.putExtra("year", year);
            tt.putExtra("user_id", User_id);
            tt.putExtra("purchase_order_id", PurchaseOrderId);
            startActivity(tt);
        } else if (DataList.size() == 5) {
            final String Seller = edt_buyer.getText().toString().trim();
            if (edt_buyer.getText().toString().isEmpty()) {
                edt_buyer.setError("กรุณาใส่ชื่อคนซื้อก่อน !!!");
                edt_buyer.requestFocus();
                return;
            }

            final String Amount1 = txt_amount1.getText().toString().trim();
            final String Amount2 = txt_amount2.getText().toString().trim();
            final String Amount3 = txt_amount3.getText().toString().trim();
            final String Amount4 = txt_amount4.getText().toString().trim();
            final String Amount5 = txt_amount5.getText().toString().trim();

            String[] Amount_Array = {Amount1, Amount2, Amount3, Amount4, Amount5};
            for (int i = 0; i < Amount_Array.length; i++) {
                if (Amount_Array[i].isEmpty() || Amount_Array.equals("0")) {
                    Toast.makeText(SelectedShoppingActivity2.this, "กรุณาเพิ่มจำนวนสินค้าก่อนให้ครบก่อน", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            final String product_id1 = DataList.get(0).getProduct_id().trim();
            final String product_id2 = DataList.get(1).getProduct_id().trim();
            final String product_id3 = DataList.get(2).getProduct_id().trim();
            final String product_id4 = DataList.get(3).getProduct_id().trim();
            final String product_id5 = DataList.get(4).getProduct_id().trim();
            final String product_type_id1 = DataList.get(0).getProduct_type_id().trim();
            final String product_type_id2 = DataList.get(1).getProduct_type_id().trim();
            final String product_type_id3 = DataList.get(2).getProduct_type_id().trim();
            final String product_type_id4 = DataList.get(3).getProduct_type_id().trim();
            final String product_type_id5 = DataList.get(4).getProduct_type_id().trim();
            if (txt_sum_cost.getText().toString().equals("0")) {
                Toast.makeText(SelectedShoppingActivity2.this, "กรุณาตรวจสอบรายการก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount1.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 1 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount2.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 2 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount3.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 3 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount4.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 4 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            if (txt_amount5.getText().toString().equals("0")) {
                Toast.makeText(this, "กรุณาเพิ่มจำนวนสินค้าชิ้นที่ 5 ก่อน", Toast.LENGTH_SHORT).show();
                return;
            }

            Calendar c = Calendar.getInstance();
            int current_day = c.get(Calendar.DAY_OF_MONTH);
            int count_year = c.get(Calendar.YEAR) + 543;
            String Year = String.valueOf(count_year);
            String Month = new SimpleDateFormat("MM").format(new Date());
            String day2 = new SimpleDateFormat("dd").format(new Date());
            String PurchaseCodeId = "PAY" + Year.substring(2, 4) + Month + day2;

            String day = current_day + new SimpleDateFormat("/MM/").format(new Date()) + Year;
            String time = new SimpleDateFormat("HH:mm").format(new Date());

            Calendar cal = Calendar.getInstance();
            String week = String.valueOf(cal.get(Calendar.WEEK_OF_MONTH));
            String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
            String year = String.valueOf(cal.get(Calendar.YEAR));

            Intent tt = new Intent(SelectedShoppingActivity2.this, PaymentActivity.class);
            tt.putExtra("size", String.valueOf(DataList.size()));
            tt.putExtra("purchase_code_id", PurchaseCodeId);
            tt.putExtra("seller", Seller);
            tt.putExtra("product_id1", product_id1);
            tt.putExtra("product_id2", product_id2);
            tt.putExtra("product_id3", product_id3);
            tt.putExtra("product_id4", product_id4);
            tt.putExtra("product_id5", product_id5);
            tt.putExtra("product_type_id1", product_type_id1);
            tt.putExtra("product_type_id2", product_type_id2);
            tt.putExtra("product_type_id3", product_type_id3);
            tt.putExtra("product_type_id4", product_type_id4);
            tt.putExtra("product_type_id5", product_type_id5);
            tt.putExtra("amount1", Amount1);
            tt.putExtra("amount2", Amount2);
            tt.putExtra("amount3", Amount3);
            tt.putExtra("amount4", Amount4);
            tt.putExtra("amount5", Amount5);
            tt.putExtra("name1", String.valueOf(DataList.get(0).getName()));
            tt.putExtra("name2", String.valueOf(DataList.get(1).getName()));
            tt.putExtra("name3", String.valueOf(DataList.get(2).getName()));
            tt.putExtra("name4", String.valueOf(DataList.get(3).getName()));
            tt.putExtra("name5", String.valueOf(DataList.get(4).getName()));
            tt.putExtra("price1", txt_cost1.getText().toString().trim());
            tt.putExtra("price2", txt_cost2.getText().toString().trim());
            tt.putExtra("price3", txt_cost3.getText().toString().trim());
            tt.putExtra("price4", txt_cost4.getText().toString().trim());
            tt.putExtra("price5", txt_cost5.getText().toString().trim());
            tt.putExtra("unit1", String.valueOf(DataList.get(0).getUnit()));
            tt.putExtra("unit2", String.valueOf(DataList.get(1).getUnit()));
            tt.putExtra("unit3", String.valueOf(DataList.get(2).getUnit()));
            tt.putExtra("unit4", String.valueOf(DataList.get(3).getUnit()));
            tt.putExtra("unit5", String.valueOf(DataList.get(4).getUnit()));
            tt.putExtra("cost", txt_sum_cost.getText().toString().trim());
            tt.putExtra("time", time);
            tt.putExtra("day", day);
            tt.putExtra("week", week);
            tt.putExtra("month", month);
            tt.putExtra("year", year);
            tt.putExtra("user_id", User_id);
            tt.putExtra("purchase_order_id", PurchaseOrderId);
            startActivity(tt);
        }
    }

    public String NumberFormatString(String number) {
        Double number2 = Double.parseDouble(number);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String get_value = formatter.format(number2);
        return get_value;
    }

    public String NumberFormatFloat(float number) {
        Double number2 = Double.parseDouble(String.valueOf(number));
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String get_value = formatter.format(number2);
        return get_value;
    }
}
