package ee.vovtech.backend4cash.security;

public enum DbRole {
    USER, ADMIN;

    public boolean isAdmin() {
        return this == ADMIN;
    }

    public String toSpringRole() {
        return "ROLE_" + this.name();
    }
}
