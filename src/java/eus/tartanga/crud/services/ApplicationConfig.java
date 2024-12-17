/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api") // Define el prefijo de la URL base para todos los recursos
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        // Registrar los recursos aquí
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Registra todas las clases de recursos RESTful.
     * Este método es útil para mantener un registro centralizado de recursos.
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