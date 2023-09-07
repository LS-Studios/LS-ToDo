package de.check24.dualesstudium.stubbe.todo.service;

public interface TodoDatabaseLayerInterface extends TodoDataLayerInterface {

    boolean createTableIfNotExist();

}
