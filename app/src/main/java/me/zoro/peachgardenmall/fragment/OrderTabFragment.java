package me.zoro.peachgardenmall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.zoro.peachgardenmall.R;
import me.zoro.peachgardenmall.adapter.AllOrderRecyclerViewAdapter;
import me.zoro.peachgardenmall.datasource.domain.Order;

/**
 * 全部订单的fragment
 * Created by dengfengdecao on 17/4/22.
 */

public class OrderTabFragment extends Fragment {
    private static final String TAG = "OrderTabFragment";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

    private AllOrderRecyclerViewAdapter mRecyclerViewAdapter;
    private List<Order> mOrders;

    public static OrderTabFragment newInstance() {
        
        Bundle args = new Bundle();

        OrderTabFragment fragment = new OrderTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrders = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_list, container, false);
        unbinder = ButterKnife.bind(this, root);

        mRecyclerViewAdapter = new AllOrderRecyclerViewAdapter(getContext(), mOrders);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        Log.d(TAG, "onCreateView: mPendingTab ==> 全部");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
