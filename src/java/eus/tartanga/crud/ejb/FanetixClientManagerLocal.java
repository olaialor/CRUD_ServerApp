/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.ejb;

import eus.tartanga.crud.entities.FanetixClient;
import eus.tartanga.crud.exceptions.CreateException;
import eus.tartanga.crud.exceptions.DeleteException;
import eus.tartanga.crud.exceptions.ReadException;
import eus.tartanga.crud.exceptions.UpdateException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Irati
 */
@Local
public interface FanetixClientManagerLocal {

    public void createClient(FanetixClient client) throws CreateException;

    public void updateClient(FanetixClient client) throws UpdateException;

    public void removeClient(FanetixClient client) throws DeleteException;

    public FanetixClient findClient(String email) throws ReadException;

    public List<FanetixClient> findAllClients() throws ReadException;

    public FanetixClient signIn(String email, String passwd) throws ReadException;
}
