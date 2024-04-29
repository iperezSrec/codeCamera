package com.example.codecamera.api;

public class UserData{
        String name, role, phId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getPhId() {
            return phId;
        }

        public void setPhId(String phId) {
            this.phId = phId;
        }

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", phId='" + phId + '\'' +
                '}';
    }
}
