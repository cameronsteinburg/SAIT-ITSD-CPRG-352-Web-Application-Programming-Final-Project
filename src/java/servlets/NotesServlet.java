package servlets;

import businesslogic.NoteService;
import businesslogic.UserService;
import domainmodel.Note;
import domainmodel.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

            int selectedNoteId = Integer.parseInt(request.getParameter("editselect"));

            try {
                Note notes = ns.get(selectedNoteId);
                request.setAttribute("selectedNote", notes);

            } catch (Exception ex) {
                Logger.getLogger(NotesServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            getServletContext().getRequestDispatcher("/WEB-INF/notes/notes.jsp").forward(request, response);
            return;

        } catch (Exception ex) {
            Logger.getLogger(NotesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // }
        getServletContext().getRequestDispatcher("/WEB-INF/notes/notes.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        String action = request.getParameter("action");
        String noteId = request.getParameter("noteId");
        String contents = request.getParameter("contents");
        String title = request.getParameter("title");

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
                    ns.delete(selectedNoteId);
                    request.setAttribute("message", "Note Successfully Deleted!");

                } else if (action.equals("edit")) {

                    int selectedNotes = Integer.parseInt(request.getParameter("editor"));

                    List<Note> AuthenticateUserForNotes = (List<Note>) user.getNoteCollection();

                    for (int i = 0; i < AuthenticateUserForNotes.size(); i++) {

                        if (selectedNotes == AuthenticateUserForNotes.get(i).getNoteID()) {

                            ns.update(AuthenticateUserForNotes.get(i), request.getParameter("title"), request.getParameter("contents"));
                            //session.setAttribute("currentUser", us.get((String) session.getAttribute("username")));
                            request.setAttribute("message", "Note Successfully Edited!");
                        }
                    }

                } else if (action.equals("add")) {
                    ns.insert(title, contents, username);
                }
            } catch (Exception ex) {
                request.setAttribute("message", "Whoops.  Could not perform that action.");
                ex.printStackTrace();
            }
            
        } else {
            request.setAttribute("message", "Whoops.  Can't enter blank data, my dude");
        }

        List<Note> notes = null;

        try {
            notes = (List<Note>) ns.getAll();
        } catch (Exception ex) {
            Logger.getLogger(NotesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        try { //reload
            List<Note> notess = ns.getAll();

            ArrayList<Note> acceptable = new ArrayList();

            for (int i = 0; i < notess.size(); i++) {

                if (notess.get(i).getOwner().getUsername().equals(username)) {
                    acceptable.add(notess.get(i));
                }
            }

            request.setAttribute("notess", acceptable);

            getServletContext().getRequestDispatcher("/WEB-INF/notes/notes.jsp").forward(request, response);
            return;

        } catch (Exception ex) {
            Logger.getLogger(NotesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
