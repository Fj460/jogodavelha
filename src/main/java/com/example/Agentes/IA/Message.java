package com.example.Agentes.IA;


public class Message {
    private int x;
    private int y;
    private String type;
    private String roomCode;
    public String getRoomCode() { return roomCode; }
    public void setRoomCode(String roomCode) { this.roomCode = roomCode; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
}
