/*
 * Copyright 2016-2017 Andres Almiray
 *
 * This file is part of Java Trove Examples
 *
 * Java Trove Examples is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Java Trove Examples is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Java Trove Examples. If not, see <http://www.gnu.org/licenses/>.
 */
package org.kordamp.javatrove.example06;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import org.kordamp.javatrove.example06.controller.AppController;
import org.kordamp.javatrove.example06.impl.FITSupplier;
import org.kordamp.javatrove.example06.impl.GithubAPIProvider;
import org.kordamp.javatrove.example06.impl.GithubImpl;
import org.kordamp.javatrove.example06.impl.ObjectMapperProvider;
import org.kordamp.javatrove.example06.model.AppModel;
import org.kordamp.javatrove.example06.service.Github;
import org.kordamp.javatrove.example06.service.GithubAPI;
import org.kordamp.javatrove.example06.util.ApplicationEventBus;
import org.kordamp.javatrove.example06.util.ApplicationEventHandler;
import org.kordamp.javatrove.example06.view.AppView;
import ru.vyarus.guice.ext.ExtAnnotationsModule;

import javax.inject.Singleton;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static com.google.inject.name.Names.named;

/**
 * @author Andres Almiray
 */
public class AppModule extends ExtAnnotationsModule {
    public AppModule() {
        super(AppModule.class.getPackage().getName());
    }

    @Override
    protected final void configure() {
        super.configure();
        bindGithubApiUrl();
        bindExecutorService();
        bindGithub();
        bindObjectMapper();
        bindGithubAPI();
        bindApplicationEventBus();
        bindApplicationEventHandler();
        bindAppController();
        bindAppModel();
        bindAppView();
        bindFitSupplier();
    }

    protected void bindGithubApiUrl() {
        bindConstant()
            .annotatedWith(named(GithubAPI.GITHUB_API_URL_KEY))
            .to("https://api.github.com");
    }

    protected void bindExecutorService() {
        bind(ExecutorService.class)
            .toInstance(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    protected void bindGithub() {
        bind(Github.class)
            .to(GithubImpl.class)
            .in(Singleton.class);
    }

    protected void bindObjectMapper() {
        bind(ObjectMapper.class)
            .toProvider(ObjectMapperProvider.class)
            .in(Singleton.class);
    }

    protected void bindGithubAPI() {
        bind(GithubAPI.class)
            .toProvider(GithubAPIProvider.class)
            .in(Singleton.class);
    }

    protected void bindApplicationEventBus() {
        bind(ApplicationEventBus.class)
            .in(Singleton.class);
    }

    protected void bindApplicationEventHandler() {
        bind(ApplicationEventHandler.class)
            .asEagerSingleton();
    }


    protected void bindFitSupplier(){
        bind(String.class).annotatedWith(Names.named("groovy.fit.impl.file")).toInstance("./githubFIT.impl");
        bind(new TypeLiteral<Supplier<Github>>(){}).to(FITSupplier.class);
    }


    protected void bindAppController() {
        bind(AppController.class).in(Singleton.class);
    }

    protected void bindAppModel() {
        bind(AppModel.class).in(Singleton.class);
    }

    protected void bindAppView() {
        bind(AppView.class).in(Singleton.class);
    }
}
