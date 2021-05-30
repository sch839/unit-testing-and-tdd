package com.acme.banking.dbo.service;

import com.acme.banking.dbo.domain.Account;
import com.acme.banking.dbo.domain.Cash;
import com.acme.banking.dbo.domain.Client;
import com.acme.banking.dbo.domain.SavingAccount;
import com.acme.banking.dbo.repository.ClientRepository;

import java.util.Collection;

public class Processing {
    private ClientRepository clients;

    //DI
    public Processing(ClientRepository clients) {
        this.clients = clients;
    }

    public Client createClient(Client client) {
        if (client == null) throw new IllegalArgumentException("client!");
        if (client.getName() == null || client.getName().isEmpty()) throw new IllegalArgumentException("name!");

        return clients.save(client);
    }

    public Collection<Account> getAccountsByClientId(int clientId) {
        return null; //TODO
    }

    public void transfer(int fromAccountId, int toAccountId, double amount) {
        Account from = clients.findById(fromAccountId);
        Account to = clients.findById(toAccountId);

        Account updatedFrom = new SavingAccount(
                from.getId(),
                from.getClient(),
                from.getAmount() - amount
            );

        Account updatedTo = new SavingAccount(
                to.getId(),
                to.getClient(),
                to.getAmount() + amount
        );

        clients.update(updatedFrom);
        clients.update(updatedTo);
    }

    public void cash(double amount, int fromAccountId) {
        Cash.log(amount, fromAccountId);
    }
}
