package module.connection.packaging;

import java.io.Serializable;

public class Packet implements Serializable {

    public static final int DATA_SIZE = 1024;  // объем передаваемых данных
    public static final int PACKAGE_SIZE = DATA_SIZE + 130; // такое количество байт занимает данный объект в сериализованном виде

    private final int serialNumber;
    private final int packagesAmount;

    private final byte[] data = new byte[DATA_SIZE];

    public Packet(int serialNumber, int packagesAmount) {
        this.serialNumber = serialNumber;
        this.packagesAmount = packagesAmount;
    }

    public Packet(byte[] data, int serialNumber, int packagesAmount) {
        assert data.length >= DATA_SIZE;

        System.arraycopy(data, 0, this.data, 0, DATA_SIZE);
        this.serialNumber = serialNumber;
        this.packagesAmount = packagesAmount;
    }

    public byte[] getData() {
        return data;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public int getPackagesAmount() {
        return packagesAmount;
    }
}
