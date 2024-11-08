package hr.datastore.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static hr.datastore.DatastoreJavaFXApplication.StageReadyEvent;

@RequiredArgsConstructor
@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private final StageInitializerService stageInitializerService;
    @Override
    public void onApplicationEvent(final StageReadyEvent event) {
        this.stageInitializerService.getLoginScreen(event);
    }
}


