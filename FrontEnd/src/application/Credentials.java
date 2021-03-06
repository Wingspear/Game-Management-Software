package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Credentials
{
	public static String path = "credentials.csv";

	//create credentials file
	//only needs to be called once
	public static void createCredentialsFile() throws IOException{
		FileWriter csvWriter = new FileWriter(path);
		csvWriter.append("Username");
		csvWriter.append(",");
		csvWriter.append("Password");
		csvWriter.append("\n");
		csvWriter.flush();
		csvWriter.close();
	}

	//return true if credentials match, return false if they don't
	public static boolean validateCredentials(String user, String pwd) throws UsernameNotFoundException, IOException {
		File csvFile = new File(path);
		if (csvFile.isFile()) {
			BufferedReader csvReader = new BufferedReader(new FileReader(path));
			String row;
			csvReader.readLine();
			while ((row = csvReader.readLine()) != null) {
				String[] data = row.split(",");
				if (data[0].equals(user)) {
					if (data[1].equals(pwd)) return true;
					else return false;		    	
				}
			}
			csvReader.close();
		}

		throw new UsernameNotFoundException("Username not found");
	}

	//append credentials to csv file
	public static void addCredentials(String user, String pwd) throws DuplicateUsernameException, IOException{
		File csvFile = new File(path);
		if (csvFile.isFile()) {
			BufferedReader csvReader = new BufferedReader(new FileReader(path));
			String row;
			csvReader.readLine();
			while ((row = csvReader.readLine()) != null) {
				String[] data = row.split(",");
				if(data[0].equals(user)) {
					throw new DuplicateUsernameException("Duplicate username found");
				}
			}
			csvReader.close();
			DataManagement.createGamesPathFile(user);
		}
		
		FileWriter csvWriter = new FileWriter(path, true);
		csvWriter.append(user);
		csvWriter.append(",");
		csvWriter.append(pwd);
		csvWriter.append("\n");

		csvWriter.flush();
		csvWriter.close();
	}
}
