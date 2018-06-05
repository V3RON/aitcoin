package pl.aitwar.aitcoin.dictionary;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Calendar;

public class Block {
    private Timestamp timestamp;
    private String hash;
    private String prevHash;
    private String data;

    public Block() { }

    public Block(String data) {
        this.setTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        this.setData(data);
        this.setPrevHash(null);
    }

    public static String computeHash(Block b) {
        String s = b.getTimestamp() + b.getPrevHash() + b.getData();

        MessageDigest digest;
        String hashedString = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedByte = digest.digest(s.getBytes(StandardCharsets.UTF_8));
            hashedString = Base64.getEncoder().encodeToString(hashedByte);
        } catch (NoSuchAlgorithmException e) {
            // Silent throw - exception cannot be achieved
        }

        return hashedString;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public String getData() {
        return data;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Block assemble() {
        this.setHash(computeHash(this));
        return this;
    }

    @Override
    public String toString() {
        return this.getPrevHash()+" "+this.getHash()+" "+this.getTimestamp()+" "+this.getData();
    }
}
