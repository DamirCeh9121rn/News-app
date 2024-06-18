package com.example.veb_projekat;

import com.example.veb_projekat.filters.AuthFilter;
import com.example.veb_projekat.filters.CorsFilter;
import com.example.veb_projekat.repository.*;
import com.example.veb_projekat.repository.implementation.*;
import com.example.veb_projekat.service.*;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication(){
        // Ukljucujemo validaciju
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        // Definisemo implementacije u dependency container-u
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                // MySql
                this.bind(CategoryRepositoryImpl.class).to(CategoryRepository.class).in(Singleton.class);
                this.bind(CommentRepositoryImpl.class).to(CommentRepository.class).in(Singleton.class);
                this.bind(NewsRepositoryImpl.class).to(NewsRepository.class).in(Singleton.class);
                this.bind(NewsTagRepositoryImpl.class).to(NewsTagRepository.class).in(Singleton.class);
                this.bind(TagRepositoryImpl.class).to(TagRepository.class).in(Singleton.class);
                this.bind(UserRepositoryImpl.class).to(UserRepository.class).in(Singleton.class);
                this.bindAsContract(CategoryService.class);
                this.bindAsContract(CommentService.class);
                this.bindAsContract(NewsService.class);
                this.bindAsContract(TagService.class);
                this.bindAsContract(UserService.class);

            }
        };

        register(binder);
        register(AuthFilter.class);
        register(CorsFilter.class);
        packages("com.example.veb_projekat.resourse");
    }
}