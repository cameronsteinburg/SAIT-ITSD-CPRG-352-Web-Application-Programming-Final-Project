package servlets;

import businesslogic.NoteService;
import businesslogic.UserService;
import domainmodel.Note;
import domainmodel.User;
import dataaccess.NoteDB;
import dataaccess.NotesDBException;
import dataaccess.UserDBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NotesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        NoteService ns = new NoteService();
        String action = request.getParameter("action");
        String username = (String) session.getAttribute("username");

        if (action != null && action.equals("view")) {

            try {
                int selectedNoteId = Integer.parseInt(request.getParameter("editselect"));
                User curuser = (User) session.getAttribute("curuser");
                NoteDB ndb = new NoteDB();
                Note gotnote = ndb.getNote(selectedNoteId);

                if (gotnote.getOwner().getUsername() == curuser.getUsername()) { //check for spoofing

                    Note notes = ns.get(selectedNoteId);
                    request.setAttribute("selectedNote", notes);

                } else {
                    throw new ServletException();
                }

            } catch (NotesDBException ex) {

                ex.printStackTrace();
                throw new ServletException();
            }
            request.setAttribute("message", "Edit Note Below");
        }

        try {
            List<Note> notess = ns.getAll();

            ArrayList<Note> acceptable = new ArrayList();

            for (int i = 0; i < notess.size(); i++) {

                if (notess.get(i).getOwner().getUsername().equals(username)) {
                    acceptable.add(notess.get(i));
                }
            }

            request.setAttribute("notess", acceptable);
            
            UserService us = new UserService();
            User user = us.get((String) session.getAttribute("username")); //current user
            ArrayList<Note> publicNotes = NoteService.getPublicNotes(user);
            request.setAttribute("publicNotes", publicNotes);
            
            
            getServletContext().getRequestDispatcher("/WEB-INF/notes/notes.jsp").forward(request, response);
            return;

        } catch (Exception ex) {

            ex.printStackTrace();
            throw new ServletException();
        }

        //getServletContext().getRequestDispatcher("/WEB-INF/notes/notes.jsp").forward(request, response);
        //return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        String action = request.getParameter("action");
        String noteId = request.getParameter("noteId");
        String contents = request.getParameter("contents");
        String title = request.getParameter("title");
        boolean visibility = request.getParameter("publicOrPrivate") != null;

        int noteIdInt = -1;

        if (noteId != null) {
            noteIdInt = Integer.parseInt(noteId);
        }

        NoteService ns = new NoteService();
        UserService us = new UserService();

        if (contents != "" && title != "") {

            try {

                User user = us.get(username);
                
                if (action.equals("delete")) {

                    int selectedNoteId = Integer.parseInt(request.getParameter("deleteselect"));
                    
                    User curuser = (User) session.getAttribute("curuser");
                    
                    NoteDB ndb = new NoteDB();
                    Note gotnote = ndb.getNote(selectedNoteId);

                    if (gotnote.getOwner().getUsername() == curuser.getUsername()) { //check for spoofing

                        ns.delete(selectedNoteId);
                        request.setAttribute("message", "Note Successfully Deleted!");

                    } else {
                        throw new ServletException();
                    }

                } else if (action.equals("edit")) {

                    int selectedNotes = Integer.parseInt(request.getParameter("editor"));

                    List<Note> AuthenticateUserForNotes = (List<Note>) user.getNoteCollection();

                    for (int i = 0; i < AuthenticateUserForNotes.size(); i++) {

                        if (selectedNotes == AuthenticateUserForNotes.get(i).getNoteID()) {

                            ns.update(AuthenticateUserForNotes.get(i), request.getParameter("title"), request.getParameter("contents"));
                            request.setAttribute("message", "Note Successfully Edited!");
                        }
                    }

                } else if (action.equals("add")) {
                    
                    ns.insert(title, contents, username, visibility);
                    request.setAttribute("message", "Note Successfully Added!");
                }
            } catch (NotesDBException ex) {

                ex.printStackTrace();
                request.setAttribute("message", "Whoops.  Could not perform that action.");

            } catch (UserDBException ex) {

                ex.printStackTrace();
                request.setAttribute("message", "Whoops.  Could not perform that action.");
            }

        } else {
            request.setAttribute("message", "Whoops.  Can't enter blank data, my dude");
        }

        List<Note> notes = null;

        try {
            notes = (List<Note>) ns.getAll();
        } catch (NotesDBException ex) {

            ex.printStackTrace();
            throw new ServletException();
        }

        try {

            List<Note> notess = ns.getAll();

            ArrayList<Note> acceptable = new ArrayList();

            for (int i = 0; i < notess.size(); i++) {

                if (notess.get(i).getOwner().getUsername().equals(username)) {
                    acceptable.add(notess.get(i));
                }
            }

            request.setAttribute("notess", acceptable);
            
           
            User user = us.get((String) session.getAttribute("username")); //current user
            ArrayList<Note> publicNotes = NoteService.getPublicNotes(user);
            request.setAttribute("publicNotes", publicNotes);

            getServletContext().getRequestDispatcher("/WEB-INF/notes/notes.jsp").forward(request, response);
            return;

        } catch (Exception ex) {

            ex.printStackTrace();
            request.setAttribute("message", "Whoops.  Could not perform that action.");
            throw new ServletException();
            
        }
    }
}
