package pl.aitwar.aitcoin.service;

import pl.aitwar.aitcoin.dictionary.Block;
import pl.aitwar.aitcoin.model.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

public class BlockService {
    private static Logger logger = Logger.getLogger(BlockService.class.getName());

    public static ArrayList<Block> getBlocks() throws SQLException {
        ArrayList<Block> ret = new ArrayList<>();
        try(Statement stmt = Database.getInstance().getStatement()) {
            ResultSet rs = Database.getInstance().getStatement().executeQuery("SELECT * FROM blocks");
            while (rs.next()) {
                Block b = new Block();
                b.setData(rs.getString("data"));
                b.setPrevHash(rs.getString("prevHash"));
                b.setHash(rs.getString("hash"));
                b.setTimestamp(rs.getTimestamp("timestamp"));
                ret.add(b);
            }
        }

        logger.info("Blocks ("+ret.size()+") have been downloaded from database!");
        return ret;
    }

    public static void updateBlocks(ArrayList<Block> toUpdate) throws SQLException {
        for (Block b : toUpdate) {
            Database.getInstance().update("INSERT OR IGNORE INTO blocks VALUES ("
                    + "\'" + b.getData() + "\',\'" + b.getPrevHash() + "\',\'" + b.getHash() + "\',\'" + b.getTimestamp() + "\')");
        }

        logger.info("Blocks ("+toUpdate.size()+") have been saved to database!");
    }
}
