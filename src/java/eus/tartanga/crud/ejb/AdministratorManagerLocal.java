package eus.tartanga.crud.ejb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import eus.tartanga.crud.entities.Administrator;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author 2dam
 */
@Local
public interface AdministratorManagerLocal {

    public void create(Administrator administrator) throws CreateException;

    public void update(Administrator administrator) throws UpdateException;

    public void remove(Administrator administrator) throws DeleteException;

    public Administrator find(String email) throws ReadException;

    public List<Administrator> findAll() throws ReadException;
}

