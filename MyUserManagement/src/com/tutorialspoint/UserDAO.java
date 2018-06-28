package com.tutorialspoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	
	private static final String FILE_NAME = "Users4.dat";
	
	public List<User> getAllUsers(){
		List<User> userList = null;
		try{
			File file = new File(FILE_NAME);
			if (!file.exists()){
				User user1 = new User(1, "Kishore Sharma", "Developer");
				User user2 = new User(2, "Priyakshi Sharma", "Student");
				User user3 = new User(3, "Utkarsha Sharma", "Student");
				User user4 = new User(4, "Chandana Devi", "Student");
				userList = new ArrayList<User>();
				userList.add(user1);
				userList.add(user2);
				userList.add(user3);
				userList.add(user4);
				saveUserList(userList);
			}else{
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				userList = (List<User>) ois.readObject();
				ois.close();
			}				
		}catch (IOException e){
			e.printStackTrace();
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		return userList;
	}
	
	//Search an User with the id in the List of Users and return
	public User getUser(int id){
		List<User> userList = getAllUsers();
		for(User user : userList){
			if (id == user.getId())
				return user;
		}
		return null;
	}
	
	//Add a User to the List. Before adding check if the user already exist.
	public int addUser(User pUser){
		List<User> userList = getAllUsers();
		Boolean userExist = false;
		for (User user:userList){
			if (pUser.getId() == user.getId()){
				userExist = true;
				break;
			}				
		}
		if (!userExist){
			userList.add(pUser);
			saveUserList(userList);
			return 1;
		}
		return 0;
	}
	
	public int updateUser(User pUser){
		List<User> userList = getAllUsers();
		for (User user:userList){
			if (pUser.getId() == user.getId()){
				int index = userList.indexOf(user);
				userList.set(index, pUser);
				saveUserList(userList);
				return 1;
			}
		}
		return 0;
	}
	
	public int deleteUser(int id){
		List<User> userList = getAllUsers();
		for (User user:userList){
			if (id == user.getId()){
				int index = userList.indexOf(user);
				userList.remove(index);
				saveUserList(userList);
				return 1;
			}
		}
		return 0;
	}
	
	private void saveUserList(List<User> userList){
		try{
			File file = new File(FILE_NAME);
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(userList);
			oos.close();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
