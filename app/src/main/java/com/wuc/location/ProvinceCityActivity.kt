package com.wuc.location

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.XPopupCallback
import com.weibo.biz.ads.ui.location.LocationBottomDialog
import com.wuc.location.adapter.CityAdapter
import com.wuc.location.adapter.DistrictAdapter
import com.wuc.location.adapter.ProvinceAdapter
import com.wuc.location.databinding.ActivityProvinceCityBinding
import com.wuc.location.location.*
import java.util.*

/**
 * @author : wuchao5
 * @date : 2020/4/27 17:51
 * @desciption : 省市
 */
class ProvinceCityActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        fun open(context: Context) {
            val intent = Intent(context, ProvinceCityActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var mBinding: ActivityProvinceCityBinding? = null

    private lateinit var mProvinceAdapter: ProvinceAdapter
    private lateinit var mCityAdapter: CityAdapter
    private lateinit var mDistrictAdapter: DistrictAdapter

    private val mProvinceList: MutableList<Province> = ArrayList()
    private val mCityList: MutableList<City> = ArrayList()
    private val mDistrictList: MutableList<District> = ArrayList()

    /**
     * 省 上一次选择的位置
     */
    private var mLastClickProvincePos = 0

    /**
     * 市 上一次选择的位置
     */
    private var mLastClickCityPos = 0

    /**
     * 存储选择的区域数据
     */
    private val mSelectResultMap = mutableMapOf<String, SelectResultBean>()

    /**
     * 已选省市总数量
     */
    private var mTotalCount = 0

    /**
     * 底部dialog是否已经显示
     */
    private var isShowDialog = true

    private lateinit var mDialog: LocationBottomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_province_city)
        initData()
        initRecyclerView()
        initBottomDialog()
        setListener()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        val locationConfig = AppConfig.getLocationConfig()
        val configResult = locationConfig.result
        //val geoLocations = configResult.geoLocations
        val geoLocations = configResult.district
        mProvinceList.addAll(geoLocations)
        mProvinceList[0].isCheck = true
        mCityList.addAll(mProvinceList[0].value)
        mCityList[0].isCheck = true
        mDistrictList.addAll(mCityList[0].value)
    }


    private fun setListener() {
        mBinding!!.lytSelectCount.setOnClickListener {
            if (mTotalCount == 0) {
                return@setOnClickListener
            }
            if (isShowDialog) {
                if (mDialog.isDismiss) {
                    showDialog()
                }
                mBinding!!.ivArrow.rotation = 180f
            } else {
                if (mDialog.isShow) {
                    mDialog.dismiss()
                }
                mBinding!!.ivArrow.rotation = 0f
            }
        }
    }

    /**
     * 打开 底部dialog
     */
    private fun showDialog() {
        mDialog.setSelectCount(mTotalCount)
        doSelectedLocation()
        XPopup.Builder(this@ProvinceCityActivity)
            .setPopupCallback(object : XPopupCallback {
                override fun onCreated() {}
                override fun beforeShow() {}
                override fun onShow() {
                    isShowDialog = false
                }

                override fun onDismiss() {
                    isShowDialog = true
                }

                override fun onBackPressed(): Boolean {
                    return false
                }
            }).atView(mBinding!!.lytBottom).asCustom(mDialog).show()
    }

    /**
     * 处理已选区域数据，转换为 List 形式
     */
    private fun doSelectedLocation() {
        val tmpMap = HashMap<String, MutableList<SelectResultBean>>()
        mSelectResultMap.forEach {
            var tmpList = tmpMap[it.value.city]
            if (null == tmpList) {
                tmpList = mutableListOf()
                tmpMap[it.value.city] = tmpList
            }
            tmpList.add(it.value)
        }
        val cityList: MutableList<SelectResultParentBean> = ArrayList()
        tmpMap.forEach {
            cityList.add(SelectResultParentBean(it.key, it.value))
        }
        mDialog.setData(cityList)
    }

    /**
     * 初始化底部弹窗
     *
     * @return BasePopupView
     */

    private fun initBottomDialog() {
        mDialog = LocationBottomDialog(this) { isClear, data ->
            if (isClear) {
                //清空已选数据
                mSelectResultMap.forEach {
                    mProvinceList[it.value.provinceIndex].selectCount = 0
                    mProvinceList[it.value.provinceIndex].value[it.value.cityIndex].selectCount = 0
                    mProvinceList[it.value.provinceIndex].value[it.value.cityIndex].value[it.value.districtIndex].isCheck =
                        false

                    mProvinceAdapter.notifyItemChanged(it.value.provinceIndex)
                    mCityAdapter.notifyDataSetChanged()
                    mDistrictAdapter.notifyDataSetChanged()
                    //取消 全选
                    mProvinceList[it.value.provinceIndex].value[it.value.cityIndex].value[0].isCheck =
                        false
                    mDistrictAdapter.notifyItemChanged(0)
                    mTotalCount = 0
                    mDialog.dismiss()
                }
                mSelectResultMap.clear()
            } else {
                //点击删除
                mProvinceList[data!!.provinceIndex].selectCount--
                mProvinceAdapter.notifyItemChanged(data.provinceIndex)
                mProvinceList[data.provinceIndex].value[data.cityIndex].selectCount--
                mCityAdapter.notifyDataSetChanged()
                mProvinceList[data.provinceIndex].value[data.cityIndex].value[data.districtIndex].isCheck =
                    false
                mDistrictAdapter.notifyItemChanged(data.districtIndex)

                //取消 全选
                mProvinceList[data.provinceIndex].value[data.cityIndex].value[0].isCheck = false
                mDistrictAdapter.notifyItemChanged(0)

                mSelectResultMap.remove(data.code)
                mTotalCount--
                if (mTotalCount == 0) {
                    mDialog.dismiss()
                }
            }
            updateTotalCountInActivity()
        }.loadDialog()

    }

    private fun initRecyclerView() {
        //初始化 省 RecyclerView 并处理数据
        initProvinceRecyclerView()
        //初始化 市 RecyclerView 并处理数据
        initCityRecyclerView()
        //初始化 区域 RecyclerView 并处理数据
        initDistrictRecyclerView()
    }

    /**
     * 初始化 省 RecyclerView 并处理数据
     */
    private fun initProvinceRecyclerView() {
        mProvinceAdapter = ProvinceAdapter(mProvinceList)
        mBinding!!.recyclerViewFirst.layoutManager = LinearLayoutManager(this)
        mProvinceAdapter!!.setOnItemClickListener(OnItemClickListener { adapter, view, position ->
            if (mLastClickProvincePos == position) {
                return@OnItemClickListener
            }
            val provinceList: List<Province> = mProvinceAdapter!!.data
            val provinceItem = provinceList[position]
            // 清除当前上一位置的选中效果，并选中当前点击的位置
            provinceList[mLastClickProvincePos].isCheck = false
            mProvinceAdapter!!.notifyItemChanged(mLastClickProvincePos)
            provinceItem.isCheck = true
            mProvinceAdapter!!.notifyItemChanged(position)

            // 清除第二列上一位置的选中效果
            mCityList[mLastClickCityPos].isCheck = false
            mLastClickCityPos = 0
            mCityList.clear()
            mCityList.addAll(provinceItem.value)
            mCityList[0].isCheck = true
            mCityAdapter!!.notifyDataSetChanged()
            //更新区域数据
            updateDistrictData(provinceItem.value[0])
            mLastClickProvincePos = position
        })
        mBinding!!.recyclerViewFirst.adapter = mProvinceAdapter
    }

    /**
     * 初始化 市 RecyclerView 并处理数据
     */
    private fun initCityRecyclerView() {
        mCityAdapter = CityAdapter(mCityList)
        mBinding!!.recyclerViewTwo.layoutManager = LinearLayoutManager(this)
        mCityAdapter!!.setOnItemClickListener(OnItemClickListener { adapter, view, position ->
            if (mLastClickCityPos == position) {
                return@OnItemClickListener
            }
            val cityList: List<City> = mCityAdapter.data
            val cityItem = cityList[position]
            // 清除当前上一位置的选中效果，并选中当前点击的位置
            mCityList[mLastClickCityPos].isCheck = false
            mCityAdapter.notifyItemChanged(mLastClickCityPos)
            cityItem.isCheck = true
            mCityAdapter.notifyItemChanged(position)
            //更新区域数据
            updateDistrictData(cityItem)
            mLastClickCityPos = position
        })
        mBinding!!.recyclerViewTwo.adapter = mCityAdapter
    }

    /**
     * 初始化 区域 RecyclerView 并处理数据
     */
    private fun initDistrictRecyclerView() {
        //解决 RecyclerView 刷新闪烁问题
        val pool = RecyclerView.RecycledViewPool()
        pool.setMaxRecycledViews(0, 25)
        mBinding!!.recyclerViewThree.setRecycledViewPool(pool)
        mDistrictAdapter = DistrictAdapter(mDistrictList)
        mBinding!!.recyclerViewThree.layoutManager = LinearLayoutManager(this)
        mDistrictAdapter.setOnItemClickListener { adapter, view, position ->

            val districtItem = mDistrictList[position]
            val province = mProvinceList[mLastClickProvincePos]
            val city = mCityList[mLastClickCityPos]

            if (position == 0) {
                //全选
                if (!districtItem.isCheck) { // 执行全选操作
                    mDistrictList.forEachIndexed { index, it ->
                        it.isCheck = true
                        if (index != 0) {
                            if (null == mSelectResultMap[it.value]) { // 没选中
                                province.selectCount++
                                city.selectCount++
                                mTotalCount++
                            }
                            saveSelectDistrictToMap(it, province, city, index)
                        }
                    }
                } else { // 反全选
                    mDistrictList.forEachIndexed { index, it ->
                        if (0 != index) {
                            mSelectResultMap.remove(it.value)
                            province.selectCount--
                            mTotalCount--
                        }
                        it.isCheck = false
                    }
                    city.selectCount = 0
                }
                mDistrictAdapter.notifyDataSetChanged()
            } else {
                //单个 item 选择、取消选择
                if (districtItem.isCheck) {
                    // 已选中，点击后未选中
                    province.selectCount--
                    city.selectCount--
                    mTotalCount--
                    mSelectResultMap.remove(districtItem.value)
                } else {
                    // 未选中，点击后已选中
                    province.selectCount++
                    city.selectCount++
                    mTotalCount++
                    saveSelectDistrictToMap(districtItem, province, city, position)
                }
                districtItem.isCheck = !districtItem.isCheck
                mDistrictAdapter.notifyItemChanged(position)

                if (city.selectCount == mDistrictList.size - 1) {
                    //全选
                    mDistrictList[0].isCheck = true
                } else {
                    if (city.selectCount == mDistrictList.size - 2) {
                        //取消全选
                        mDistrictList[0].isCheck = false
                    }
                }
                mDistrictAdapter.notifyItemChanged(0)
            }
            updateTotalCountInActivity()
            mProvinceAdapter.notifyItemChanged(mLastClickProvincePos)
            mCityAdapter.notifyItemChanged(mLastClickCityPos)
        }
        mBinding!!.recyclerViewThree.adapter = mDistrictAdapter
    }

    /**
     * 保存选择的区域数据到 HashMap
     */
    private fun saveSelectDistrictToMap(
        districtItem: District,
        province: Province,
        city: City,
        position: Int
    ) {
        mSelectResultMap[districtItem.value] = SelectResultBean(
            province.name, city.name, districtItem.name, districtItem.value,
            mLastClickProvincePos, mLastClickCityPos, position
        )
    }

    /**
     * 更新选中的区域数量
     */
    private fun updateTotalCountInActivity() {
        mBinding!!.txtCount.text = "已选择${mTotalCount}个"
    }

    /**
     * 更新区域数据
     *
     * @param city city
     */
    private fun updateDistrictData(city: City) {
        mDistrictList.clear()
        mDistrictList.addAll(city.value)
        mDistrictAdapter.notifyDataSetChanged()
    }

}