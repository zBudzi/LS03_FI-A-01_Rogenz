package model.persistence;

import toDo.User;

public interface IUserPersistance {

	User readUser(String username);

}