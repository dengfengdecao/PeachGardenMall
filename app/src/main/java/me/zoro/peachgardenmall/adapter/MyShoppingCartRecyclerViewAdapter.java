package me.zoro.peachgardenmall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.activity.GoodsDetailActivity;
import me.zoro.peachgardenmall.activity.MyShoppingCartActivity;
import me.zoro.peachgardenmall.datasource.domain.Cart;

import static me.zoro.peachgardenmall.fragment.HomeFragment.GOODS_ID_EXTRA;

/**
 * Created by dengfengdecao on 16/12/5.
 */

public class MyShoppingCartRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = -1;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;


    private MyShoppingCartActivity mContext;
    private List<Cart> mCarts;
    private double mTotalMoney;

    private boolean mIsShowChecked;

    /**
     * 存储勾选框状态的map集合
     */
    private Map<Integer, Boolean> map = new HashMap<>();

    private static OnAddSubtractClickListener sOnAddSubtractClickListener;

    public void appendData(List<Cart> carts) {
        mCarts.addAll(carts);
        notifyDataSetChanged();
    }


    public static interface OnAddSubtractClickListener {
        void onAddSubtractClick(View view, double money, int count);
    }

    public void setOnAddSubtractClickListener(OnAddSubtractClickListener listener) {
        sOnAddSubtractClickListener = listener;
    }

    public MyShoppingCartRecyclerViewAdapter(MyShoppingCartActivity context, List<Cart> carts, boolean isShowChecked) {
        mContext = context;
        mCarts = carts;
        mIsShowChecked = isShowChecked;
    }

    @Override
    public int getItemViewType(int position) {
        // item 第一个位置position为0，之后递增
        if (isEmpty()) {
            return TYPE_EMPTY;
        }

        return TYPE_ITEM;
    }

    private boolean isEmpty() {
        return mCarts.size() == 0;
    }

    public void replaceData(List<Cart> carts, boolean isShowChecked) {
        mCarts = carts;
        mIsShowChecked = isShowChecked;
        mTotalMoney = 0;
        // 调用以下方法更新后，会依次调用getItemViewType和onBindViewHolder方法
        notifyDataSetChanged();
        //mContext.updateTotalMoney();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            View viewItem = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,
                    parent, false);
            return new RecyclerEmptyViewHolder(viewItem);
        }
        View viewItem = LayoutInflater.from(mContext).inflate(R.layout.my_shopping_cart_rvi,
                parent, false);

        return RecyclerItemViewHolder.newInstance(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // item 第一个位置position为0，之后递增
        if (holder instanceof RecyclerEmptyViewHolder) {
            RecyclerEmptyViewHolder viewHolder = (RecyclerEmptyViewHolder) holder;
            viewHolder.mTvEmptyHint.setText(R.string.empty_data_hint);
            mTotalMoney = 0;
        } else {
            RecyclerItemViewHolder viewHolder = (RecyclerItemViewHolder) holder;
            if (mIsShowChecked) {
                viewHolder.mCbChecked.setVisibility(View.VISIBLE);
                // 设置checkBox选中监听
                viewHolder.mCbChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // 用map集合保存
                        map.put(position, isChecked);
                    }
                });

            } else {
                viewHolder.mCbChecked.setVisibility(View.GONE);
            }


            Cart cart = getItem(position);
            viewHolder.itemView.setTag(Double.parseDouble(cart.getGoodsPrice()));
            viewHolder.mGoodsNameTv.setText(cart.getGoodsName());
            viewHolder.mTvGoodsMoney.setText(cart.getGoodsPrice());
            viewHolder.mTvGoodsSpec.setText(cart.getSpecKeyName());
            Picasso.with(mContext)
                    .load(cart.getImageUrl())
                    .fit()
                    .into(viewHolder.mCartsImgIv);
            viewHolder.mCountTv.setText(String.valueOf(cart.getGoodsNum()));
            viewHolder.mSubtractIv.setTag(Double.parseDouble(cart.getGoodsPrice()));
            viewHolder.mAddIv.setTag(Double.parseDouble(cart.getGoodsPrice()));

            // 存入商品id
            viewHolder.mCartsInfo.setTag(cart.getGoodsId());

            mTotalMoney += Double.parseDouble(cart.getGoodsPrice()) * cart.getGoodsNum();
        }
        mContext.updateTotalMoney(mTotalMoney);
    }

    private Cart getItem(int position) {
        return mCarts.get(position);
    }

    public Map<Integer, Boolean> getMap() {
        return map;
    }

    @Override
    public int getItemCount() {
        return mCarts.size() > 0 ? mCarts.size() : 1;
    }


    static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_img_iv)
        ImageView mCartsImgIv;
        @BindView(R.id.goods_info)
        LinearLayout mCartsInfo;
        @BindView(R.id.goods_name_tv)
        TextView mGoodsNameTv;
        @BindView(R.id.tv_goods_spec)
        TextView mTvGoodsSpec;
        @BindView(R.id.tv_goods_money)
        TextView mTvGoodsMoney;
        @BindView(R.id.subtract_iv)
        ImageView mSubtractIv;
        @BindView(R.id.count_tv)
        TextView mCountTv;
        @BindView(R.id.add_iv)
        ImageView mAddIv;
        @BindView(R.id.cb_checked)
        CheckBox mCbChecked;

        private Context mContext;
        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public static RecyclerView.ViewHolder newInstance(View viewItem) {
            return new RecyclerItemViewHolder(viewItem);
        }

        @OnClick({R.id.goods_info, R.id.subtract_iv, R.id.add_iv})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.goods_info:
                    Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                    intent.putExtra(GOODS_ID_EXTRA, (int) mCartsInfo.getTag());
                    mContext.startActivity(intent);
                    break;
                case R.id.subtract_iv:
                    int count = Integer.parseInt(mCountTv.getText().toString());
                    count--;

                    if (count >= 1) {
                        if (sOnAddSubtractClickListener != null) {
                            sOnAddSubtractClickListener.onAddSubtractClick(view, -(double) view.getTag(), count);
                        }
                    } else {
                        count = 1;
                    }
                    mCountTv.setText(String.valueOf(count));

                    break;
                case R.id.add_iv:
                    count = Integer.parseInt(mCountTv.getText().toString());
                    count++;
                    mCountTv.setText(String.valueOf(count));
                    if (sOnAddSubtractClickListener != null) {
                        sOnAddSubtractClickListener.onAddSubtractClick(view, (double) view.getTag(), count);
                    }
                    break;
            }
        }
    }

    private class RecyclerEmptyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvEmptyHint;

        public RecyclerEmptyViewHolder(View viewItem) {
            super(viewItem);
            mTvEmptyHint = (TextView) viewItem.findViewById(android.R.id.text1);
        }
    }
}
