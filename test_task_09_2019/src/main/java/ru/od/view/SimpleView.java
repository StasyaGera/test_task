package ru.od.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import ru.od.model.MainEntity;
import ru.od.repository.MainEntityRepository;

@SpringComponent
@SpringView(name = "")
@UIScope
public class SimpleView extends Panel implements View {
    private final MainEntityRepository mainEntityRepository;

    private final VerticalLayout rootLayout = new VerticalLayout();
    private final VerticalLayout dataLayout = new VerticalLayout();
    private final Button button = new Button("Показать дальше");

    private Logger logger = LoggerFactory.getLogger(SimpleView.class);

    private static int page = 0;
    public final static int pageSize = 20;

    @Autowired
    public SimpleView(MainEntityRepository mainEntityRepository) {
        rootLayout.addComponent(dataLayout);
        rootLayout.addComponent(button);
        setContent(rootLayout);
        this.mainEntityRepository = mainEntityRepository;
    }

    @Override
    public void attach() {
        super.attach();
        logger.debug("Enter");
        loadMore();
        button.addClickListener((Button.ClickListener) clickEvent -> loadMore());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        rootLayout.setSpacing(true);
        rootLayout.setMargin(true);
        setSizeFull();
        rootLayout.setSizeUndefined();
        rootLayout.setWidth("100%");
        dataLayout.setWidth("100%");
    }

    private void loadMore() {
        logger.debug("Loading page " + page);
        for (MainEntity mainEntity : mainEntityRepository.findAll(new PageRequest(page, pageSize))) {
            String format = String.format("Имя сущности %s", mainEntity.getName());
            logger.debug(format);
            dataLayout.addComponent(new Label("----------"));
            dataLayout.addComponent(new Label(format));
            dataLayout.addComponent(new Label(
                    String.format("Количество элементов подсущности %d", mainEntity.getSubEntities().size())));
            dataLayout.addComponent(new Label("----------"));
        }
        page++;
    }
}
