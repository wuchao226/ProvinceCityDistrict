package com.wuc.location.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.wuc.location.R;
import com.wuc.location.databinding.LayoutLocationItemBinding;
import com.wuc.location.location.Province;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author : wuchao5
 * @date : 2020/5/1 23:06
 * @desciption :
 */
public class ProvinceAdapter extends BaseQuickAdapter<Province,
        BaseDataBindingHolder<LayoutLocationItemBinding>> {


    public ProvinceAdapter(@Nullable List<Province> data) {
        super(R.layout.layout_location_item, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<LayoutLocationItemBinding> holder, Province item) {
        LayoutLocationItemBinding binding = holder.getDataBinding();
        if (binding != null) {
            binding.setIsFirst(true);
            binding.setProvince(item);
            binding.setSelectedCount(item.getSelectCount());
            if (item.isCheck()) {
                binding.setIsSelected(true);
            } else {
                binding.setIsSelected(false);
            }
        }
    }
}
