package businesslogic;

import dataaccess.NoteDB;
import dataaccess.NotesDBException;
import domainmodel.Note;
import domainmodel.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NoteService {

    private NoteDB noteDB;

    public NoteService() {
        noteDB = new NoteDB();
    }

    public Note get(int noteId) throws Exception {
        return noteDB.getNote(noteId);
    }

    public List<Note> getAll() throws Exception {
        return noteDB.getAll();
    }

    public int update(Note note, String title, String contents) {

        NoteDB noteDB = new NoteDB();

        note.setTitle(title);
        note.setContents(contents);
        note.setDateCreated(new java.util.Date());
        try {
            return noteDB.update(note);
        } catch (NotesDBException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int delete(int noteId) throws Exception {

        Note deletedNote = noteDB.getNote(noteId);
        return noteDB.delete(deletedNote);
    }

    public int insert(String title, String contents, String username) throws Exception {

        UserService us = new UserService();
        User user = us.get(username);
        java.util.Date date = new java.util.Date();
        Note note = new Note(-1, date, title, contents);

        note.setOwner(user);
        return noteDB.insert(note);
    }
}
