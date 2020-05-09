package com.wuc.location.adapter

/**
 * @author : wuchao5
 * @date : 2020/5/3 18:14
 * @desciption :
 */

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Descriptions：
 * <p>
 * Author：ChenME
 * Date：2020/5/1
 * Email：ibelieve1210@163.com
 */
class BaseRecyclerViewAdapter<T>(private val context: Context, private var data: MutableList<T>,
                                 private var layoutId: Int, private val init: (View, T, Int) -> Unit) :
        RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder<T>>() {

    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null
    private var showCount = -1
    private var lastClickTime = 0L
    var SPACE_TIME = 1000
    private var clickPosition = -1

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindForest(data[position], position)
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener { v ->
                if (clickPosition != position) {
                    clickPosition = position
                    lastClickTime = System.currentTimeMillis()
                    mOnItemClickListener!!.onItemClick(v = v, position = position)
                } else {
                    val currTime = System.currentTimeMillis()
                    if (currTime - lastClickTime > SPACE_TIME) {
                        lastClickTime = System.currentTimeMillis()
                        mOnItemClickListener!!.onItemClick(v = v, position = position)
                    }
                }
            }
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener { v ->
                mOnItemLongClickListener!!.onItemLongClick(v = v, position = position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return BaseViewHolder(mInflater.inflate(layoutId, parent, false), init)
    }

    override fun getItemCount(): Int = if (showCount > -1 && showCount <= data.size) showCount else data.size

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener
    }

    fun setData(data: MutableList<T>) {
        this.data.clear()
        addData(data)
    }

    fun getDate( ):MutableList<T> {
        return data
    }

    fun addData(data: MutableList<T>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setShowCount(count: Int) {
        if (count >= 0) {
            this.showCount = count
            notifyDataSetChanged()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(v: View, position: Int): Boolean
    }

    class BaseViewHolder<in T>(v: View, private val init: (View, T, Int) -> Unit) : RecyclerView.ViewHolder(v) {
        fun bindForest(item: T, position: Int): Unit {
            with(item) {
                return init(itemView, item, position)
            }
        }
    }
}