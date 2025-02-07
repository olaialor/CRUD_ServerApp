package eus.tartanga.crud.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api") // Defines the base URL prefix for all resources
public class ApplicationConfig extends Application {

    /**
     * Returns a set of classes that are part of the RESTful resources.
     * This method is used to maintain a centralized registry of resources.
     *
     * @return a set of classes to be included in the application
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        // Register resources here
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Registers all the RESTful resource classes. This method is useful for
     * keeping a centralized registry of the resources.
     *
     * @param resources a set of classes to be registered
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(eus.tartanga.crud.services.AdministratorFacadeREST.class);
        resources.add(eus.tartanga.crud.services.ArtistFacadeREST.class);
        resources.add(eus.tartanga.crud.services.CartFacadeREST.class);
        resources.add(eus.tartanga.crud.services.ConcertFacadeREST.class);
        resources.add(eus.tartanga.crud.services.FanetixClientFacadeREST.class);
        resources.add(eus.tartanga.crud.services.ProductFacadeREST.class);
    }
}
