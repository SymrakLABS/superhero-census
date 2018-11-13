import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuperheroController {

    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    public SuperheroController(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
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
        System.out.print(superhero.toString());
        String sql = "INSERT INTO mysql.superheroes_table (name, universe, power, description, alive) VALUES (" + superhero.toString() + ")";
        Statement statement = jdbcConnection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
        disconnect();
    }

    public List<SuperheroModel> listAllSuperhero() throws SQLException {
        List<SuperheroModel> listSuperhero = new ArrayList<>();

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



}
