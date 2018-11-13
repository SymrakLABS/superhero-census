import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Servlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private SuperheroController superheroController;

    @Override
    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

        superheroController = new SuperheroController(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath(); //servlets 3.0

        try {
            switch (action) {
                case "/new":
                    showNewForm(req, resp);
                    break;
                case "/insert":
                    insert(req, resp);
                    break;
                case "/delete":
                    delete(req, resp);
                    break;
                case "/edit":
                    showEditForm(req, resp);
                    break;
                case "/update":
                    update(req, resp);
                    break;
                default:
                    list(req, resp);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp) //final блок for DB, flush
            throws SQLException, IOException, ServletException {

        String name = req.getParameter("name");
        String universe = req.getParameter("universe");
        int power = Integer.parseInt(req.getParameter("power"));
        String description = req.getParameter("description");
        String alive = req.getParameter("alive");

        SuperheroModel newSuperhero = new SuperheroModel(name, universe, power, description, alive);
        superheroController.insert(newSuperhero);
        resp.sendRedirect("list");
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {
        List<SuperheroModel> listSuperhero = superheroController.listAllSuperhero();
        req.setAttribute("listSuperhero", listSuperhero );
        RequestDispatcher dispatcher = req.getRequestDispatcher("SuperheroList.jsp");
        dispatcher.forward(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        SuperheroModel superhero = new SuperheroModel(id);
        superheroController.delete(superhero);
        resp.sendRedirect("list");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        SuperheroModel existingSuperhero = superheroController.getSuperhero(id);
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        req.setAttribute("superhero", existingSuperhero);
        dispatcher.forward(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String universe = req.getParameter("universe");
        int  power = Integer.parseInt(req.getParameter("power"));
        String description = req.getParameter("description");
        String alive = req.getParameter("alive");
        SuperheroModel superhero = new SuperheroModel(id, name, universe, power, description, alive );
        superheroController.update(superhero);
        resp.sendRedirect("list");

    }
}

