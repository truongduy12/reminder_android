package com.example.reminder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reminder.R;
import com.example.reminder.models.Reminder;

import java.util.List;

public class CardReminderAdapter extends RecyclerView.Adapter<CardReminderAdapter.ViewHolder> {
    private List<Reminder> reminders;
    private Context context;

    public CardReminderAdapter(List<Reminder> reminders, Context context) {
        this.reminders = reminders;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View reminderView;
        reminderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_reminder, parent, false);
        return new ViewHolder(reminderView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardReminderAdapter.ViewHolder holder, int position) {
        String title = reminders.get(position).getTitle();
        String content = reminders.get(position).getContent();
        holder.reminderTitle.setText(title);
        holder.reminderContent.setText(content);
    }

    @Override
    public int getItemCount() {
        return reminders == null ? 0 : reminders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView reminderTitle;
        public TextView reminderContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reminderTitle = itemView.findViewById(R.id.reminderTitle);
            reminderContent = itemView.findViewById(R.id.reminderContent);
        }
    }
}
