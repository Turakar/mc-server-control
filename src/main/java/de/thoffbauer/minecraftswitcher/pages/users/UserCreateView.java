package de.thoffbauer.minecraftswitcher.pages.users;

import de.thoffbauer.minecraftswitcher.MinecraftController;
import de.thoffbauer.minecraftswitcher.auth.User;
import de.thoffbauer.minecraftswitcher.pages.BaseView;

public class UserCreateView extends BaseView {

    private User user;
    private MinecraftController minecraftController = MinecraftController.getInstance();

    protected UserCreateView(User user, String error) {
        super("userCreate.ftl", error);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public MinecraftController getMinecraftController() {
        return minecraftController;
    }
}
