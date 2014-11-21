package com.Qcards

import java.util.Date;

class Category {
		
	String title	
	Float orderIndex
	Date dateCreated
	Date lastUpdated
	String descriptionOfSet
	String authorName
	String authorLink
	Boolean hasPdfFile = false
	Boolean hasPhoto = false
	Boolean hasDeleted = false
	
	static hasMany = [items:Item]
	
	static belongsTo = [template: Template, user: User]
	
	def beforeInsert() {		
		dateCreated = new Date()		
	}
	def beforeUpdate() {
		lastUpdated = new Date()
	}
	
    static mapping = {
		version false
	}
	
	String toString(){
		"${title}"
	}
	static constraints = {
		hasPdfFile nullable: true
		user nullable: true
		
	}
	
	
		
}
