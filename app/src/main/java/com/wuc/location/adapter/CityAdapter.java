package com.wuc.location.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.wuc.location.R;
import com.wuc.location.databinding.LayoutLocationItemBinding;
import com.wuc.location.location.City;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author : wuchao5
 * @date : 2020/4/30 16:33
 * @desciption :
 */
public class CityAdapter extends BaseQuickAdapter<City,
        BaseDataBindingHolder<LayoutLocationItemBinding>> {

    public CityAdapter(@Nullable List<City> data) {
        super(R.layout.layout_location_item, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<LayoutLocationItemBinding> holder, City city) {
        LayoutLocationItemBinding binding = holder.getDataBinding();
        if (binding != null) {
            binding.setIsFirst(false);
            binding.setCity(city);
            binding.setSelectedCount(city.getSelectCount());
            if (city.isCheck()) {
                binding.setIsSelected(true);
            } else {
                binding.setIsSelected(false);
            }
        }
    }
}
