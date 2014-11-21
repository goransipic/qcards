package com.Qcards

import grails.plugins.springsecurity.Secured

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.dao.DataIntegrityViolationException


@Secured(['ROLE_ADMIN', 'ROLE_PARTNER'])
class ItemRequestsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }
	
	def itemHistory() {
		def query
		
		
		query = ItemRequests.where {
			itemSecretCode == params.item
		}
		
		if(!params.sort) {
			params.sort = 'dateCreated'
			params.order = 'desc'
			
			
		}
		
		[itemRequestsInstanceList: query.list(params), itemRequestsInstanceTotal: query.list().size(), itemSecretCode:params.item]
	}
	
	def categoryCount() {
		
		def query
		def categoryElements
		def numberOfRequests = []
		
		def startDate
		def endDate
		def typeOfUser
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			categoryElements = Category.list(params)
			typeOfUser = 'admin'
		 }
		 else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			 def currentUser = getAuthenticatedUser()
			 categoryElements = Category.where{
				 user == currentUser
			 }
			 categoryElements = categoryElements.list(params)
		 }
		 
		 def categoryInstanceList = [:]
		 def categoryInstanceId = [:]
		 /* --------------Filter by date -------------------------------------------------*/
		 
		 if(params.startDate)
		 try {
		 startDate = new Date().parse("dd/MM/yyyy", params.startDate)
		 } catch (Exception e) {
			 flash.message = e
		 }
		 if(params.endDate)
		 try {
			 endDate = new Date().parse("dd/MM/yyyy",params.endDate)
			 endDate = endDate.next()
		 }
		 catch (Exception e){
			 flash.message = e
		 }
		 
		 /*--------------------------------------------------------------------------*/
		 
		 for(int i=0;i<categoryElements.size();i++){
			 
			 //def categoryCount = ItemRequests.findAllByCategoryId(categoryElements[i].id)
			 def categoryCount = ItemRequests.where{
				 categoryId == categoryElements[i].id
				 if(startDate)
				 dateCreated >= startDate
				 if(endDate)
				 dateCreated <= endDate
			 }
			 
			 categoryInstanceList.put((categoryElements[i].title),categoryCount.count())
			 categoryInstanceId.put((categoryElements[i].title), categoryElements[i].id)
		}
		 
		 if(!params.sort) {
			params.sort = 'dateCreated'
			params.order = 'asc'
		}
		 if(params.sort == "title") {
			
		 }
		 else{
			 categoryInstanceList=categoryInstanceList.sort { a, b -> params.order == 'asc' ? b.value <=> a.value : a.value <=> b.value }
		 }
		 
		 [categoryElements: categoryElements,categoryInstanceList: categoryInstanceList, categoryInstanceId: categoryInstanceId, startDate:params.startDate, endDate:params.endDate, typeOfUser: typeOfUser ]
	}
	
	def itemCount() {
		def query
		def itemElements
		def numberOfRequests = [:]
		def typeOfUser
		
		def startDate
		def endDate
		
		
		
		def selectedCategory = Category.get(params.id)
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			itemElements = Item.where{
				category == selectedCategory
				
			}
			itemElements = itemElements.list(params)
			typeOfUser = 'admin'
		 }
		 else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			 def currentUser = getAuthenticatedUser()
			 
			 itemElements = Item.where{
				category == selectedCategory
				//jeli kategorija pripada tom partneru
			 }
			 itemElements = itemElements.list(params)
		 }
		 
		 def itemRequestsInstanceList = [:]
		 def itemRequestsInstanceListOfTitle = [:]
		 
		/* --------------Filter by date -------------------------------------------------*/
		 
		 if(params.startDate)
		 try {
			 startDate = new Date().parse("dd/MM/yyyy", params.startDate)
		 } catch (Exception e) {
			 flash.message = e
		 }
		 if(params.endDate)
		 try {
			 endDate = new Date().parse("dd/MM/yyyy",params.endDate)
			 endDate = endDate.next()
		 }
		 catch (Exception e){
			 flash.message = e
		 }
		 
		 /*--------------------------------------------------------------------------*/
		 
		 for(item in itemElements){
			 
			 def query2 = ItemRequests.where {
				 categoryId == params.id
				 itemSecretCode == item.code
				 if(startDate)
				 dateCreated >= startDate
				 if(endDate)
				 dateCreated <= endDate
			 }
		
		itemRequestsInstanceList.put((item.code),query2.list().size())
		itemRequestsInstanceListOfTitle.put((item.code),item.title)
		} 
		
		
		 if(!params.sort) {
			params.sort = 'dateCreated'
			params.order = 'asc'
		}
		 if(params.sort == "title") {
		 
		 }
		 else{
			 itemRequestsInstanceList=itemRequestsInstanceList.sort { a, b -> params.order == 'asc' ? b.value <=> a.value : a.value <=> b.value }
		 }
		 
		 [itemRequestsInstanceList:itemRequestsInstanceList, itemRequestsInstanceListOfTitle: itemRequestsInstanceListOfTitle, typeOfUser: typeOfUser]
	}
	
    def list(Integer max) {
		
		def currentUser = getAuthenticatedUser()
		//def categoryList = Category.list()
		def categoryList
		def typeOfUser
		
		def query
		
		def specificCategory
		def specificPartner
		def startDate
		def endDate
		
		def partnerList = User.list()
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			categoryList = Category.list()
			typeOfUser = "admin"
		 }
		 else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			categoryList = Category.findAllByUser(currentUser)
			specificPartner = currentUser
			typeOfUser = "partner"
		}
		if(params.specificCategory)
			specificCategory = Category.get(params.specificCategory)
		if(params.specificPartner)
			specificPartner = User.get(params.specificPartner)
		if(params.startDate)
			try {
			startDate = new Date().parse("dd/MM/yyyy", params.startDate)
			} catch (Exception e) {
				flash.message = e
			}
		if(params.endDate)
			try {
				endDate = new Date().parse("dd/MM/yyyy",params.endDate)
				endDate = endDate.next()
			}
			catch (Exception e){
				flash.message = e
			}
		query = ItemRequests.where {
			if(specificCategory)
				categoryId == specificCategory.id
			if(specificPartner)
				userId == specificPartner.id
			if(startDate)
				dateCreated >= startDate
			if(endDate)
				dateCreated <= endDate 
		}
		if(!params.sort) {
			params.sort = 'dateCreated'
			params.order = 'desc'
		}
		
		//return render (query.list(params))
		
        params.max = Math.min(max ?: 50, 100)
		//[itemRequestsInstanceList: ItemRequests.list(params), itemRequestsInstanceTotal: ItemRequests.count(), categoryList: categoryList, partnerList: partnerList]
		[itemRequestsInstanceList: query.list(params), itemRequestsInstanceTotal: query.list().size(), categoryList: categoryList, partnerList: partnerList, specificCategory:specificCategory,specificPartner:specificPartner, typeOfUser: typeOfUser, startDate:params.startDate, endDate:params.endDate]
    }

    def create() {
        [itemRequestsInstance: new ItemRequests(params)]
    }

    def save() {
        def itemRequestsInstance = new ItemRequests(params)
        if (!itemRequestsInstance.save(flush: true)) {
            render(view: "create", model: [itemRequestsInstance: itemRequestsInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'itemRequests.label', default: 'ItemRequests'), itemRequestsInstance.id])
        redirect(action: "show", id: itemRequestsInstance.id)
    }

    def show(Long id) {
        def itemRequestsInstance = ItemRequests.get(id)
        if (!itemRequestsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'itemRequests.label', default: 'ItemRequests'), id])
            redirect(action: "list")
            return
        }

        [itemRequestsInstance: itemRequestsInstance]
    }

    def edit(Long id) {
        def itemRequestsInstance = ItemRequests.get(id)
        if (!itemRequestsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'itemRequests.label', default: 'ItemRequests'), id])
            redirect(action: "list")
            return
        }

        [itemRequestsInstance: itemRequestsInstance]
    }

    def update(Long id, Long version) {
        def itemRequestsInstance = ItemRequests.get(id)
        if (!itemRequestsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'itemRequests.label', default: 'ItemRequests'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (itemRequestsInstance.version > version) {
                itemRequestsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'itemRequests.label', default: 'ItemRequests')] as Object[],
                          "Another user has updated this ItemRequests while you were editing")
                render(view: "edit", model: [itemRequestsInstance: itemRequestsInstance])
                return
            }
        }

        itemRequestsInstance.properties = params

        if (!itemRequestsInstance.save(flush: true)) {
            render(view: "edit", model: [itemRequestsInstance: itemRequestsInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'itemRequests.label', default: 'ItemRequests'), itemRequestsInstance.id])
        redirect(action: "show", id: itemRequestsInstance.id)
    }

    def delete(Long id) {
        def itemRequestsInstance = ItemRequests.get(id)
        if (!itemRequestsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'itemRequests.label', default: 'ItemRequests'), id])
            redirect(action: "list")
            return
        }

        try {
            itemRequestsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'itemRequests.label', default: 'ItemRequests'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'itemRequests.label', default: 'ItemRequests'), id])
            redirect(action: "show", id: id)
        }
    }
}
