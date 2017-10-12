package de.thoffbauer.minecraftswitcher.pages;

import com.codahale.metrics.MetricRegistryListener;
import io.dropwizard.views.View;

public class BaseView extends View {

    private String error;

    public BaseView(String template, String error) {
        super(template);
        this.error = error;
    }

    public BaseView(String template) {
        this(template, null);
    }

    public String getError() {
        return error;
    }
}
