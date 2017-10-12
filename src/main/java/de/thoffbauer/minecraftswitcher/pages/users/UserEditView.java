package de.thoffbauer.minecraftswitcher.pages.users;

import de.thoffbauer.minecraftswitcher.MinecraftController;
import de.thoffbauer.minecraftswitcher.auth.User;
import de.thoffbauer.minecraftswitcher.pages.BaseView;

public class UserEditView extends BaseView {

    private User user;
    private MinecraftController minecraftController;

    public UserEditView(User user) {
        super("userEdit.ftl");
        this.user = user;
        this.minecraftController = MinecraftController.getInstance();
    }

    public User getUser() {
        return user;
    }

    public MinecraftController getMinecraftController() {
        return minecraftController;
    }
}
