package com.wuc.location.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.wuc.location.R;
import com.wuc.location.databinding.LayoutLocationCheckedItemBinding;
import com.wuc.location.location.District;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author : wuchao5
 * @date : 2020/4/30 14:29
 * @desciption :
 */
public class DistrictAdapter extends BaseQuickAdapter<District,
        BaseDataBindingHolder<LayoutLocationCheckedItemBinding>> {

    public DistrictAdapter(List<District> data) {
        super(R.layout.layout_location_checked_item, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<LayoutLocationCheckedItemBinding> holder, District district) {
        LayoutLocationCheckedItemBinding binding = holder.getDataBinding();
        if (binding != null) {
            binding.setDistrict(district);
            if (district.isCheck()) {
                binding.setIsSelected(true);
            } else {
                binding.setIsSelected(false);
            }
        }
    }
}
