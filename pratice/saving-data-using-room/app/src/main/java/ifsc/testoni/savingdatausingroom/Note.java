package ifsc.testoni.savingdatausingroom;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @NonNull
    public String title;

    @NonNull
    public String text;

    public Note(@NonNull String title, @NonNull String text) {
        this.title = title;
        this.text = text;
    }
}
