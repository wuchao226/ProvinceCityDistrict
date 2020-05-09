package com.wuc.location;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.wuc.location.databinding.LayoutLocationSelectDistrictDialogItemBinding;
import com.wuc.location.location.SelectResultBean;
import com.wuc.location.location.SelectResultParentBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author wuchao
 * @date 2020/4/16 15:46
 * @desciption
 */
public class FlexBoxDistrictRecyclerView extends RecyclerView {

    private MyAdapter mAdapter;

    public FlexBoxDistrictRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public FlexBoxDistrictRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlexBoxDistrictRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FlexboxLayoutManager manager = new FlexboxLayoutManager(context, FlexDirection.ROW, FlexWrap.WRAP);
        manager.setJustifyContent(JustifyContent.FLEX_START);
        this.setLayoutManager(manager);
        //this.setAdapter(new MyAdapter());
    }

    public void setItems(SelectResultParentBean city) {
        if (city != null) {
            List<SelectResultBean> data = city.getResult();
            if (data != null && data.size() > 0) {
                mAdapter = new MyAdapter(data);

                this.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                    }
                });
            }
        }
    }

    private static class MyAdapter extends BaseQuickAdapter<SelectResultBean,
            BaseDataBindingHolder<LayoutLocationSelectDistrictDialogItemBinding>> {

        public MyAdapter(List<SelectResultBean> data) {
            super(R.layout.layout_location_select_district_dialog_item, data);
        }


        @Override
        protected void convert(@NotNull BaseDataBindingHolder<LayoutLocationSelectDistrictDialogItemBinding> holder,
                               SelectResultBean item) {
            // 获取 Binding
            LayoutLocationSelectDistrictDialogItemBinding binding = holder.getDataBinding();
            if (binding != null) {
                binding.setDistrict(item);
                binding.executePendingBindings();
                binding.ivClose.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
    }

}
