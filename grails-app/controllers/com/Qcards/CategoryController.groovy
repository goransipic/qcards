package com.Qcards

import grails.converters.JSON

import java.awt.Image

import javax.swing.ImageIcon

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.access.annotation.Secured

class CategoryController {
	def burningImageService
	
	@Secured(['ROLE_ADMIN', 'ROLE_PARTNER'])
	def index() {
		params.sort = 'dateCreated'
		params.order = 'desc'
		redirect(action: "list", params: params)
	}

	@Secured(['ROLE_ADMIN', 'ROLE_PARTNER'])
	def list(Integer max) {
		
		params.max = Math.min(max ?: 100, 100)
		def arrayListOfPdfFiles = []
		def typeOfUser
		def categoryElements
		
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			categoryElements = Category.where{
				hasDeleted == false
			}
			categoryElements = categoryElements.list(params)
		 }
		 else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			 def currentUser = getAuthenticatedUser()
			 //categoryElements = Category.findAllByUser(currentUser)
			 
			categoryElements = Category.where{
				 user == currentUser
				 hasDeleted == false
			 }
			categoryElements = categoryElements.list(params)
		}
		
		for(int i=0;i<categoryElements.size();i++){
			
			def pdffile = categoryElements[i].id
			def f = new File("/var/www/PHP/PDFGenerator/"+pdffile+".pdf")

				if(f.file)
				{
					categoryElements[i].hasPdfFile=true;
				}
				else{
					categoryElements[i].hasPdfFile=false;
				}
					categoryElements[i].save(flush: true)
				
		}
		if (params.sort == 'dateCreated'){
			
			categoryElements.asList().sort{a,b ->
				a = a."${params.sort}".toString().toUpperCase()
				b = b."${params.sort}".toString().toUpperCase()
				params.order == 'desc' ? b <=> a:a <=> b}
		}
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			typeOfUser = "admin"
		}
		else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			typeOfUser = "partner"
		}
		
		
		[categoryInstanceList: categoryElements, categoryInstanceTotal: Category.count(), typeOfUser:typeOfUser]
	}
	
	
	
	@Secured(['ROLE_ADMIN','ROLE_PARTNER'])
	def create() {
		def maxOrderIndex = Category.executeQuery('select max(orderIndex) from Category')[0]
		params.orderIndex = maxOrderIndex ? (int)maxOrderIndex + 1 : 1
		
		def currentUser = getAuthenticatedUser()
		def typeOfUser
		
		/*-----------------------Select list for template field----------------*/
		def templateElements
		def templateQuery
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			//templateElements = Template.list(params)
			templateElements = Template.where{
				hasDeleted == false
			}
			templateElements = templateElements.list()
			
			
		 }
		 else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			
			 
			 //templateElements = Template.findAllByUser(currentUser)
			 templateQuery = Template.where{
				 user == currentUser
				 hasDeleted == false
			 }
			 //println(query.list(max:10, offset: 5))
			 templateElements = templateQuery.list()
			 
		 }
		 /*------------------------------------------------------------------*/
		 
		/* -------------------------------------- UserSelect---------------------------------------------*/
		
		def userElements
		def userQuery
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			//templateElements = Template.list(params)
			userElements = User.where{
				hasDeleted == false
				enabled == true
			}
			userElements = userElements.list()
			
			
		 }
		/*------------------------------------- */
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			typeOfUser = "admin"
			
			
		}
		else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			typeOfUser = "partner"
		}
		
		[categoryInstance: new Category(params), typeOfUser:typeOfUser, templateElements: templateElements, userElements:userElements]
	}

	@Secured(['ROLE_ADMIN','ROLE_PARTNER'])
	def save() {
		
		def currentUser = getAuthenticatedUser()
		def categoryInstance
		def typeOfUser
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			typeOfUser = "admin"
			categoryInstance = new Category(params)
		}
		else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			typeOfUser = "partner"
			categoryInstance = new Category(params)
			categoryInstance.user = currentUser
		}
		
		if (!categoryInstance.save(flush: true)) {
			render(view: "create", model: [categoryInstance: categoryInstance])
			return
		}
		
		
		def setPhoto = request.getFile('setPhoto')
		if(setPhoto && !setPhoto.empty){
			if(setPhoto.contentType != 'image/jpeg'){
				flash.message = "Only JPEG format is accepted for image files."
				redirect(action: "edit", id: categoryInstance.id)
				return
			}
			
			setPhoto.transferTo(new File("/var/www/qcards/setPhoto",categoryInstance.id+".jpg"))
			categoryInstance.hasPhoto = true
		}
		
		
		flash.message = message(code: 'default.created.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])
		redirect(action: "edit", id: categoryInstance.id)
	}

	@Secured(['ROLE_ADMIN', 'ROLE_PARTNER'])
	def edit(Long id, Integer max) {
		
//		if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
//			if (flash.message != 'OK')
//			render ("The requested resource is not available!!")  
//		}
		
		/*-----------------------Select list for template field----------------*/
		def templateElements
		def templateQuery
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			//templateElements = Template.list(params)
			templateElements = Template.where{
				hasDeleted == false
			}
			templateElements = templateElements.list()
			
			
		 }
		 else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			 def currentUser = getAuthenticatedUser()
			 
			 //templateElements = Template.findAllByUser(currentUser)
			 templateQuery = Template.where{
				 user == currentUser
				 hasDeleted == false
			 }
			 //println(query.list(max:10, offset: 5))
			 templateElements = templateQuery.list()
			 
		 }
		 /*------------------------------------------------------------------*/
		
		 /* -------------------------------------- UserSelect---------------------------------------------*/
		 
		 def userElements
		 def userQuery
		 if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			 //templateElements = Template.list(params)
			 userElements = User.where{
				 hasDeleted == false
				 enabled == true
			 }
			 userElements = userElements.list()
			 
			 
		  }
		 /*------------------------------------- */
		 
		def categoryInstance = Category.get(id)
		
		
		if (!categoryInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
			redirect(action: "list")
			return
		}
		
		def numberOfItems = 0
		def typeOfUser
		def currentUser = getAuthenticatedUser()
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER') && categoryInstance.user != currentUser) {
			return render ('The requested resource is not available.')
		}
		
//		if (categoryInstance.user != currentUser) {
//			return render ('The requested resource is not available.')
//		}
		
		params.max = Math.min(max ?: 100, 100)
		
		if (params.sort == null) {
			params.sort = "dateCreated"
			params.order= 'desc'
		}
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			typeOfUser = "admin"
		}
		else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			typeOfUser = "partner"
		}
		def query = Item.where{
			category == categoryInstance
			hasDeleted == false
		}
		//println(query.list(max:10, offset: 5))
		def itemsSlice = query.list(params)
		
		
		if (params.sort == 'dateCreated'){
			
			itemsSlice.asList().sort{a,b ->
				a = a."${params.sort}".toString().toUpperCase()
				b = b."${params.sort}".toString().toUpperCase()
				params.order == 'desc' ? b <=> a:a <=> b}
		}
		
		return [categoryInstance: categoryInstance, templateElements: templateElements, userElements:userElements, itemsTotal:categoryInstance?.items?.size(),items: itemsSlice, typeOfUser: typeOfUser, currentUser: currentUser]
		
//		if(params.sort != 'type'){
//			[categoryInstance: categoryInstance, itemsTotal:categoryInstance?.items?.size(),items: categoryInstance?.items?.asList().sort{a,b ->
//				
//					a = a."${params.sort}".toString().toUpperCase()
//					b = b."${params.sort}".toString().toUpperCase()
//					
//				
//				params.order == 'desc' ? b <=> a:a <=> b}, typeOfUser: typeOfUser]
//		}
//		else{
//		[categoryInstance: categoryInstance, itemsTotal:categoryInstance?.items?.size(),items: categoryInstance?.items?.asList().sort{a,b -> params.order == 'desc' ? b."${params.sort}" <=> a."${params.sort}":a."${params.sort}" <=> b."${params.sort}"}, typeOfUser: typeOfUser]
//	
//		}
	}

	@Secured(['ROLE_ADMIN','ROLE_PARTNER'])
	def update(Long id) {
		def categoryInstance = Category.get(id)
		if (!categoryInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
			redirect(action: "list")
			return
		}

		categoryInstance.properties = params

		if (!categoryInstance.save(flush: true)) {
			render(view: "edit", model: [categoryInstance: categoryInstance])
			return
		}
		
		def setPhoto = request.getFile('setPhoto')
		if(setPhoto && !setPhoto.empty){
			if(setPhoto.contentType != 'image/jpeg'){
				flash.message = "Only JPEG format is accepted for image files."
				redirect(action: "edit", id: categoryInstance.id)
				return
			}
			
			setPhoto.transferTo(new File("/var/www/qcards/setPhoto",categoryInstance.id+".jpg"))
			categoryInstance.hasPhoto = true
		}
		
		flash.message = message(code: 'default.updated.message', args: [message(code: 'category.label', default: 'Category'), categoryInstance.id])
		redirect(action: "edit", id: categoryInstance.id)
	}

	@Secured(['ROLE_ADMIN','ROLE_PARTNER'])
	def delete(Long id) {
		def categoryInstance = Category.get(id)
		if (!categoryInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'category.label', default: 'Category'), id])
			redirect(action: "list")
			return
		}

	/*	try {
			categoryInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'category.label', default: 'Category'), id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'category.label', default: 'Category'), id])
			redirect(action: "edit", id: categoryInstance.id)
		}  */
		
		categoryInstance.hasDeleted = true
		
		if (!categoryInstance.save(flush: true)) {
			render(view: "edit", model: [categoryInstance: categoryInstance])
			return
		}
		redirect(action: "list")
	}
	
	@Secured(['ROLE_ADMIN'])
	def renderAudio() {
		def codes = []
		
		if(params.id){
			
			def category = Category.get(params.id)
			
			if(category){
				for(itemInstance in category.items){
					
					// Uploding 600px, 320px, 80px photo
					
					if (itemInstance.hasAudio){
						
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
					
				}
			}
			else{
				codes = [status: '10', message: 'NOT_FOUND']
				render codes as JSON
			}
			render "OK"
		}
		else{
			codes = [status: '20', message: 'MISSING_ID_PARAMETER']
			render codes as JSON
		}
		
		
	}
	
	@Secured(['ROLE_ADMIN'])
	def renderImage() {
		
		def codes = [] 
		
		if(params.id){
			
			def category = Category.get(params.id)
			
			if(category){
				for(itemInstance in category.items){
					
					// Uploding 600px, 320px, 80px photo
					
					if (itemInstance.hasPhoto){
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
				}
			}
			else{
				codes = [status: '10', message: 'NOT_FOUND']
				render codes as JSON
			}
			render "OK"
		}
		else{
			codes = [status: '20', message: 'MISSING_ID_PARAMETER']
			render codes as JSON
		}
		
		
	}
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def getImage() {
		 
		def f = new File("/var/www/qcards/setPhoto/${params.setpicture}")
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
	def codes(){
		
		def codes = []
				
		if(params.id){
			def category = Category.get(params.id)
			if(category){
				for(item in category.items){
				  codes << item.code
				  codes.asList().sort{
					  a,b -> a <=> b
				  }

				}
			}
			else{
				codes = [status: '10', message: 'NOT_FOUND']
			}
		}
		else{
			codes = [status: '20', message: 'MISSING_ID_PARAMETER']
		}
		render codes as JSON
	}
	
	@Secured(['ROLE_ADMIN', 'ROLE_PARTNER'])
	def renderPDF(){
		
		def categoryInstance = Category.get(params.id)
		//def qRCodeService = new QRCodeService()
		
		//Map information = [:]
		//def Code = ""
			   
		//information.put("chs", "250x250")
		//information.put("cht", "qr")
		//information.put("chl", Code)
		//information.put("chld", "H|1")
		//information.put("choe", "UTF-8")
		
		//int i=-1, j=0, k=0;
		
	/*	for (itemInstance in categoryInstance.items ){
			
			if(k % 9 == 0)
				{
					j = 0;
					i++;
				}
			Code = itemInstance.code
			information.put("chl", itemInstance.code)
			qRCodeService.createQRCode(information, "", "/var/www/qcards/qrcode",itemInstance.code+".png")
			
			
			j++;
			k++;
		} */
		
		String USER_AGENT = "Mozilla/5.0";
		String url = "http://manas.hopto.org:8888/PHP/PDFGenerator/TwoSided.php";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
		
		//String urlParameters = "CategoryID=4&TitleFontFamily=Comic&TitleFontSize=20&TitleFontStyle=&TitleFontColorRed=185&TitleFontColorGreen=197&TitleFontColorBlue=152&TitleTextAlign=C&TitleOffsetX=2&TitleOffsetY=74&FooterFontFamily=Tahoma&FooterFontSize=14&FooterFontStyle=&FooterFontColorRed=156&FooterFontColorGreen=159&FooterFontColorBlue=187&FooterTextAlign=C&FooterOffsetX=2&FooterOffsetY=84&PhotoOffsetY=2";
		String urlParameters = "CategoryID=${categoryInstance.id}&TemplateID=${categoryInstance.template.id}&TitleFontFamily=${categoryInstance.template.titleFontFamily}&TitleFontSize=${categoryInstance.template.titleFontSize}&TitleFontStyle=${categoryInstance.template.titleFontStyle}&TitleFontColorRed=${categoryInstance.template.titleColorRed}&TitleFontColorGreen=${categoryInstance.template.titleColorGreen}&TitleFontColorBlue=${categoryInstance.template.titleColorBlue}&TitleTextAlign=${categoryInstance.template.titleTextAlign}&TitleOffsetX=${categoryInstance.template.titleOffsetX}&TitleOffsetY=${categoryInstance.template.titleOffsetY}&FooterFontFamily=${categoryInstance.template.footerFontFamily}&FooterFontSize=${categoryInstance.template.footerFontSize}&FooterFontStyle=${categoryInstance.template.footerFontStyle}&FooterFontColorRed=${categoryInstance.template.footerColorRed}&FooterFontColorGreen=${categoryInstance.template.footerColorGreen}&FooterFontColorBlue=${categoryInstance.template.footerColorBlue}&FooterTextAlign=${categoryInstance.template.footerTextAlign}&FooterOffsetX=${categoryInstance.template.footerOffsetX}&FooterOffsetY=${categoryInstance.template.footerOffsetY}&PhotoOffsetY=${categoryInstance.template.photoOffsetY}";
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader inBuf = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = inBuf.readLine()) != null) {
			response.append(inputLine);
		}
		inBuf.close();
 
		//print result
		//System.out.println(response.toString());
		
		render (response.toString()) 
	} 
	
	
 /*	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def renderPDF(){
		def categoryInstance = Category.get(params.id)
		def qRCodeService = new QRCodeService()
		def numberofitems = 0
		def totalnumberofpages
		def photocodes
		def itemsname
		def itemsfooter
		Map information = [:]
		def Code = ""
			   
		information.put("chs", "250x250")
		information.put("cht", "qr")
		information.put("chl", Code)
		information.put("chld", "H|1")
		information.put("choe", "UTF-8")
		
		for (itemInstance in categoryInstance.items){
			
			numberofitems=numberofitems + 1
		}
		
		if ((numberofitems % 9) == 0){
			
			totalnumberofpages= numberofitems / 9;
		}
		else{
			
			totalnumberofpages = (numberofitems / 9) + 1
		}
		photocodes = new String[totalnumberofpages][9];
		itemsname = new String[totalnumberofpages][9];
		itemsfooter = new String[totalnumberofpages][9];
		
		int i=-1, j=0, k=0;
		
		for (itemInstance in categoryInstance.items ){
			
			if(k % 9 == 0)
				{
					j = 0;
					i++;
				}
			Code = itemInstance.code
			information.put("chl", itemInstance.code)
			qRCodeService.createQRCode(information, "", "/var/www/qcards/qrcode",itemInstance.code+".png")
			
			photocodes[i][j] =  itemInstance.code;
			itemsname[i][j] = itemInstance.title;
			itemsfooter[i][j] = itemInstance.footer;
			j++;
			k++;
		}
		
		def args = [template:"pdf", model:[itemsfooter:itemsfooter,itemsname:itemsname,categoryInstance:categoryInstance, photocodes:photocodes, totalnumberofpages:totalnumberofpages ]]
		pdfRenderingService.render(args+[controller:this],response)
		
		
	} */
	
	
}

