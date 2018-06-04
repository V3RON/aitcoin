package pl.aitwar.aitcoin.dictionary;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;

    public Blockchain() {
        generateChain();
    }

    public Blockchain(ArrayList<Block> bl) {
        if(bl.size() == 0) {
            generateChain();
            return;
        }
        this.chain = bl;
    }

    private Block createGenesis() {
        return new Block("").assemble();
    }

    public void add(Block b) {
        if(chain.size() > 0)
            b.setPrevHash(this.chain.get(chain.size()-1).getHash());
        this.chain.add(b.assemble());
    }

    public void add(ArrayList<Block> bl) {
        for(Block b : bl) {
            this.add(b);
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for(Block b : this.chain) {
            res.append(b.getPrevHash()).append(" ").append(b.getHash()).append(" ").append(b.getTimestamp()).append(" ")
                    .append(b.getData()).append("\n");
        }
        return res.toString();
    }

    public Block get() {
        return this.chain.get(chain.size()-1);
    }

    public Block get(int i) {
        return this.chain.get(i);
    }

    public void set(int i, Block b) {
        assert i > 1;
        b.setPrevHash(this.chain.get(i-1).getHash());
        this.chain.set(i, b.assemble());
    }

    public boolean isValid() {
        for(int i = this.chain.size()-1; i > 0; i--) {
            Block curr = this.chain.get(i);
            Block prev = this.chain.get(i-1);

            if(!curr.getHash().equals(Block.computeHash(curr))) {
                return false;
            }

            // Genesis block got null there so be aware of that
            if(prev != null && !curr.getPrevHash().equals(Block.computeHash(prev))) {
                return false;
            }
        }
        return true;
    }

    public int size() {
         return this.chain.size();
    }

    public ArrayList<Block> getAll() {
        return new ArrayList<Block>(this.chain);
    }

    private void generateChain() {
        chain = new ArrayList<Block>();
        chain.add(createGenesis());
    }
}
