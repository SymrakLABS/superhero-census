import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;




public class Servlet extends HttpServlet {

    private final static Logger log =
            Logger.getLogger(Servlet.class);


    private static final long serialVersionUID = 1L;
    private SuperheroController superheroController;
    private SuperheroValidator superheroValidator;


    @Override
    public void init() {
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

        superheroController = new SuperheroController(jdbcURL, jdbcUsername, jdbcPassword);
        superheroValidator = new SuperheroValidator();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String action = req.getServletPath(); //servlets 3.0



        try {
            if(action.equals("/new")){
                showNewForm(req, resp);
            } else if(action.equals("/insert")){
                insert(req, resp);
            } else if (action.equals("/delete")){
                delete(req, resp);
            } else if (action.equals("/edit")){
                showEditForm(req, resp);
            } else if(action.equals("/update")){
                update(req, resp);
            } else if(action.equals("/import")){
                superheroController.importJSON();

            } else if(action.equals("/export")){
                superheroController.exportToJSON();
            }
            else {
                list(req, resp);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServletException(e);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    private void insert(HttpServletRequest req, HttpServletResponse resp) //final блок for DB, flush
            throws  Exception {
        String name = req.getParameter("name");
        String universe = req.getParameter("universe");
        int power = Integer.parseInt(req.getParameter("power"));
        String description = req.getParameter("description");
        String alive = req.getParameter("alive");

        checkForInsert(req, resp, name, universe, power, description, alive);

        SuperheroModel newSuperhero = new SuperheroModel(name, universe, power, description, alive);
        superheroController.insert(newSuperhero);
        resp.sendRedirect("list");
        log.info("insert");
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
        log.info("delete superhero " + id);
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
            throws Exception {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String universe = req.getParameter("universe");
        int  power = Integer.parseInt(req.getParameter("power"));
        String description = req.getParameter("description");
        String alive = req.getParameter("alive");

        checkForUpdate(req, resp, name, universe, power, description, alive);

        SuperheroModel superhero = new SuperheroModel(id, name, universe, power, description, alive );
        superheroController.update(superhero);
        resp.sendRedirect("list");
        log.info("update superhero " + id);
    }

    private void checkForInsert(HttpServletRequest req, HttpServletResponse resp, String name, String universe,
                       int power, String description, String alive) throws Exception {
        try {
            superheroValidator.checkSuperhero(name, universe, power, description, alive);
            superheroController.checkUniquenessName(name);
        } catch (NotValidAliveException | NotValidUniverseException |
                NotValidPowerException | NotValidNameException | NotValidDescriptionException | NotUniquenessNameException exp){
            RequestDispatcher dispatcher = req.getRequestDispatcher("Error.jsp");
            dispatcher.forward(req, resp);
            log.error(exp);
            throw new Exception(exp);
        }
    }

    //todo при изменении существующего героя имя не уникально экзепшое придумать, как решшить
    private void checkForUpdate(HttpServletRequest req, HttpServletResponse resp, String name, String universe,
                       int power, String description, String alive) throws Exception {
        try {
            superheroValidator.checkSuperhero(name, universe, power, description, alive);
        } catch (NotValidAliveException | NotValidUniverseException |
                NotValidPowerException | NotValidNameException | NotValidDescriptionException exp){
            RequestDispatcher dispatcher = req.getRequestDispatcher("Error.jsp");
            dispatcher.forward(req, resp);
            log.error(exp);
            throw new Exception(exp);
        }
    }
}

