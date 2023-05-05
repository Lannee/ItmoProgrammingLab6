package src.utils.loggerModule;

import java.net.SocketAddress;

public class LogRecord {
    private SocketAddress clientAddress;
    private String logRecordMessage;
    private LogRecordType logRecordType;

    public LogRecord(SocketAddress aClientAddress, String alogRecordMessage, LogRecordType aLogRecordType) {
        clientAddress = aClientAddress;
        logRecordMessage = alogRecordMessage;
        logRecordType = aLogRecordType;
    }

    public LogRecord(String alogRecordMessage, LogRecordType aLogRecordType) {
        clientAddress = null;
        logRecordMessage = alogRecordMessage;
        logRecordType = aLogRecordType;
    }

    public SocketAddress getClientAddress() {
        return clientAddress;
    }

    public String getLogRecordMessage() {
        return logRecordMessage;
    }

    public LogRecordType getLogRecordType() {
        return logRecordType;
    }

    @Override
    public String toString() {
        if (clientAddress.equals(null)) {
            return logRecordMessage + " " + logRecordType.getDescription() + ".";
        } else {
            return clientAddress.toString() + " " + logRecordMessage + " " + logRecordType.getDescription() + ".";
        }
    }
}
