package eus.tartanga.crud.ejb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import eus.tartanga.crud.entities.Administrator;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author 2dam
 */
@Local
public interface AdministratorManagerLocal {

    public void create(Administrator administrator);

    public void update(Administrator administrator);

    public void remove(Administrator administrator);

    public Administrator find(String email);

    public List<Administrator> findAll();
}

