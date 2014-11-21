package com.Qcards

import com.Qcards.Item;

class CodeGenerator {	
	
	def static generate(Integer n){
		
		def code = this.randomString(n)
		while(Item.findByCode(code)){
			code = this.randomString(n)
		}
		return code		
	}
	
	private static def randomString(Integer n){	
		def alphabet = (('A'..'Z')+('a'..'z')+('0'..'9')).join()
		return new Random().with {
			(1..n).collect {
				alphabet[ nextInt( alphabet.length() ) ]
			}.join()
		}
	}

}
