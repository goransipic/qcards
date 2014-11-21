package com.Qcards

import grails.converters.JSON
import grails.plugins.qrcode.QRCodeService

import java.awt.Image

import javax.swing.ImageIcon

import org.apache.commons.io.IOUtils
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.access.annotation.Secured

import com.Qcards.Item.ItemType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.ForEach;

class ItemController {
	
	def burningImageService
	public static  var

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def index() {
		redirect(controller: "category", action: "list")
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def create() {
		def videoInput
		def inCreateAction = true
		def slideShow
		def itemInstanceList
		
		if(params.id)
		var = params.id // Global Variable...
		
		def maxOrderIndex = Item.executeQuery('select max(orderIndex) from Item')[0]
		//println(params.select)
		
		if (params.type2=="InternalVideo")
		{
			videoInput=true
		}
		if (params.type2=="SlideShowCard")
		{	
			//println("OK")
			
			slideShow=true
			params.id=var
			def selectedCategory = Category.get(params.id)
			//println (selectedCategory)
			itemInstanceList = Item.where{
				category == selectedCategory
				type != Item.ItemType.SlideShowCard
			}
			//itemInstanceList = Item.executeQuery('select * from Item where category_id = 7')
			
			
			itemInstanceList = itemInstanceList.list(sort: "title", order: "asc");
			
		}
		//params.type = params.type2
		params.orderIndex = maxOrderIndex ? (int)maxOrderIndex + 1 : 1
		
		[itemInstance: new Item(params), select: var, selectType:params.type2, videoInput: videoInput, inCreateAction: inCreateAction, slideShow:slideShow, itemInstanceList:itemInstanceList]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def save() {
		
		
		def qRCodeService = new QRCodeService()
		
		Map information = [:]
		def Code = ""
		
		information.put("chs", "250x250")
		information.put("cht", "qr")
		information.put("chl", Code)
		information.put("chld", "H|1")
		information.put("choe", "UTF-8")
		
		
//		while (params."${i}.title") {
//			// do something with params."${i}.title"
//			println(params."${i}.title")
//			i++
//		}
		
		def itemInstance = new Item(params)
		itemInstance.type = params.type2
		itemInstance.code = CodeGenerator.generate(12)
		
		information.put("chl", itemInstance.code)
		qRCodeService.createQRCode(information, "", "/var/www/qcards/qrcode",itemInstance.code+".png")
		
	/*	if(params.url){		
			itemInstance.url = params.url.contains('http://') ? params.url : 'http://' + params.url
		} */

		if (!itemInstance.save(flush: true)) {
			render(view: "create", model: [itemInstance: itemInstance])
			return
		}
		
		
		//def i = 0
		def checkBox
		def indexAndIdSelection
		params.each(){
			if(it.key.startsWith('items.')){
				//println "${it.value}"
				if (it.value instanceof String){
					checkBox = it.value
					
					indexAndIdSelection  = checkBox.split("-")
//					println indexSelection[0]
//					println "alal"
//					println indexSelection[1]
					
					def slideShowInstance = new SlideShow()
					slideShowInstance.slideShowId = itemInstance.id
//					slideShowInstance.itemPosition = Integer.parseInt(indexAndIdSelection[0]);
					slideShowInstance.itemId = Integer.parseInt(indexAndIdSelection[1]);
					
					if (!slideShowInstance.save(flush: true)) {
						render(view: "create", model: [itemInstance: itemInstance])
						return
					}
					
				}
				
			}
		}
		
		
		//Uploading photo and audio file
		
		def photofile = request.getFile('photofile')
		if(photofile && !photofile.empty){
			if(photofile.contentType != 'image/jpeg'){
				flash.message = "Only JPEG format is accepted for image files."
				redirect(action: "edit", id: itemInstance.id)
				return
			}
			
			photofile.transferTo(new File("/var/www/qcards/photo",itemInstance.code+".jpg"))
			itemInstance.hasPhoto = true
			
			// Uploding 600px, 320px, 80px photo
			Image image = new ImageIcon("/var/www/qcards/photo/"+itemInstance.code+".jpg").getImage();
			int imgWidth = image.getWidth(null);
			int imgHeight = image.getHeight(null);
			
			double ratio = imgWidth/imgHeight
			
			if (imgWidth > imgHeight){
			burningImageService.doWith("/var/www/qcards/photo/"+itemInstance.code+".jpg", '/var/www/qcards/photo/600').execute (itemInstance.code, {
						   it.scaleAccurate(600, (int)600/ratio)
						})
			burningImageService.doWith("/var/www/qcards/photo/"+itemInstance.code+".jpg", '/var/www/qcards/photo/320').execute (itemInstance.code, {
				it.scaleAccurate(320, (int)320/ratio)
			 })
			
			burningImageService.doWith("/var/www/qcards/photo/"+itemInstance.code+".jpg", '/var/www/qcards/photo/80').execute (itemInstance.code, {
				it.scaleAccurate(80, (int)80/ratio)
			 })
			
			}
			else{
				
				burningImageService.doWith("/var/www/qcards/photo/"+itemInstance.code+".jpg", '/var/www/qcards/photo/600').execute (itemInstance.code, {
					it.scaleAccurate((int)600*ratio,600)
				 })
				
				burningImageService.doWith("/var/www/qcards/photo/"+itemInstance.code+".jpg", '/var/www/qcards/photo/320').execute (itemInstance.code, {
					it.scaleAccurate((int)320*ratio,320)
				 })
				
				burningImageService.doWith("/var/www/qcards/photo/"+itemInstance.code+".jpg", '/var/www/qcards/photo/80').execute (itemInstance.code, {
					it.scaleAccurate((int)80*ratio,80)
				 })
			}
			
		}

		def audiofile = request.getFile('audiofile')
		if(audiofile && !audiofile.empty){
			if(audiofile.contentType != 'audio/ogg'){
				flash.message = "Only OGG format is accepted for audio files."
				redirect(action: "edit", id: itemInstance.id)
				return
			}
			audiofile.transferTo(new File("/var/www/qcards/audio",itemInstance.code+".ogg"))
			itemInstance.hasAudio = true
			
			// First Delete audio file because process.execute will not work
			def f = new File("/var/www/qcards/audio/mp3/"+itemInstance.code+".mp3")
			if (f){
				f.delete();
			}
			//************************************************************
			def process = "ffmpeg -i /var/www/qcards/audio/"+itemInstance.code+".ogg -acodec libmp3lame /var/www/qcards/audio/mp3/"+itemInstance.code+".mp3"
			def process1 = process.execute()
			//println "Found text ${process1.text}"
		}
		
		def videofile = request.getFile('videofile')
		if(videofile && !videofile.empty){
			if(videofile.contentType != 'video/mp4'){
				flash.message = "Only mp4 format is accepted for video files."
				redirect(action: "edit", id: itemInstance.id)
				return
			}
			videofile.transferTo(new File("/var/www/qcards/internalVideo",itemInstance.code+".mp4"))
			itemInstance.hasVideo = true
			
		}

		itemInstance.save(flush: true)

		flash.message = message(code: 'default.created.message', args: [message(code: 'item.label', default: 'Item'), itemInstance.id])
		redirect(action: "edit", id: itemInstance.id)

	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def edit(Long id) {
		def videoInput
		String serverName = request.getServerName();
		String scheme = request.getScheme();
		def typeOfUser
		def slideShow
		def currentUser = getAuthenticatedUser()
		
//		def i = 0
//		println(params)
//		while (params."${i}.title") {
//			// do something with params."${i}.title"
//			println(params."${i}.title")
//			i++
//		}
		
		def itemInstance = Item.get(id)
		//println (itemInstance.type)
		if (params.type2=="InternalVideo" || itemInstance.hasVideo)
		{
			videoInput=true
			//println (params.type2)
		}
		
		if (itemInstance.type.name()=="SlideShowCard")
		{
			slideShow=true
		}
		
		// ********************************
		def selectedCategory = Category.get(itemInstance.category.id)
		//println (selectedCategory)
		def itemInstanceList = Item.where{
			category == selectedCategory
			type != Item.ItemType.SlideShowCard
		}
		
		itemInstanceList = itemInstanceList.list(sort: "title", order: "asc");
		
		def slideShowInstanceList = SlideShow.where{
			slideShowId == itemInstance.id
		}
		
		slideShowInstanceList = slideShowInstanceList.list()
		// Groovy for-each loop.
		
//		def emptyList = []
//		
//		for (int i=0; i<itemInstanceList.size();i++){
//			emptyList.add(0)
//		}
//		
//		def isCheckBoxTrueList = []
//		
//		for (slideShowInstance in slideShowInstanceList){
//			
//			isCheckBoxTrueList.add(slideShowInstance.itemPosition)
//		}
//		
//		for (int i=0; i<itemInstanceList.size();i++){
//			if (isCheckBoxTrueList.contains(i)){
//				emptyList[i]=1
//			}
//		}
		
		//******************************************
		//itemInstanceList = Item.executeQuery('select * from Item where category_id = 7')
		
		
		
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER') && itemInstance.category.user != currentUser) {
			return render ('The requested resource is not available.')
		}
		
		if (!itemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), id])
			redirect(controller: "category", action: "list")
			return
		}		
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			typeOfUser = "admin"
		}
		else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			typeOfUser = "partner"
		}
		
		[itemInstance: itemInstance, slideShow:slideShow, slideShowInstanceList: slideShowInstanceList, itemInstanceList:itemInstanceList, serverName: serverName, scheme: scheme, typeOfUser:typeOfUser,videoInput:videoInput,selectType:params.type2]
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def update(Long id) {

		def itemInstance = Item.get(id)

		if (!itemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), id])
			redirect(controller: "category", action: "list")
			return
		}

		itemInstance.properties = params
		itemInstance.type = params.type2
	/*	if(params.url){		
			itemInstance.url = params.url.contains('http://') ? params.url : 'http://' + params.url
		} */

		if (!itemInstance.save(flush: true)) {
			render(view: "edit", model: [itemInstance: itemInstance])
			return
		}
		// ********************************
		
		
		def slideShowInstanceList = SlideShow.where{
			slideShowId == itemInstance.id
		}
		
		for ( slideShowInstance in slideShowInstanceList){
			slideShowInstance.delete(flush: true)
		}
		def checkBox
		def indexAndIdSelection
		
		params.each(){
			if(it.key.startsWith('items.')){
				//println "${it.value}"
				if (it.value instanceof String){
					checkBox = it.value
					
					indexAndIdSelection  = checkBox.split("-")
//					println indexSelection[0]
//					println "alal"
//					println indexSelection[1]
					
					def slideShowInstance = new SlideShow()
					slideShowInstance.slideShowId = itemInstance.id
					slideShowInstance.itemId = Integer.parseInt(indexAndIdSelection[1]);
					
					if (!slideShowInstance.save(flush: true)) {
						render(view: "create", model: [itemInstance: itemInstance])
						return
					}
					
				}
				
			}
		}
		
		
		
		//******************************************
		//Uploading photo and audio file
		
		def photofile = request.getFile('photofile')
		if(photofile && !photofile.empty){
			if(photofile.contentType != 'image/jpeg'){
				flash.message = "Only JPEG format is accepted for image files."
				redirect(action: "edit", id: itemInstance.id)
				return
			}
			
			photofile.transferTo(new File("/var/www/qcards/photo",itemInstance.code+".jpg"))
			itemInstance.hasPhoto = true
			
			// Uploding 600px, 320px, 80px photo
			Image image = new ImageIcon("/var/www/qcards/photo/"+itemInstance.code+".jpg").getImage();
			int imgWidth = image.getWidth(null);
			int imgHeight = image.getHeight(null);
			
			double ratio = imgWidth/imgHeight
			
			if (imgWidth > imgHeight){
			burningImageService.doWith("/var/www/qcards/photo/"+itemInstance.code+".jpg", '/var/www/qcards/photo/600').execute (itemInstance.code, {
						   it.scaleAccurate(600, (int)600/ratio)
						})
			burningImageService.doWith("/var/www/qcards/photo/"+itemInstance.code+".jpg", '/var/www/qcards/photo/320').execute (itemInstance.code, {
				it.scaleAccurate(320, (int)320/ratio)
			 })
			
			burningImageService.doWith("/var/www/qcards/photo/"+itemInstance.code+".jpg", '/var/www/qcards/photo/80').execute (itemInstance.code, {
				it.scaleAccurate(80, (int)80/ratio)
			 })
			
			}
			else{
				
				burningImageService.doWith("/var/www/qcards/photo/"+itemInstance.code+".jpg", '/var/www/qcards/photo/600').execute (itemInstance.code, {
					it.scaleAccurate((int)600*ratio,600)
				 })
				
				burningImageService.doWith("/var/www/qcards/photo/"+itemInstance.code+".jpg", '/var/www/qcards/photo/320').execute (itemInstance.code, {
					it.scaleAccurate((int)320*ratio,320)
				 })
				
				burningImageService.doWith("/var/www/qcards/photo/"+itemInstance.code+".jpg", '/var/www/qcards/photo/80').execute (itemInstance.code, {
					it.scaleAccurate((int)80*ratio,80)
				 })
			}
			
		}

		def audiofile = request.getFile('audiofile')		
		if(audiofile && !audiofile.empty){
			if(audiofile.contentType != 'audio/ogg'){
				flash.message = "Only OGG format is accepted for audio files."
				redirect(action: "edit", id: itemInstance.id)
				return
			}
			
			audiofile.transferTo(new File("/var/www/qcards/audio",itemInstance.code+".ogg"))
			itemInstance.hasAudio = true
			
			// First Delete audio file because process.execute will not work
			def f = new File("/var/www/qcards/audio/mp3/"+itemInstance.code+".mp3")
			if (f){
				f.delete();
			}
			//************************************************************
			def process = "ffmpeg -i /var/www/qcards/audio/"+itemInstance.code+".ogg -acodec libmp3lame /var/www/qcards/audio/mp3/"+itemInstance.code+".mp3"
			def process1 = process.execute()
			//println "Found text ${process1.text}"
		}
		
		def videofile = request.getFile('videofile')
		if(videofile && !videofile.empty){
			if(videofile.contentType != 'video/mp4'){
				flash.message = "Only mp4 format is accepted for video files."
				redirect(action: "edit", id: itemInstance.id)
				return
			}
			videofile.transferTo(new File("/var/www/qcards/internalVideo",itemInstance.code+".mp4"))
			itemInstance.hasVideo = true
			
		}

		itemInstance.save(flush: true)

		flash.message = message(code: 'default.updated.message', args: [message(code: 'item.label', default: 'Item'), itemInstance.id])
		redirect(action: "edit", id: itemInstance.id )
	}

	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def delete(Long id) {
		def itemInstance = Item.get(id)
		if (!itemInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), id])
			redirect(controller: "category", action: "list")
			return
		}
		
	/*	try {
			itemInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'item.label', default: 'Item'), id])
			redirect(controller: "category", action: "edit", id:itemInstance.category.id)
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'item.label', default: 'Item'), id])
			redirect(action: "show", id: id)
		} */
		itemInstance.hasDeleted = true
		
		if (!itemInstance.save(failOnError: true, flush: true)) {
			render(view: "edit", model: [id: itemInstance.id])
			return
		}
		redirect(controller: "category", action: "edit", params: [id: itemInstance.category.id])
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def getImage() {		
		
		def f = new File("/var/www/qcards/photo/${params.code}.jpg")
		if(f.file)
		{
			byte[] image = f.getBytes()
			response.setContentLength(image.size())
			OutputStream out = response.getOutputStream()
			out.write(image)
			out.close()
		}
	}
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def itemsGet(){
		String serverName = request.getServerName();
		String scheme = request.getScheme();
		def result= []
		def categoryInformation = [:]
		
		
		
		if(params.id){
			def category = Category.get(params.id)
			
			if(category){
				
				categoryInformation.put("status", "00")
				categoryInformation.put("message", "OK")
				categoryInformation.put("categoryId", category.id)
				categoryInformation.put("categoryName", category.title)
				categoryInformation.put("categoryDescriptionOfSet", category.descriptionOfSet)
				categoryInformation.put("setPhoto", category.hasPhoto ? scheme+'://'+serverName+':8888'+'/qcards/setPhoto/'+category.id+'.jpg' : null)
				categoryInformation.put("categoryAuthorName", category.authorName)
				categoryInformation.put("categoryAuthorLink", category.authorLink)
				
				result.add(categoryInformation)
				int i = 1;
				for(itemInstance in category.items){
					def item = [:]
					
//					item:[
//						id: itemInstance.id,
//						numberOfRequests:itemInstance.numberOfRequests,
//						code: itemInstance.code,
//						title: itemInstance.title,
//						footer: itemInstance.footer,
//						type: itemInstance.type.name(),
//						url: itemInstance.url,
//						attribution: itemInstance.attribution,
//						photo: itemInstance.hasPhoto ? scheme+'://'+serverName+':8888'+'/qcards/photo/'+item.code+'.jpg' : null,
//						audio: itemInstance.hasAudio ? scheme+'://'+serverName+':8888'+'/qcards/audio/'+item.code+'.ogg': null
//					]
					item.put("id", itemInstance.id)
					item.put("numberOfRequests", itemInstance.numberOfRequests)
					item.put("code", itemInstance.code)
					item.put("title", itemInstance.title)
					item.put("footer", itemInstance.footer)
					item.put("type", itemInstance.type.name())
					item.put("url", itemInstance.url)
					item.put("attribution", itemInstance.attribution)
					item.put("photo", itemInstance.hasPhoto ? scheme+'://'+serverName+':8888'+'/qcards/photo/'+item.code+'.jpg' : null)
					item.put("audio", itemInstance.hasAudio ? scheme+'://'+serverName+':8888'+'/qcards/audio/'+item.code+'.ogg': null)
					result[i]= [item:item]
					i++;
				}
			}
			else{
				result = [status: '10', message: 'NOT_FOUND']
			}
		}
		else{
			result = [status: '20', message: 'MISSING_ID_PARAMETER']
		}
		render result as JSON
	}
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def get(){
		
		String serverName = request.getServerName();
		String scheme = request.getScheme();
		def result = []
		
		def item 
		def category 
		
		if(params.code && (params.code != 'reset')){
			
			item = Item.findByCode(params.code)
			category = Category.get(item.category.id)
			
			def itemRequestsInstance = ItemRequests.findByItemSecretCode(params.code)
			
				itemRequestsInstance = new ItemRequests()
				itemRequestsInstance.ip = "not in use"
				itemRequestsInstance.itemSecretCode = params.code
				itemRequestsInstance.userId = item.category.user.id
				itemRequestsInstance.categoryId = item.category.id
				itemRequestsInstance.title = item.title
				itemRequestsInstance.save(flash:true)
			
			
			def codes = []
			String[] codesInString;
			for(itemtemp in category.items){
				codes << itemtemp.code
				codes.asList().sort{
					a,b -> a <=> b
				}

			 }
			def previous
			def next
			
			if (codes.indexOf(params.code)== 0)
			{
				previous = null
				next = codes[codes.indexOf(params.code)+1]
			}
			else{
			previous = codes[codes.indexOf(params.code)-1]
			next = codes[codes.indexOf(params.code)+1]
			}
			 if(item){
				item.numberOfRequests = item.numberOfRequests +1
				item.save(failOnError: true, flush: true)
				
				result = [
					status: '00',
					message: 'OK',
					item:[
						id: item.id,
						categoryId: item.category.id,
						categoryName: item.category.title,
						numberOfRequests:item.numberOfRequests,
						code: item.code,
						previous: previous,
						next: next,
						title: item.title,
						footer: item.footer,
						type: item.type.name(),
						url: item.url,
						attribution: item.attribution,
						photo: item.hasPhoto ? scheme+'://'+serverName+':8888'+'/qcards/photo/'+item.code+'.jpg' : null,
						audio: item.hasAudio ? scheme+'://'+serverName+':8888'+'/qcards/audio/'+item.code+'.ogg': null
					]   
				]	
			}		
			else{
				result = [status: '10', message: 'NOT_FOUND']
			}
		}
		else{
			result = [status: '20', message: 'MISSING_CODE_PARAMETER']
		}
		render result as JSON
	}
	
	
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def getAttributeOfItem(){
		
		def codes = []
		def result= []
		String serverName = request.getServerName();
		String scheme = request.getScheme();
		
		if(params.id){
			
			def categoryInstance = Category.get(params.id)
			def totalNumberOfItems = Item.where{
				category == categoryInstance
			}
			
			if(categoryInstance){
			
			def query = Item.where{
				category == categoryInstance
			}
			
			def itemsSlice = query.list( max:params.max, offset: params.offset, sort: "title", order: "asc")
			result.add("OK")
			for(int i=0; i<itemsSlice.size();i=i+1){
					
//				def item = [id: itemsSlice[i].id,
//							categoryId: categoryInstance.id,
//							categoryName: categoryInstance.title,
//							title: itemsSlice[i].title,
//							footer: itemsSlice[i].footer,
//							type: itemsSlice[i].type.name(),
//							url: itemsSlice[i].url,
//							attributionPhotoUrl: itemsSlice[i].attributionPhoto,
//							attributionAudioUrl: itemsSlice[i].attributionAudio,
//							photo: itemsSlice[i].hasPhoto ? scheme+'://'+serverName+'/qcards/photo/'+itemsSlice[i].code+'.jpg' : null,
//							audio: itemsSlice[i].hasAudio ? scheme+'://'+serverName+'/qcards/audio/'+itemsSlice[i].code+'.ogg': null
//							]
				
				def item = [id: itemsSlice[i].id]
				
				if (params.title != null){
					item.put("title",itemsSlice[i].title)
				}
				
				if (params.footer != null){
					item.put("footer",itemsSlice[i].footer)
				}
				
				if (params.code != null){
					item.put("code",itemsSlice[i].code)
				}
				
				if (params.photo != null){
					item.put("photo",itemsSlice[i].hasPhoto ? scheme+'://'+serverName+':8888'+'/qcards/photo/'+itemsSlice[i].code+'.jpg' : null)
				}
				
				if (params.audio != null){
					item.put("audio",itemsSlice[i].hasAudio ? scheme+'://'+serverName+':8888'+'/qcards/audio/'+itemsSlice[i].code+'.ogg': null)
				}
				
				if (params.url != null){
					item.put("url",itemsSlice[i].url)
				}
				
				if (i==0){
					result[i] = [
						status: '00',
						message: 'OK',
						categoryId: categoryInstance.id,
						totalNumberOfItems:totalNumberOfItems.count(),
						categoryName: categoryInstance.title,
						categoryDescriptionOfSet: categoryInstance.descriptionOfSet,
						categoryAuthorName: categoryInstance.authorName,
						categoryAuthorLink: categoryInstance.authorLink,
						item:item
					]
				}
				else{
					result[i] = [item:item]
				}
			}
			}
			else{
				result = [status: '10', message: 'NOT_FOUND']
			}
		}
		else{
			result = [status: '20', message: 'MISSING_CODE_PARAMETER']
		}
		render result as JSON
	}
	

	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def newItem(){
		
		//def req = request.JSON   //POST
		
		def req = params    //GET
		
		def item = new Item()
		
		item.title = req.title
		item.footer = req.footer
		item.type = req.type
		item.url = req.url
		
		item.code = CodeGenerator.generate(12)
		
		def maxOrderIndex = Item.executeQuery('select max(orderIndex) from Item')[0]
		item.orderIndex = maxOrderIndex ? (int)maxOrderIndex + 1 : 1
		
		def category = Category.get(req.categoryId)
		category.addToItems(item).save()
		
		
		def qRCodeService = new QRCodeService()
		
		Map information = [:]
		def Code = ""
		
		information.put("chs", "250x250")
		information.put("cht", "qr")
		information.put("chl", Code)
		information.put("chld", "H|1")
		information.put("choe", "UTF-8")
		information.put("chl", item.code)
		qRCodeService.createQRCode(information, "", "/var/www/qcards/qrcode",item.code+".png")
		
		
		
		if(req.photourl){
			def file = new File("/var/www/qcards/photo",item.code+".jpg").newOutputStream()
			file << new URL(req.photourl).openStream()
			file.close()
			item.hasPhoto = true
		}
		
		if(req.audiourl){
			def file = new File("/var/www/qcards/audio",item.code+".ogg").newOutputStream()
			file << new URL(req.audiourl).openStream()
			file.close()
			item.hasAudio = true
		}
		
		item.save(failOnError: true, flush: true)
		
		def result = [status: '00', message: 'OK']
		render result as JSON
	}
	
		
}
