import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import org.mortbay.util.ajax.JSON;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonFactory;

public class SuperheroController {

    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;
    private SuperheroValidator superheroValidator;

    public SuperheroController(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
        superheroValidator = new SuperheroValidator();

    }

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                    jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public void insert(SuperheroModel superhero) throws SQLException {
        connect();
        String sql = "INSERT INTO mysql.superheroes_table (name, universe, power, description, alive) VALUES (" + superhero.toString() + ")";
        Statement statement = jdbcConnection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
        disconnect();
    }

    public List<SuperheroModel> listAllSuperhero() throws SQLException {
        List<SuperheroModel> listSuperhero = new ArrayList<SuperheroModel>();

        String sql = "SELECT * FROM mysql.superheroes_table";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String universe = resultSet.getString("universe");
            int power = resultSet.getInt("power");
            String description = resultSet.getString("description");
            String alive = resultSet.getString("alive");

            SuperheroModel superhero= new SuperheroModel(id, name, universe, power, description, alive);
            listSuperhero.add(superhero);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return listSuperhero;
    }

    public boolean delete(SuperheroModel superhero) throws SQLException {
        String sql = "DELETE FROM mysql.superheroes_table where id = ?";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, superhero.getId());
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;
    }

    public SuperheroModel getSuperhero(int id) throws SQLException {
        SuperheroModel superhero = null;
        String sql = "SELECT * FROM mysql.superheroes_table WHERE id = ?";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String universe = resultSet.getString("universe");
            int power = resultSet.getInt("power");
            String description = resultSet.getString("description");
            String alive = resultSet.getString("alive");

            superhero = new SuperheroModel(id, name, universe, power, description, alive);
        }

        resultSet.close();
        statement.close();

        return superhero;
    }

    public ArrayList<String> getNames() throws SQLException{
        String sql = "SELECT name FROM mysql.superheroes_table";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<String> names = new ArrayList<String>();
        while (resultSet.next()){
            names.add(resultSet.getString("name"));
        }
        return names;
    }

    public void checkUniquenessName(String name) throws SQLException, NotUniquenessNameException{
        for(int i = 0; i < getNames().size(); i++){
            if(getNames().get(i).equals(name)){
                throw new NotUniquenessNameException();
            }
        }
    }

    public boolean update(SuperheroModel superhero) throws SQLException {
        String name = "'" + superhero.getName() + "'";
        String universe = "'" + superhero.getUniverse() + "'";
        int power = superhero.getPower();
        String description = "'" + superhero.getDescription() + "'";
        Boolean alive = superhero.isAlive();
        String sql = "UPDATE mysql.superheroes_table SET name = " + name + ", universe = " + universe + ", power = " + power + ", description = " + description + ", alive = " + alive + "  WHERE id = " + superhero.getId();
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated; //sql teampl
    }


    public void exportToJSON() throws SQLException, IOException{
        String sql = "SELECT * FROM mysql.superheroes_table";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        FileOutputStream fos = new FileOutputStream("E:\\fil.json");

        JsonGenerator jsonGenerator = new JsonFactory()
                .createGenerator(fos);
        jsonGenerator.writeStartObject();

        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String universe = resultSet.getString("universe");
            int power = resultSet.getInt("power");
            String description = resultSet.getString("description");
            String alive = resultSet.getString("alive");

            jsonGenerator.writeNumberField("id", id);
            jsonGenerator.writeStringField("name", name);
            jsonGenerator.writeStringField("universe", universe);
            jsonGenerator.writeNumberField("power", power);
            jsonGenerator.writeStringField("description", description);
            jsonGenerator.writeStringField("alive", alive);
        }
        jsonGenerator.close();
        disconnect();
    }

    public void importJSON() throws SQLException, IOException {
        JsonParser jsonParser = new JsonFactory().createParser(new File("E:\\fil.json"));
        SuperheroModel superheroModel;
        String name = "";
        String universe = "";
        int power = 0;
        String description = "";
        String alive;

        while(jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String currentName = jsonParser.getCurrentName();
            if ("id".equals(currentName)) {
                jsonParser.nextToken();
            } else if ("name".equals(currentName)) {
                jsonParser.nextToken();
                name = jsonParser.getText();
            } else if ("universe".equals(currentName)) {
                jsonParser.nextToken();
                universe = jsonParser.getText();
            } else if ("power".equals(currentName)) {
                jsonParser.nextToken();
                power = jsonParser.getIntValue();
            } else if ("description".equals(currentName)) {
                jsonParser.nextToken();
                description = jsonParser.getText();
            } else if ("alive".equals(currentName)) {
                jsonParser.nextToken();
                alive = jsonParser.getText();
                superheroModel = new SuperheroModel(name, universe, power, description, alive);
                String sql = "INSERT INTO mysql.superheroes_table (name, universe, power, description, alive) VALUES (" + superheroModel.toString() + ")";
                connect();
                Statement statement = jdbcConnection.createStatement();
                statement.executeUpdate(sql);
                statement.close();
            }
      }
      disconnect();
    }
}
