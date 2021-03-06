package com.parsa_plm.jointelementinspector.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.jointelementinspector.main.ExpandableListHeader;
import com.jointelementinspector.main.R;
import com.parsa_plm.Layout.ParentLevelAdapter;

public class ProductStructureFragment extends Fragment{
    // this contains the data to be displayed
    private ExpandableListHeader headerData;
    private static final String PRODUCT_STRUCTURE = "Product Structure";
    private static final String PRODUCT_STRUCTURE_PART_NAME = "Part Name";
    private static final String PRODUCT_STRUCTURE_ITEM_TYPE = "Item Type";
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            headerData = bundle.getParcelable("com.ExpandableListData");
        }
    }

    public ProductStructureFragment(){}
    public static ProductStructureFragment newInstance(ExpandableListHeader headerData){
        ProductStructureFragment productStructureFragment = new ProductStructureFragment();
        Bundle bundle = new Bundle();
        if (headerData != null) {
            bundle.putParcelable("com.ExpandableListData", headerData);
        }
        productStructureFragment.setArguments(bundle);
        return productStructureFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productstructure, container, false);
        TextView productStructureHeader = (TextView) view.findViewById(R.id.productStructureHeader);
        productStructureHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        productStructureHeader.setText(PRODUCT_STRUCTURE);
        // 20160902: list structure item header
        TextView productStructurePartName = (TextView) view.findViewById(R.id.productStructure_partName);
        productStructurePartName.setText(PRODUCT_STRUCTURE_PART_NAME);
        TextView productStructureItemType = (TextView) view.findViewById(R.id.productStructure_itemType);
        productStructureItemType.setText(PRODUCT_STRUCTURE_ITEM_TYPE);
        final ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.parentLevel);
        // 20160831: check null value
        // 20160902: show proper indicator
        int width = getResources().getDisplayMetrics().widthPixels;
        if (expandableListView != null) {
            // 20160831: use get Activity to obtain the context, and this is working
            expandableListView.setAdapter(new ParentLevelAdapter(getActivity(), headerData));
            expandableListView.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
            expandableListView.setChildIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                int previousGroup = -1;
                @Override
                public void onGroupExpand(int i) {
                    if (i != previousGroup) {
                        expandableListView.collapseGroup(previousGroup);
                        previousGroup = i;
                    }
                }
            });
        }
        return view;
    }
    private int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
}
