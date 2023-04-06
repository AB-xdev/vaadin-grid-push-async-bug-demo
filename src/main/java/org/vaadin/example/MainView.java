package org.vaadin.example;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Route
public class MainView extends VerticalLayout {

    private final Grid<DummyDTO> grid = new Grid<>();

    // Other grids could be here

    private final Button btn = new Button("Do stuff");

    public MainView() {

        grid.addColumn(new ComponentRenderer<>(dto -> new Anchor(dto.url(), dto.url())))
                .setHeader("URL");

        btn.addClickListener(ev -> doStuff());

        this.add(grid, btn);
    }

    private void doStuff() {
        hideAllGrids();

        final UI ui = UI.getCurrent();
        CompletableFuture.runAsync(() -> {
            // Simulate some work
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException iex) {
                // Do nothing
            }

            final Grid<DummyDTO> currentGrid = grid;

            ui.access(() -> currentGrid.setItems(List.of(new DummyDTO("https://xdev.software"))));

            ui.access(() -> {
                hideAllGrids();
                currentGrid.setVisible(true);
            });
        });
    }

    private void hideAllGrids()
    {
        grid.setVisible(false);
    }

    record DummyDTO(
            String url
    ) {
    }
}
