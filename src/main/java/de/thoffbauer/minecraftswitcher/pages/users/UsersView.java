package de.thoffbauer.minecraftswitcher.pages.users;

import de.thoffbauer.minecraftswitcher.auth.User;
import de.thoffbauer.minecraftswitcher.pages.BaseView;

import java.util.Collection;

public class UsersView extends BaseView {

    private Collection<User> users;

    public UsersView(Collection<User> users) {
        super("users.ftl");
        this.users = users;
    }

    public Collection<User> getUsers() {
        return users;
    }
}
