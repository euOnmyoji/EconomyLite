/*
 * This file is part of EconomyLite, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 - 2017 Flibio
 * Copyright (c) Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.flibio.economylite.api;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.service.economy.account.UniqueAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Interfaces directly with the storage system. Performs no checks, simply does
 * exactly what it is told.
 */
public interface PlayerEconService {

    /**
     * Gets the balance of a player.
     * 
     * @param uuid The UUID of the player to get the balance of.
     * @param currency The currency to use.
     * @param cause What is getting the balance.
     * @return The balance of the player.
     */
    public BigDecimal getBalance(UUID uuid, Currency currency, Cause cause);

    /**
     * Sets the balance of a player.
     * 
     * @param uuid The UUID of the player to set the balance of.
     * @param balance The new balance of the uuid.
     * @param currency The currency to use.
     * @param cause What caused the balance change.
     * @return If the player's balance was changed successfully.
     */
    public boolean setBalance(UUID uuid, BigDecimal balance, Currency currency, Cause cause);

    /**
     * Removes currency from a player's balance.
     * 
     * @param uuid The UUID of the player to remove currency from.
     * @param amount The amount of currency to remove.
     * @param currency The currency to use.
     * @param cause What caused the balance change.
     * @return If the player's balance was changed successfully.
     */
    default public boolean withdraw(UUID uuid, BigDecimal amount, Currency currency, Cause cause) {
        return setBalance(uuid, getBalance(uuid, currency, cause).subtract(amount), currency, cause);
    }

    /**
     * Adds currency to a player's balance.
     * 
     * @param uuid The UUID of the player to add currency to.
     * @param amount The amount of currency to add.
     * @param currency The currency to use.
     * @param cause What caused the balance change.
     * @return If the player's balance was changed successfully.
     */
    default public boolean deposit(UUID uuid, BigDecimal amount, Currency currency, Cause cause) {
        return setBalance(uuid, getBalance(uuid, currency, cause).add(amount), currency, cause);
    }

    /**
     * Checks if a player exists in the system with any currency.
     * 
     * @param uuid The UUID of the player to check for.
     * @param cause What is checking if the account exists.
     * @return If the player exists or not.
     */
    public boolean accountExists(UUID uuid, Cause cause);

    /**
     * Checks if a player exists in the system for the specified currency.
     * 
     * @param uuid The UUID of the player to check for.
     * @param currency The currency to check against.
     * @param cause What is checking if the account exists.
     * @return If the player exists or not.
     */
    public boolean accountExists(UUID uuid, Currency currency, Cause cause);

    /**
     * Clears a currency from the database.
     * 
     * @param currency The currency to clear.
     * @param cause What is clearing the balances.
     */
    public void clearCurrency(Currency currency, Cause cause);

    /**
     * Gets the top unique accounts registered in the EconomyLite system.
     * 
     * @param start The starting account to get.
     * @param end The ending account to get.
     * @param cause What is getting the accounts.
     * @return The top unique accounts registered in the EconomyLite system.
     */
    public List<UniqueAccount> getTopAccounts(int start, int end, Cause cause);

    /**
     * Sets the balance of all players.
     * 
     * @param balance The new balance.
     * @param currency The currency to use.
     * @param cause What caused the balance change.
     * @return If the balances were changed successfully.
     */
    public boolean setBalanceAll(BigDecimal balance, Currency currency, Cause cause);
}
