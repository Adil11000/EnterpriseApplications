package rajouai.adil.reservationplatform.user;

// Overkill for this project, but still good practice
public enum UserRole {
    USER("user", "ROLE_USER");

    private final String role;
    private final String[] authorities;

    UserRole(String role, String... authorities) {
        this.role = role;
        this.authorities = authorities;
    }

    public String getRole() {
        return role;
    }

    public String[] getAuthority() {
        return authorities;
    }
}
