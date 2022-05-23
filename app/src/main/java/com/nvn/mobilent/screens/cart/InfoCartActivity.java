package com.nvn.mobilent.screens.cart;

import android.app.Notification;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.nvn.mobilent.R;
import com.nvn.mobilent.data.base.NotificationApp;
import com.nvn.mobilent.data.base.PathAPI;
import com.nvn.mobilent.data.base.RetrofitClient;
import com.nvn.mobilent.data.datalocal.DataLocalManager;
import com.nvn.mobilent.data.model.cart.Cart;
import com.nvn.mobilent.data.model.order.OrderDetailCheckout;
import com.nvn.mobilent.data.model.cart.RListCartItem;
import com.nvn.mobilent.data.model.order.ROrderObject;
import com.nvn.mobilent.data.model.cart.R_Cart;
import com.nvn.mobilent.data.model.cart.R_ProductCartItem;
import com.nvn.mobilent.data.model.user.User;
import com.nvn.mobilent.data.api.CartItemAPI;
import com.nvn.mobilent.data.api.OrderAPI;
import com.nvn.mobilent.data.api.ProductAPI;
import com.nvn.mobilent.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoCartActivity extends AppCompatActivity {

    NotificationManagerCompat notificationManagerCompat;

    EditText recipientName, phone, address;
    Button btnDatHang;
    User user;
    Toolbar toolbar;
    int sizeCart;
    private String regexName = "[aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆ fFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTu UùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ]+";
    private String regexPhone = "[0]{1}\\d{9}";

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutPhone;
    private TextInputLayout textInputLayoutAddress;

    public static void deleteAllCart(int userid) {
        CartItemAPI cartItemAPI = (CartItemAPI) RetrofitClient.getClient(PathAPI.linkAPI).create(CartItemAPI.class);
        cartItemAPI.deleteAllCartByUserId(userid).enqueue(new Callback<R_Cart>() {
            @Override
            public void onResponse(Call<R_Cart> call, Response<R_Cart> response) {
                if (response.isSuccessful()) {
                    System.out.println("Deleted Cart: " + response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<R_Cart> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    private void catchData() {
        recipientName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty() && !charSequence.toString().matches(regexName)) {
                    textInputLayoutName.setError("Chỉ dùng các chữ cái");
                } else {
                    textInputLayoutName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty() && !charSequence.toString().matches(regexPhone)) {
                    textInputLayoutPhone.setError("Số điện thoại gồm 10 số và bắt đầu bằng 0");
                } else {
                    textInputLayoutPhone.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private boolean checkData() {
        boolean check = true;
        if (recipientName.getText().toString().trim().equals("")) {
            textInputLayoutName.setError("Không được để trống!");
            return false;
        }
        if (address.getText().toString().trim().equals("")) {
            textInputLayoutAddress.setError("Không được để trống!");
            return false;
        }
        if (phone.getText().toString().trim().equals("")) {
            textInputLayoutPhone.setError("Không được để trống!");
            return false;
        } else {
            textInputLayoutPhone.setError(null);
        }

        // Check TextInput null
        if (!(textInputLayoutName.getError() == null)) {
            return false;
        }
        if (!(textInputLayoutAddress.getError() == null)) {
            return false;
        }
        if (!(textInputLayoutPhone.getError() == null)) {
            return false;
        }
        return check;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_cart);
        user = DataLocalManager.getUser();
        setControl();
        recipientName.setText(user.getLastname() + " " + user.getFirstname());
        address.setText(user.getAddress());
        phone.setText(user.getPhone());
        catchData();
        setEvent();
        actionToolBar();
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //tạo nút home
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setEvent() {
        if (!AppUtils.haveNetworkConnection(getApplicationContext())) {
            AppUtils.showToast_Short(getApplicationContext(), "Kiểm tra lại kết nối Internet");
        } else {
            btnDatHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProductAPI productAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(ProductAPI.class);
                    CartItemAPI cartItemAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(CartItemAPI.class);
                    OrderAPI orderAPI = RetrofitClient.getClient(PathAPI.linkAPI).create(OrderAPI.class);

                    String name = recipientName.getText().toString().trim();
                    String sdt = phone.getText().toString().trim();
                    String diaChi = address.getText().toString().trim();
                    if (checkData()) {
                        //CHECK CHECK CHECK
                        System.out.println("INFOSIZECART: " + sizeCart);
                        if (sizeCart > 0) {
                            System.out.println("AAAAAA: " + user.getId());
                            orderAPI.postOrder(user.getId(), diaChi, sdt, name).enqueue(new Callback<ROrderObject>() {
                                @Override
                                public void onResponse(Call<ROrderObject> call, Response<ROrderObject> response) {
                                    if (response.isSuccessful()) {
//                                        AppUtils.showToast_Short(getApplicationContext(), "Đặt hàng thành công!");
                                        int idOrder = response.body().getData().getId();
                                        recipientName.setText("");
                                        phone.setText("");
                                        address.setText("");
                                        // Get sản phẩm từ cart
                                        cartItemAPI.getCartItemByUserId(user.getId()).enqueue(new Callback<RListCartItem>() {
                                            @Override
                                            public void onResponse(Call<RListCartItem> call, Response<RListCartItem> response) {
                                                if (response.isSuccessful() && response.body().getData().size() > 0) {
                                                    for (Cart c : response.body().getData()) {
                                                        //   Get giá sản phẩm từ product
                                                        productAPI.getProductByID(c.getProdId()).enqueue(new Callback<R_ProductCartItem>() {
                                                            @Override
                                                            public void onResponse(Call<R_ProductCartItem> call, Response<R_ProductCartItem> response) {
                                                                int price = response.body().getData().getPrice();
                                                                // Đưa vào order Detail
                                                                orderAPI.postOrderDetail(c.getQuantity(), c.getProdId(), price, idOrder).enqueue(new Callback<OrderDetailCheckout>() {
                                                                    @Override
                                                                    public void onResponse(Call<OrderDetailCheckout> call, Response<OrderDetailCheckout> response) {
                                                                        if (response.isSuccessful()) {
                                                                            System.out.println("ADDED!");
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<OrderDetailCheckout> call, Throwable t) {

                                                                    }
                                                                });

                                                            }

                                                            @Override
                                                            public void onFailure(Call<R_ProductCartItem> call, Throwable t) {
                                                            }
                                                        });
                                                    }
                                                    deleteAllCart(user.getId());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<RListCartItem> call, Throwable t) {
                                                System.out.println();
                                            }
                                        });
                                        sendOnChannel1();
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ROrderObject> call, Throwable t) {
                                    System.out.println("Lỗi " + t);
                                }
                            });
                            sizeCart = 0;
                        } else {
                            AppUtils.showToast_Short(getApplicationContext(), "Giỏ hàng trống!");
                        }
                    } else {
                        AppUtils.showToast_Short(getApplicationContext(), "Kiểm tra lại dữ liệu nhập vào");
                    }
                }
            });
        }
    }

    private void setControl() {
        sizeCart = getIntent().getIntExtra("sizecart", 0);
        toolbar = findViewById(R.id.toolbar_infocart);
        recipientName = findViewById(R.id.txtnguoinhan);
        phone = findViewById(R.id.txtsdtnguoinhan);
        address = findViewById(R.id.txtdiachi);
        btnDatHang = findViewById(R.id.btncheckout);
        textInputLayoutName = findViewById(R.id.namepayment);
        textInputLayoutPhone = findViewById(R.id.phonepayment);
        textInputLayoutAddress = findViewById(R.id.addresspayment);

    }

    private void sendOnChannel1() {
        String title = "Mobile Shop App";
        String message = "Đơn hàng của bạn đã được đặt thành công!";

        Notification notification = new NotificationCompat.Builder(this, NotificationApp.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_ok)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        int notificationId = 1;
        NotificationManagerCompat.from(getApplicationContext())
                .notify("tag", notificationId, notification);
    }

    private void sendOnChannel2() {
        String title = "Mobile Shop App";
        String message = "Đơn hàng của bạn đã được đặt thành công!";

        Notification notification = new NotificationCompat.Builder(this, NotificationApp.CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_ok)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_PROMO) // Promotion.
                .build();

        int notificationId = 2;
        NotificationManagerCompat.from(getApplicationContext())
                .notify("tag", notificationId, notification);
    }
}