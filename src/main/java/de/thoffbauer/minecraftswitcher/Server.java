package de.thoffbauer.minecraftswitcher;

import de.thoffbauer.minecraftswitcher.auth.Authenticator;
import de.thoffbauer.minecraftswitcher.auth.Authorizer;
import de.thoffbauer.minecraftswitcher.auth.User;
import de.thoffbauer.minecraftswitcher.pages.ControlPage;
import de.thoffbauer.minecraftswitcher.pages.users.UserCreatePage;
import de.thoffbauer.minecraftswitcher.pages.users.UserEditPage;
import de.thoffbauer.minecraftswitcher.pages.users.UsersPage;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.io.File;

public class Server extends Application<ServerConfig> {

    public static void main(String[] args) throws Exception {
        new Server().run(args);
    }

    @Override
    public void initialize(Bootstrap<ServerConfig> bootstrap) {
        bootstrap.addBundle(new ViewBundle<>());
        bootstrap.addBundle(new AssetsBundle("/static", "/static"));
    }

    @Override
    public void run(ServerConfig configuration, Environment environment) throws Exception {
        MinecraftController.setInstance(new MinecraftController(new File(configuration.getServerDir())));

        Authenticator authenticator = new Authenticator();
        authenticator.load();
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(authenticator)
                        .setAuthorizer(new Authorizer())
                        .setRealm("Restricted Access")
                        .buildAuthFilter()));

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        environment.jersey().register(new UsersPage(authenticator));
        environment.jersey().register(new UserCreatePage(authenticator));
        environment.jersey().register(new UserEditPage(authenticator));

        environment.jersey().register(new ControlPage());
    }

    @Override
    public String getName() {
        return "mc-server-control";
    }

}
