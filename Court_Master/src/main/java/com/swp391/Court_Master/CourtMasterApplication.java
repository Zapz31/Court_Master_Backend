package com.swp391.Court_Master;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication()
public class CourtMasterApplication {

	public static void main(String[] args) throws IOException {
		File file = new File("firebasePrivateKey.json");
		//"D:/FPTU/Semester V/SWP391/Court_Master_Backend/Court_Master_Backend/Court_Master/src/main/resources/firebasePrivateKey.json"
		String absolutePath = file.getAbsolutePath();
		FileInputStream serviceAccount = new FileInputStream(absolutePath);

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		FirebaseApp.initializeApp(options);

		SpringApplication.run(CourtMasterApplication.class, args);
	}
}
