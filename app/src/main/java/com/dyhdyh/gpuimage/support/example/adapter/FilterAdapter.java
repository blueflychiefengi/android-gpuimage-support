package com.dyhdyh.gpuimage.support.example.adapter;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dyhdyh.gpuimage.support.example.R;
import com.dyhdyh.gpuimage.support.example.model.FilterModel;
import com.dyhdyh.helper.checkable.MultipleCheckableAdapter;
import com.dyhdyh.helper.checkable.MultipleCheckableHelper;
import com.gcssloop.widget.CheckedRCRelativeLayout;

import java.util.List;

/**
 * @author dengyuhan
 *         created 2018/3/21 19:16
 */
public class FilterAdapter extends BaseRecyclerAdapter<FilterModel, RecyclerView.ViewHolder> implements MultipleCheckableAdapter<FilterModel> {
    private MultipleCheckableHelper mHelper;

    public FilterAdapter(List<FilterModel> data) {
        super(data);
        this.mHelper = new MultipleCheckableHelper<>(this);
    }

    @Override
    public int getItemViewType(int position) {
        return getData().get(position).isFilterGroupLabel() ? 1 : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_list, parent, false));
        } else {
            return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_header, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, FilterModel item) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == 0) {
            ItemHolder holder = (ItemHolder) viewHolder;
            holder.rlContainer.setChecked(item.isChecked());
            if (item.getCoverBitmap() == null) {
                holder.ivCover.setImageDrawable(new ColorDrawable(holder.itemView.getResources().getColor(R.color.colorLoading)));
            } else {
                holder.ivCover.setImageBitmap(item.getCoverBitmap());
            }
            holder.tvName.setText(item.getFilterNameRes());
            holder.tvProgress.setText(String.valueOf(item.getProgress()));
        } else {
            HeaderHolder holder = (HeaderHolder) viewHolder;
            holder.tvFilterGroup.setText(item.getFilterGroupNameRes());
        }

    }


    @Override
    public void setCheckedPositionArray(int[] checkedPositionArray, boolean checked) {
        mHelper.setCheckedPositionArray(checkedPositionArray, checked);
    }

    @Override
    public int[] getCheckedPositionArray() {
        return mHelper.getCheckedPositionArray();
    }

    @Override
    public List<FilterModel> getCheckedList() {
        return mHelper.getCheckedList();
    }

    @Override
    public void onAdapterNotifyChanged(int[] checkedPositionArray) {
        notifyDataSetChanged();
    }

    @Override
    public void onChecked(int checkedPosition, boolean checked) {
        getData().get(checkedPosition).setChecked(checked);
    }

    @Override
    public void clear() {
        mHelper.clear();
    }


    static class HeaderHolder extends RecyclerView.ViewHolder {
        TextView tvFilterGroup;

        public HeaderHolder(View itemView) {
            super(itemView);
            tvFilterGroup = itemView.findViewById(R.id.tv_filter_group);
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        CheckedRCRelativeLayout rlContainer;
        ImageView ivCover;
        TextView tvName;
        TextView tvProgress;

        public ItemHolder(View itemView) {
            super(itemView);
            rlContainer = itemView.findViewById(R.id.rl_container);
            ivCover = itemView.findViewById(R.id.iv_filter_cover);
            tvName = itemView.findViewById(R.id.tv_filter_name);
            tvProgress = itemView.findViewById(R.id.tv_filter_progress);
        }
    }
}
