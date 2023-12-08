package software.ulpgc.kata4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.System.load;

public class SqliteTrackLoader implements TrackLoader {

    private final Connection connection;

    public SqliteTrackLoader(Connection connection) {
        this.connection = connection;
    }

    public static TrackLoader with(Connection connection) {
        return new SqliteTrackLoader(connection);
    }

    @Override
    public List<Track> loadAll() {
        try {
            return load(querryAll());
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    private List<Track> load(ResultSet resultSet) throws SQLException {
        List<Track> list = new ArrayList<>();
        while (resultSet.next())
            list.add(trackFrom(resultSet));
        return list;
    }

    private Track trackFrom(ResultSet resultSet) throws SQLException {
        return new Track(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("website"),
                resultSet.getString("country"),
                resultSet.getString("description"),
                resultSet.getInt("founded"),
                resultSet.getString("industry"),
                resultSet.getInt("numberOfEmployees")
        );
    }

    private final static String QueryAll = "SELECT id, name, website, country, description, founded, " +
            "industry, numberOfEmployees FROM organizations";

    private ResultSet querryAll() throws SQLException {
        return connection.createStatement().executeQuery(QueryAll);
    }
}
