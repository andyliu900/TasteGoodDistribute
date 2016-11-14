package com.tastegood.distribute.order.view;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tastegood.distribute.R;
import com.tastegood.distribute.order.domain.model.Cart;
import com.tastegood.distribute.order.domain.model.Order;
import com.tastegood.distribute.util.StringUtils;
import com.tastegood.distribute.util.UIUtils;
import com.tastegood.distribute.util.ViewCompactUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by surandy on 2016/11/11.
 */

public class OrderRecordArrivedView extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;

    private TextView nubmer;
    private TextView orderNo;
    private TextView foodInfoIndicator;
    private TextView orderInfoIndicator;

    private View foodInfoLayout;
    private View orderInfoLayout;
    private View orderInfoBottomLayout;
    private View foodInfoBottomLayout;

    // 菜品信息字段
    private ListView cartListView;
    private TextView lunchPrice;
    private TextView sendPrice;
    private TextView discountPrice;
    private TextView foodTotal;
    private TextView foodDiscount;
    private TextView realPayTotal;

    // 订单信息字段
    private TextView customerName;
    private TextView customerPhone;
    private TextView customerLocation;
    private TextView storeName;
    private TextView storePhone;
    private TextView storeLocation;
    private TextView orderTime;
    private TextView printTime;
    private TextView sendTime;
    private TextView arrivedTime;

    private ItemClickListener mListener;
    private CartListAdapter cartListAdapter;
    private String storePhoneStr;

    public OrderRecordArrivedView(ViewGroup parent, ItemClickListener listener) {
        super(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_order_arrived, parent, false));

        this.context = parent.getContext();
        this.mListener = listener;

        nubmer = (TextView) itemView.findViewById(R.id.nubmer);
        orderNo = (TextView) itemView.findViewById(R.id.orderNo);
        foodInfoIndicator = (TextView) itemView.findViewById(R.id.foodInfoIndicator);
        foodInfoIndicator.setOnClickListener(this);
        orderInfoIndicator = (TextView) itemView.findViewById(R.id.orderInfoIndicator);
        orderInfoIndicator.setOnClickListener(this);
        foodInfoLayout = itemView.findViewById(R.id.foodInfoLayout);
        orderInfoLayout = itemView.findViewById(R.id.orderInfoLayout);
        orderInfoBottomLayout = itemView.findViewById(R.id.orderInfoBottomLayout);
        foodInfoBottomLayout = itemView.findViewById(R.id.foodInfoBottomLayout);

        // 菜品字段
        cartListView = (ListView) itemView.findViewById(R.id.cartListView);
        cartListAdapter = new CartListAdapter(parent.getContext());
        cartListView.setAdapter(cartListAdapter);
        lunchPrice = (TextView) itemView.findViewById(R.id.lunchPrice);
        sendPrice = (TextView) itemView.findViewById(R.id.sendPrice);
        discountPrice = (TextView) itemView.findViewById(R.id.discountPrice);
        foodTotal = (TextView) itemView.findViewById(R.id.foodTotal);
        foodDiscount = (TextView) itemView.findViewById(R.id.foodDiscount);
        realPayTotal = (TextView) itemView.findViewById(R.id.realPayTotal);

        // 订单字段
        customerName = (TextView) itemView.findViewById(R.id.customerName);
        customerPhone = (TextView) itemView.findViewById(R.id.customerPhone);
        customerLocation = (TextView) itemView.findViewById(R.id.customerLocation);
        storeName = (TextView) itemView.findViewById(R.id.storeName);
        storePhone = (TextView) itemView.findViewById(R.id.storePhone);
        storePhone.setOnClickListener(this);
        storeLocation = (TextView) itemView.findViewById(R.id.storeLocation);
        orderTime = (TextView) itemView.findViewById(R.id.orderTime);
        printTime = (TextView) itemView.findViewById(R.id.printTime);
        sendTime = (TextView) itemView.findViewById(R.id.sendTime);
        arrivedTime = (TextView) itemView.findViewById(R.id.arrivedTime);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.foodInfoIndicator:
                ViewCompactUtils.setTextViewColor(context, foodInfoIndicator, R.color.itemOrderRecordIndicatorColor);
                ViewCompactUtils.setTextViewColor(context, orderInfoIndicator, R.color.whiteGray);
                foodInfoLayout.setVisibility(View.VISIBLE);
                orderInfoLayout.setVisibility(View.GONE);
                foodInfoBottomLayout.setVisibility(View.VISIBLE);
                orderInfoBottomLayout.setVisibility(View.GONE);
                break;
            case R.id.orderInfoIndicator:
                ViewCompactUtils.setTextViewColor(context, orderInfoIndicator, R.color.itemOrderRecordIndicatorColor);
                ViewCompactUtils.setTextViewColor(context, foodInfoIndicator, R.color.whiteGray);
                foodInfoLayout.setVisibility(View.GONE);
                orderInfoLayout.setVisibility(View.VISIBLE);
                foodInfoBottomLayout.setVisibility(View.GONE);
                orderInfoBottomLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.storePhone:
                mListener.itemPhoneClick(storePhoneStr);
                break;
        }
    }

    public void setData(Order item) {
        nubmer.setText("#" + item.getId());
        orderNo.setText("订单号：" + item.getOrderNo());

        // 菜品信息
        if (cartListAdapter != null) {
            cartListAdapter.setDatas(item.getCartList());
            cartListAdapter.notifyDataSetChanged();
            UIUtils.setListViewHeightBasedOnChildren(cartListView);
        }
        lunchPrice.setText("¥" + item.getLunchPrice());
        sendPrice.setText("¥" + item.getSendPrice());
        discountPrice.setText("-¥" + item.getDiscountPrice());

        List<Cart> cartList = item.getCartList();
        double foodTotalFree = 0.0;
        for (Cart cart : cartList) {
            foodTotalFree += (cart.getOrgPrice() * cart.getNum());
        }
        foodTotalFree = foodTotalFree + item.getLunchPrice() + item.getSendPrice();
        foodTotal.setText("总计¥" + foodTotalFree);

        double foodDiscountFree = 0.0;
        for (Cart cart : cartList) {
            if (cart.getActivityPrice() != 0
                    && cart.getActivityPrice() != 0.0
                    && cart.getActivityPrice() != 0.00) {
                foodDiscountFree += (cart.getOrgPrice() - cart.getActivityPrice());
            }
        }
        foodDiscount.setText("优惠¥" + foodDiscountFree);

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("实付");
        spannableStringBuilder.append(UIUtils.textColor("¥" + item.getTotalCost(), ViewCompactUtils.getColor(context, R.color.itemOrderRecordIndicatorColor)));
        realPayTotal.setText(spannableStringBuilder);

        // 订单信息
        customerName.setText(item.getReceiver());
        customerPhone.setText(item.getMobile());
        customerLocation.setText(item.getAddress());
        storeName.setText(item.getStorefrontName());
        storePhoneStr = item.getStorefrontTelephone();
        storeLocation.setText(item.getStorefrontAddress());
        orderTime.setText("下单时间：" + StringUtils.CoverLongTime2String(item.getCreateTime()));
        printTime.setText("打印时间：" + StringUtils.CoverLongTime2String(item.getPrintTime()));
        sendTime.setText("配送时间：" + StringUtils.CoverLongTime2String(item.getGetTime()));
        arrivedTime.setText("送达时间：" + StringUtils.CoverLongTime2String(item.getSendoneTime()));
    }

    public interface ItemClickListener {
        public void itemPhoneClick(String phone);
    }

    class CartListAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private List<Cart> mCartList = new ArrayList();

        public CartListAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        public void setDatas(List<Cart> cartList) {
            mCartList.clear();
            mCartList.addAll(cartList);
        }

        @Override
        public int getCount() {
            return mCartList.size();
        }

        @Override
        public Object getItem(int position) {
            return mCartList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_cart,null);
                holder.foodName = (TextView) convertView.findViewById(R.id.foodName);
                holder.foodCount = (TextView) convertView.findViewById(R.id.foodCount);
                holder.foodOrgPrice = (TextView) convertView.findViewById(R.id.foodOrgPrice);
                holder.foodActivityPrice = (TextView) convertView.findViewById(R.id.foodActivityPrice);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Cart item = mCartList.get(position);
            holder.foodName.setText(item.getMenuName());
            holder.foodCount.setText("x" + item.getNum());
            if (item.getSpecialities() == 0) { // 不是活动
                holder.foodActivityPrice.setText("¥" + item.getOrgPrice());
                holder.foodOrgPrice.setVisibility(View.GONE);
            } else {
                holder.foodOrgPrice.setVisibility(View.VISIBLE);
                holder.foodOrgPrice.setText("¥" + item.getOrgPrice());
                holder.foodOrgPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.foodActivityPrice.setText("¥" + item.getActivityPrice());
            }

            holder.foodName.setText(item.getMenuName());
            holder.foodName.setText(item.getMenuName());

            return convertView;
        }

        public class ViewHolder {
            TextView foodName;
            TextView foodActivityPrice;
            TextView foodOrgPrice;
            TextView foodCount;
        }

    }
}
