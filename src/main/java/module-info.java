module NewspaperFX {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    requires lombok;
    requires org.apache.logging.log4j;
    requires io.vavr;

    requires jakarta.inject;
    requires jakarta.annotation;
    requires jakarta.cdi;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires jakarta.xml.bind;

    exports gui.main;
    exports gui.main.common;
    exports gui.screens.main;
    exports gui.screens.common;
    exports gui.screens.login;
    exports gui.screens.welcome;
    exports gui.screens.newspapers_list;
    exports gui.screens.articles_list;
    exports gui.screens.articles_add;
    exports gui.screens.newspapers_delete;
    exports gui.screens.readers_list;
    exports gui.screens.readers_delete;
    exports gui.screens.subscriptions_list;
    exports gui.screens.ratings_list;
    exports gui.screens.ratings_add;

    exports dao;
    exports domain.services;
    exports domain.modelo;
    exports domain.type_adapters;
    exports configuration;

    opens domain.modelo;
    opens domain.type_adapters;
    opens configuration;
    opens gui.main;
    opens gui.screens.common;
    opens gui.screens.login;
    opens gui.screens.main;
    opens gui.screens.welcome;
    opens gui.screens.newspapers_list;
    opens gui.screens.newspapers_delete;
    opens gui.screens.articles_add;
    opens gui.screens.articles_list;
    opens gui.screens.readers_list;
    opens gui.screens.readers_delete;
    opens gui.screens.subscriptions_list;
    opens gui.screens.ratings_list;
    opens gui.screens.ratings_add;

    opens css;
    opens fxml;
    opens media;
    opens configs;
    exports dao.impl;
    exports domain.services.impl;
}
