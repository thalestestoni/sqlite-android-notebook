package ifsc.testoni.savingdatausingroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {
    private NoteViewModel mNoteViewModel;
    private EditText mNoteTitle;
    private EditText mNoteText;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        note = (Note) getIntent().getSerializableExtra("note");

        mNoteTitle = findViewById(R.id.note_title);
        mNoteText = findViewById(R.id.note_text);

        mNoteTitle.setText(note.title);
        mNoteText.setText(note.text);

        setOnClickListenerCancelButton();
        setOnClickListenerDeleteButton();
        setOnClickListenerSaveButton();

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_theme)));
    }

    private void setOnClickListenerSaveButton() {
        final Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(view -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage(R.string.confirm_action_message)
                            .setPositiveButton(R.string.confirm_action_yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String title = mNoteTitle.getText().toString();
                                    String text = mNoteText.getText().toString();

                                    note.title = title;
                                    note.text = text;

                                    mNoteViewModel.update(note);

                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.confirm_action_no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Nothing
                                }
                            });
            dialogBuilder.create().show();
        });
    }

    private void setOnClickListenerCancelButton() {
        final Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(view -> {
            finish();
        });
    }

    private void setOnClickListenerDeleteButton() {
        final Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage(R.string.confirm_action_message)
                            .setPositiveButton(R.string.confirm_action_yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mNoteViewModel.delete(note);
                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.confirm_action_no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Nothing
                                }
                            });
            dialogBuilder.create().show();
        });
    }
}