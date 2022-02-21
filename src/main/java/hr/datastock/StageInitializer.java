package hr.datastock;

import hr.datastock.services.StageInitializerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static hr.datastock.DatastockJavaFXAplication.StageReadyEvent;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Autowired
    private StageInitializerService stageInitializerService;

    @Override
    public void onApplicationEvent(final StageReadyEvent event) {
        this.stageInitializerService.onStartOfLogin(event);
    }
}


