package com.aycap.optech.nlp.controller;


import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import com.google.common.collect.Lists;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@RestController
@RequestMapping("")
public class NLPController {

	@PostMapping(value = "/testConnect")
	public void test() throws IOException {
				
		  GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("D:\\google\\SpeechToTech-213eda7ada3d.json"))
			        .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
			  Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

			  System.out.println("Buckets:");
			  Page<Bucket> buckets = storage.list();
			  for (Bucket bucket : buckets.iterateAll()) {
			    System.out.println(bucket.toString());
			  }
	}
	
	@PostMapping(value = "/testnlp")
	public void tesnlp() throws IOException {
		

		try (LanguageServiceClient language = LanguageServiceClient.create()) {

		      // The text to analyze
		      String text = "ระบบห่วยแตกมาก ฉันมารอแต่เช้า นี้เพิ่งจะเสร็จ";
		      Document doc = Document.newBuilder().setContent(text).setType(Type.PLAIN_TEXT).build();

		      // Detects the sentiment of the text
		      Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();

		      System.out.printf("Text: %s%n", text);
		      System.out.printf("Sentiment: %s, %s%n ", sentiment.getScore(), sentiment.getMagnitude());
		}
	}
}
