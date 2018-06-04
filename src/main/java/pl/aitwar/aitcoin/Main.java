package pl.aitwar.aitcoin;

import pl.aitwar.aitcoin.dictionary.Block;
import pl.aitwar.aitcoin.dictionary.Blockchain;
import pl.aitwar.aitcoin.model.Database;
import pl.aitwar.aitcoin.service.BlockService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Blockchain bc = new Blockchain(BlockService.getBlocks());
        bc.add(new Block("Kutaz"));
        BlockService.updateBlocks(bc.getAll());

    }
}
