package com.Qcards

class Template {
	
	String name
	
	String titleFontFamily
	Integer titleFontSize
	String titleFontStyle
	Integer titleColorRed
	Integer titleColorGreen
	Integer titleColorBlue
	String titleTextAlign
	Integer titleOffsetX
	Integer titleOffsetY
	
	String footerFontFamily
	Integer footerFontSize
	String footerFontStyle
	Integer footerColorRed
	Integer footerColorGreen
	Integer footerColorBlue
	String footerTextAlign
	Integer footerOffsetX
	Integer footerOffsetY
	
	Integer photoOffsetY
	
	Boolean hasFrontPhoto = false
	Boolean hasBackPhoto = false
	Boolean hasDeleted = false
	
	String toString(){
		"${name}"
	}
	
	static belongsTo = [user: User]
	
	static hasMany = [categories: Category]
	
	static constraints = {
		name blank: false, unique: true
		titleFontFamily blank: false
		footerFontFamily blank: false
		
	}
}
