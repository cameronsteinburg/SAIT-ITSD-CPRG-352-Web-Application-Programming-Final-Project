package businesslogic;

import dataaccess.NoteDB;
import dataaccess.NotesDBException;
import dataaccess.UserDBException;
import domainmodel.Note;
import domainmodel.User;
import java.util.List;

public class NoteService {

    private NoteDB noteDB;

    public NoteService() {
        noteDB = new NoteDB();
    }

    public Note get(int noteId) throws NotesDBException {
        return noteDB.getNote(noteId);
    }

    public List<Note> getAll() throws NotesDBException {
        return noteDB.getAll();
    }

    public int update(Note note, String title, String contents) throws NotesDBException{

        NoteDB noteDB = new NoteDB();

        note.setTitle(title);
        note.setContents(contents);
        note.setDateCreated(new java.util.Date());
        
        try {
            
            return noteDB.update(note);
            
        } catch (NotesDBException ex) {
            
            ex.printStackTrace();
            throw new NotesDBException();
        }
    }

    public int delete(int noteId) throws NotesDBException {

        Note deletedNote = noteDB.getNote(noteId);
        return noteDB.delete(deletedNote);
    }

    public int insert(String title, String contents, String username) throws NotesDBException, UserDBException  {

        UserService us = new UserService();
        User user = us.get(username);
        java.util.Date date = new java.util.Date();
        Note note = new Note(-1, date, title, contents);

        note.setOwner(user);
        return noteDB.insert(note);
    }
}
