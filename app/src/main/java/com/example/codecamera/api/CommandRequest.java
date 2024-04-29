package com.example.codecamera.api;

public class CommandRequest {
    private String commandName;
    private String deviceName;
    private String timestamp;

    public CommandRequest(String commandName, String deviceName, String timestamp) {
        this.commandName = commandName;
        this.deviceName = deviceName;
        this.timestamp = timestamp;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
