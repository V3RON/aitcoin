package pl.aitwar.aitcoin;

import pl.aitwar.aitcoin.dictionary.Block;
import pl.aitwar.aitcoin.dictionary.Blockchain;
import pl.aitwar.aitcoin.model.Database;
import pl.aitwar.aitcoin.service.BlockService;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException {
        Database.getInstance();
        Blockchain bc = new Blockchain(BlockService.getBlocks());
        if(!bc.isValid()) {
            logger.log(Level.SEVERE, "Blockchain is not valid!");
            return;
        }
        bc.add(new Block("K"));
        BlockService.updateBlocks(bc.getAll());
    }
}
