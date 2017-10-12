package de.thoffbauer.minecraftswitcher.auth;

public class Authorizer implements io.dropwizard.auth.Authorizer<User> {

    public static final String ROLE_USER = "user";
    public static final String ROLE_ADMIN = "admin";

    @Override
    public boolean authorize(User principal, String role) {
        switch (role) {
            case ROLE_ADMIN:
                return principal.isAdmin();
            case ROLE_USER:
                return true;
            default:
                return false;
        }
    }
}
