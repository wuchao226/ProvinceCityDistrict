package com.weibo.biz.ads.ui.location

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.google.android.flexbox.FlexboxLayoutManager
import com.lxj.xpopup.impl.PartShadowPopupView
import com.wuc.location.R
import com.wuc.location.adapter.BaseRecyclerViewAdapter
import com.wuc.location.location.SelectResultBean
import com.wuc.location.location.SelectResultParentBean

/**
 * @author : wuchao5
 * @date : 2020/5/2 22:37
 * @desciption : 创建广告  --- 设置计划  --- 定向 --  省市区  --- 底部弹窗
 */
class LocationBottomDialog(
    context: Context,
    private val onDelClick: (isClear: Boolean, data: SelectResultBean?) -> Unit
) : PartShadowPopupView(context) {

    private lateinit var mAdapter: BaseRecyclerViewAdapter<SelectResultParentBean>

    private var mTxtSelectCount: AppCompatTextView? = null
    private var mTxtCloseAll: AppCompatTextView? = null
    private var mRecyclerView: RecyclerView? = null

    private val mData = mutableListOf<SelectResultParentBean>()
    private var mCount = 0

    override fun getImplLayoutId(): Int {
        return R.layout.layout_location_select_dailog
    }

    fun loadDialog(): LocationBottomDialog {
        initView()
        setSelectCount(0)
        return this
    }

    fun setData(list: MutableList<SelectResultParentBean>) {
        mData.clear()
        mData.addAll(list)
        mAdapter.notifyDataSetChanged()
    }

    /**
     * 设置已选省市的数量
     */
    fun setSelectCount(count: Int) {
        mCount = count
        updateCount()
    }

    /**
     * 更新已选数据的数量
     */
    private fun updateCount() {
        mTxtSelectCount!!.text = "已选省市：$mCount"
    }

    private fun initView() {
        mTxtSelectCount = findViewById<View>(R.id.txt_select_count) as AppCompatTextView
        mTxtCloseAll = findViewById<View>(R.id.txt_close_all) as AppCompatTextView
        mRecyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)


        mAdapter = BaseRecyclerViewAdapter(
            context,
            mData,
            R.layout.layout_location_select_dialog_item
        ) { rootView, dataItem, pos ->
            rootView.findViewById<TextView>(R.id.txt_name).text = dataItem.name
            val rv = rootView.findViewById<RecyclerView>(R.id.recycler_view_flex)
            rv.layoutManager = FlexboxLayoutManager(context)

            val adapter = BaseRecyclerViewAdapter(
                context,
                dataItem.result,
                R.layout.layout_location_select_district_dialog_item
            ) { innerView, innerData, innerPos ->

                innerView.findViewById<AppCompatButton>(R.id.btn_body).text = innerData.district
                innerView.findViewById<AppCompatImageView>(R.id.iv_close).setOnClickListener {
                    if (mData[pos].result.size <= 1) {
                        mData.removeAt(pos)
                    } else {
                        mData[pos].result.removeAt(innerPos)
                    }
                    mAdapter.notifyDataSetChanged()
                    mCount--
                    updateCount()
                    onDelClick(false, innerData)
                }
            }
            rv.adapter = adapter
        }
        mRecyclerView!!.adapter = mAdapter

        findViewById<TextView>(R.id.txt_close_all).setOnClickListener {
            if (mData.isEmpty()) {
                return@setOnClickListener
            }
            onDelClick(true, null)
            mCount = 0
            updateCount()
            mData.clear()
            mAdapter.notifyDataSetChanged()
        }
    }
}