package com.liezh.onerecord.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.liezh.onerecord.R;
import com.liezh.onerecord.core.extend.click.SingleClick;
import com.liezh.onerecord.databinding.NoteItemBinding;
import com.liezh.onerecord.domian.vo.Note;

import java.util.ArrayList;
import java.util.List;


public class NoteItemAdapter extends RecyclerView.Adapter<NoteItemAdapter.NoteItemViewHolder> {

    private ArrayList<Note> mList;

    private Context mContext;

    private final LayoutInflater mInflater;

    private NoteItemCallBackListener mListener;

    public NoteItemAdapter(@NonNull Context context, @NonNull ArrayList<Note> mList) {
        this.mContext = context;
        this.mList = mList;
        mInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public interface NoteItemCallBackListener {
        @SingleClick
        void onItemSingleClick(Note note);
    }


    @Override
    public NoteItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater.from(mContext)
        NoteItemBinding noteItemBinding = DataBindingUtil.inflate(mInflater, R.layout.note_item, parent, false);
        return new NoteItemViewHolder(noteItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteItemViewHolder holder, int position) {
        Note note = mList.get(position);
        holder.mBinding.setNote(mList.get(position));
        holder.mBinding.executePendingBindings();
        // TODO 在这里完成item事件绑定
        holder.itemView.setOnClickListener((view) -> {
            if (mListener != null) {
                mListener.onItemSingleClick(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }




    static class NoteItemViewHolder extends RecyclerView.ViewHolder {
        public NoteItemBinding mBinding;

        public NoteItemViewHolder(NoteItemBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }

    /**
     * 设置RecyclerView监听器
     * @param mListener
     */
    public void setmListener(NoteItemCallBackListener mListener) {
        this.mListener = mListener;
    }

    public void addAll(List<Note> notes) {
        this.mList.addAll(notes);
        notifyItemInserted(notes.size());
    }

    public void remove() {
        if (mList.size() == 0) {
            return;
        }
        mList.remove(0);
        notifyItemRemoved(0);
    }
}
