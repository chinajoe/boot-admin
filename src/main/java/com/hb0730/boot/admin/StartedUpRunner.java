package com.hb0730.boot.admin;

import com.hb0730.boot.admin.commons.utils.FebsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartedUpRunner implements ApplicationRunner {
    private final ConfigurableApplicationContext context;
    private final Environment environment;

    @Override
    public void run(ApplicationArguments args) {
        if (context.isActive()) {
            FebsUtil.printSystemUpBanner(environment);
        }
    }
}
