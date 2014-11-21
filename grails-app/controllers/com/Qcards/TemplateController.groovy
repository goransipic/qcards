package com.Qcards

import grails.plugins.springsecurity.Secured

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.dao.DataIntegrityViolationException

class TemplateController {

    
	@Secured(['ROLE_ADMIN', 'ROLE_PARTNER'])
    def index() {
        redirect(action: "list", params: params)
    }
	@Secured(['ROLE_ADMIN', 'ROLE_PARTNER'])
    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
		def query
		def templateSlice
		def templateElements
		def typeOfUser
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			//templateElements = Template.list(params)
			templateElements = Template.where{
				hasDeleted == false
			}
			templateElements = templateElements.list(params)
			typeOfUser = 'admin'
			return [templateInstanceList: templateElements, templateInstanceTotal: Template.count(), typeOfUser: typeOfUser]
		 }
		 else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			 def currentUser = getAuthenticatedUser()
			 
			 //templateElements = Template.findAllByUser(currentUser)
			 query = Template.where{
				 user == currentUser
				 hasDeleted == false
			 }
			 //println(query.list(max:10, offset: 5))
			 templateElements = query.list(params)
			 return [templateInstanceList: templateElements, templateInstanceTotal: query.count()]
		 }
		 
    }
	@Secured(['ROLE_ADMIN', 'ROLE_PARTNER'])
    def create() {
		def currentUser
		def typeOfUser
		if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			currentUser = getAuthenticatedUser()
			typeOfUser = "partner"
			
		}
		else if(SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			currentUser = getAuthenticatedUser()
			typeOfUser = "admin"
		 }
		
		/* -------------------------------------- UserSelect---------------------------------------------*/
		
		def userElements
		def userQuery
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			//templateElements = Template.list(params)
			userElements = User.where{
				hasDeleted == false
			}
			userElements = userElements.list()
			
			
		 }
		/*------------------------------------- */
		
		[templateInstance: new Template(params), currentUser: currentUser, typeOfUser: typeOfUser, userElements: userElements]
	}
	@Secured(['ROLE_ADMIN', 'ROLE_PARTNER'])
    def save() {
		def templateInstance = new Template(params)
		def currentUser
		if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			currentUser = getAuthenticatedUser()
			templateInstance.user = User.get(params.userId)
			
		}
		else if(SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			currentUser = getAuthenticatedUser()
			//templateInstance.user = User.get(currentUser.id)
		 }
		if (!templateInstance.save(flush: true)) {
			render(view: "create", model: [templateInstance: templateInstance])
			return
		}
		//Uploading front and back template picture
		
		def templatefront = request.getFile('templatefront')
		if(templatefront && !templatefront.empty){
			if(templatefront.contentType != 'image/png'){
				flash.message = "Only png format is accepted for image files."
				redirect(action: "edit", id: templateInstance.id)
				return
			}
			
			templatefront.transferTo(new File("/var/www/qcards/templates",templateInstance.id+"f.png"))
			templateInstance.hasFrontPhoto = true
		}
		def templateback = request.getFile('templateback')
		if(templateback && !templateback.empty){
			if(templateback.contentType != 'image/png'){
				flash.message = "Only png format is accepted for image files."
				redirect(action: "edit", id: templateInstance.id)
				return
			}
			
			templateback.transferTo(new File("/var/www/qcards/templates",templateInstance.id+"b.png"))
			templateInstance.hasBackPhoto = true
		}
		templateInstance.save(flush: true)
		
		flash.message = message(code: 'default.created.message', args: [message(code: 'template.label', default: 'Template'), templateInstance.id])
		redirect(action: "edit", id: templateInstance.id)
    }
	@Secured(['ROLE_ADMIN', 'ROLE_PARTNER'])
	def edit(Long id) {
		
		def currentUser
		def typeOfUser
		if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			currentUser = getAuthenticatedUser()
			typeOfUser= "partner"
			
		}
		else if(SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			currentUser = getAuthenticatedUser()
			typeOfUser= "admin"
		 }
		/* -------------------------------------- UserSelect---------------------------------------------*/
		
		def userElements
		def userQuery
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			//templateElements = Template.list(params)
			userElements = User.where{
				hasDeleted == false
			}
			userElements = userElements.list()
			
			
		 }
		/*------------------------------------- */
		def templateInstance = Template.get(id)
        if (!templateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'template.label', default: 'Template'), id])
            redirect(action: "list")
            return
        }

        [templateInstance: templateInstance, userElements: userElements, currentUser:currentUser, typeOfUser: typeOfUser]
    }
	@Secured(['ROLE_ADMIN', 'ROLE_PARTNER'])
    def update(Long id, Long version) {
		
        def templateInstance = Template.get(id)
        if (!templateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'template.label', default: 'Template'), id])
            redirect(action: "list")
            return
        }

		templateInstance.properties = params
		
		if (!templateInstance.save(flush: true)) {
			render(view: "edit", model: [templateInstance: templateInstance])
			return
		}
		
		//Uploading front and back template picture
		
		def templatefront = request.getFile('templatefront')
		if(templatefront && !templatefront.empty){
			if(templatefront.contentType != 'image/png'){
				flash.message = "Only png format is accepted for image files."
				redirect(action: "edit", id: templateInstance.id)
				return
			}
			
			templatefront.transferTo(new File("/var/www/qcards/templates",templateInstance.id+"f.png"))
			templateInstance.hasFrontPhoto = true
		}
		def templateback = request.getFile('templateback')
		if(templateback && !templateback.empty){
			if(templateback.contentType != 'image/png'){
				flash.message = "Only png format is accepted for image files."
				redirect(action: "edit", id: templateInstance.id)
				return
			}
			
			templateback.transferTo(new File("/var/www/qcards/templates",templateInstance.id+"b.png"))
			templateInstance.hasBackPhoto = true
		}
		
		templateInstance.save(flush: true)
		
		flash.message = message(code: 'default.updated.message', args: [message(code: 'template.label', default: 'Template'), templateInstance.id])
        redirect(action: "edit", id: templateInstance.id)
    }
	@Secured(['ROLE_ADMIN', 'ROLE_PARTNER'])
    def delete(Long id) {
        def templateInstance = Template.get(id)
        if (!templateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'template.label', default: 'Template'), id])
            redirect(action: "list")
            return
        }

    /*    try {
            templateInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'template.label', default: 'Template'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'template.label', default: 'Template'), id])
            redirect(action: "show", id: id)
        } */
		templateInstance.hasDeleted = true
		
		if (!templateInstance.save(flush: true)) {
			render(view: "edit", model: [templateInstance: templateInstance])
			return
		}
		redirect(action: "list")
    }
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED'])
	def getImage() {
		 
		def f = new File("/var/www/qcards/templates/${params.templatepicture}")
		if(f.file)
		{
			byte[] image = f.getBytes()
			response.setContentLength(image.size())
			OutputStream out = response.getOutputStream()
			out.write(image)
			out.close()
		}
	}
}
