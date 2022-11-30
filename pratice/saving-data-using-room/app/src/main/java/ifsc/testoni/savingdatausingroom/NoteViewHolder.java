package ifsc.testoni.savingdatausingroom;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView noteTextView;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        noteTextView = itemView.findViewById(R.id.noteTextView);
    }

    public void bind(Note note) {
        noteTextView.setText(note.title);

        noteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditNoteActivity.class);
                intent.putExtra("note", note);
                itemView.getContext().startActivity(intent);
            }
        });
    }

    static NoteViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new NoteViewHolder(view);
    }
}
