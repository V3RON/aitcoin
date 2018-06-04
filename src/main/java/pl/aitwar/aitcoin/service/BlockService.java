package pl.aitwar.aitcoin.service;

import pl.aitwar.aitcoin.dictionary.Block;
import pl.aitwar.aitcoin.model.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BlockService {
    public static ArrayList<Block> getBlocks() throws SQLException {
        ArrayList<Block> ret = new ArrayList<>();
        ResultSet rs = Database.getInstance().query("SELECT * FROM blocks ORDER BY timestamp");
        while(rs.next()) {
            Block b = new Block();
            b.setData(rs.getString("data"));
            b.setPrevHash(rs.getString("prevHash"));
            b.setHash(rs.getString("hash"));
            b.setTimestamp(rs.getTimestamp("timestamp"));
            ret.add(b);
        }
        return ret;
    }

    public static void updateBlocks(ArrayList<Block> toUpdate) throws SQLException {
        for(int i = 0; i < toUpdate.size(); i++) {
            Block b = toUpdate.get(i);
            Database.getInstance().update("INSERT OR IGNORE INTO blocks VALUES ("
                    +"\'"+b.getData()+"\',\'"+b.getPrevHash()+"\',\'"+b.getHash()+"\',\'"+b.getTimestamp()+"\')");
        }
    }
}
