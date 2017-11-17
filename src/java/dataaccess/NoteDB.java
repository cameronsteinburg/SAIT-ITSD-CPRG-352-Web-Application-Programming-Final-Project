package dataaccess;

import domainmodel.Note;
import domainmodel.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class NoteDB {


    
    public int insert(Note note) throws NotesDBException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
     
        try {
            trans.begin();
            em.persist(note);
            trans.commit();
            em.close();
            return 1;
        
        } finally {
            em.close();
        }
    } 
    
    public int delete(Note notes) throws NotesDBException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {

            trans.begin();
            em.remove(em.merge(notes));
            trans.commit();
            return 1;

        } catch (Exception ex) {

            trans.rollback();
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot delete " + notes.toString(), ex);
            throw new NotesDBException("Error deleting note");

        } finally {
            em.close();
        }
    }

    public int update(Note notes) throws NotesDBException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        trans.begin();
        em.merge(notes);
        trans.commit();
        return 1;
    }

    public List<Note> getAll() throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {

            List<Note> notes = em.createNamedQuery("Note.findAll", Note.class).getResultList();
            return notes;

        } catch (Exception ex) {

            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot read notes", ex);
            throw new NotesDBException("Error getting Notes");

        } finally {
            em.close();
        }
    }

    public Note getNote(int noteId) throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {

            Note notes = em.find(Note.class, noteId);
            return notes;

        } catch (Exception ex) {

            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot read notes", ex);
            throw new NotesDBException("Error getting Notes");

        } finally {
            em.close();
        }
    }

    public static java.sql.Date toSQlDate(java.util.Date date) {

        long javaDateMilisec = date.getTime();
        java.sql.Date sqlDate = new java.sql.Date(javaDateMilisec);
        return sqlDate;
    }

    public static java.util.Date toJavaDate(java.sql.Date date) {

        long sqlDateMilisec = date.getTime();
        java.util.Date javaDate = new java.util.Date(sqlDateMilisec);

        return javaDate;
    }
}
