package com.Qcards

class Item {
	
	String code
	String title
	String footer	
	String url
	String attribution
	Boolean hasPhoto = false
	Boolean hasAudio = false
	Boolean hasDeleted = false
	Boolean hasVideo = false
	Float orderIndex
	Date dateCreated
	Date lastUpdated
	ItemType type
	Long numberOfRequests = 0
	
	static belongsTo = [category:Category]	
	
	def beforeInsert() {
		dateCreated = new Date()
	}
	def beforeUpdate() {
		lastUpdated = new Date()
	}
	
    static constraints = {
		footer nullable: true
		code unique: true, maxSize: 12
		title blank: false
    }
	
	enum ItemType {
		Web, Video, InternalPhoto, InternalVideo, QuizCard, SlideShowCard
	}
	
	static mapping = {
		footer sqlType: 'text'
		attribution sqlType: 'text'
		type enumType:'ordinal', sqlType:'TINYINT'	
		version false
	}
	
	String toString(){
		"${title}"
	}
}
