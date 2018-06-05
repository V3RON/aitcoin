package pl.aitwar.aitcoin.model;

import java.sql.*;
import java.util.logging.Logger;

public class Database {
    private static Database instance = null;
    private Connection c = null;
    private Logger logger = Logger.getLogger(Database.class.getName());

    private Database() throws SQLException {
        this.connect();
    }

    public static Database getInstance() throws SQLException {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:chain.db");
        } catch (ClassNotFoundException e) {
            // Silent throw - exception cannot be achieved (theoretically)
        }


        DatabaseMetaData dbm = c.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "blocks", null);
        if(!tables.next())
            this.generate();

        logger.info("Connected!");
    }

    public void isConnected() {
        //TODO
    }

    public void update(String sql) throws SQLException {
        Statement stmt = c.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }

    public Statement getStatement() throws SQLException {
        return c.createStatement();
    }

    public void close() throws SQLException {
        assert c != null;
        c.close();
    }

    private void generate() throws SQLException {
        Statement stmt = c.createStatement();
        stmt.executeUpdate(
                "CREATE TABLE blocks ("+
                "data varchar(32) not null,"+
                "prevHash varchar(32) null,"+
                "hash varchar(32) not null PRIMARY KEY,"+
                "timestamp timestamp not null"+
                ")"
        );
        stmt.close();

        logger.info("Tables have been generated!");
    }
}
