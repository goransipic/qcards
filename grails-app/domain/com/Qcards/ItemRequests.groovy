package com.Qcards

import java.util.Date;

class ItemRequests {
	
	Integer userId
	Integer categoryId
	Date dateCreated
	String itemSecretCode
	String ip
	String title
	
	def beforeInsert() {
		dateCreated = new Date()
	}
	
	static constraints = {
		itemSecretCode maxSize: 12
    }
	
	static mapping = {
		version false
	}
}
