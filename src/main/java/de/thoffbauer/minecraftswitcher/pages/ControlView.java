package de.thoffbauer.minecraftswitcher.pages;

import de.thoffbauer.minecraftswitcher.MinecraftController;
import de.thoffbauer.minecraftswitcher.auth.User;

public class ControlView extends BaseView {

    private User user;
    private MinecraftController minecraftController;

    public ControlView(User user, String error) {
        super("control.ftl", error);
        this.user = user;
        this.minecraftController = MinecraftController.getInstance();
    }

    public MinecraftController getMinecraftController() {
        return minecraftController;
    }

    public User getUser() {
        return user;
    }
}
