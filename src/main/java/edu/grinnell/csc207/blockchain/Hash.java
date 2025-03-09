package edu.grinnell.csc207.blockchain;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {

    private byte[] data;

    /**
     * @param data constructs a new Hash object that contains the given hash (as an array of bytes).
     */
    public Hash(byte[] data) {
        this.data = data;
    }

    /**
     * @return the hash contained in this object.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @return true if this hash meets the criteria for validity.
     */
    public boolean isValid() {
        if (data.length < 3) {
            return false;
        }
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            if (data[i] == 0) {
                counter++;
            }
        }
        if (counter == 3) {
            return true;
        }
        return false;
    }

    /**
     * @return the string representation of the hash as a string of hexadecimal digits.
     */
    public String toString() {
        String ret = "";
        for (int i = 0; i < data.length; i++) {
            ret += Byte.toUnsignedInt(data[i]);
        }
        return ret;
    }

    /**
     * @param other Object given to determine equality
     * @return true if this hash is structurally equal to the argument.
     */
    public boolean equals(Object other) {
        if (other instanceof Hash) {
            Hash o = (Hash) other;
            if (this.data.length != o.data.length) {
                return false;
            }
            int counter = 0;
            for (int i = 0; i < this.data.length; i++) {
                if (this.data[i] == o.data[i]) {
                    counter++;
                }
            }
            if (counter == this.data.length) {
                return true;
            }
        }
        return false;
    }
    
}
