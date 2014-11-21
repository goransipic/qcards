package com.Qcards

import grails.plugins.springsecurity.Secured

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.dao.DataIntegrityViolationException

class UserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	@Secured(['ROLE_ADMIN'])
    def index() {
        redirect(action: "list", params: params)
    }
	
	@Secured(['ROLE_ADMIN'])
    def list(Integer max) {
		def typeOfUser
		
        params.max = Math.min(max ?: 10, 100)
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			typeOfUser = "admin"
		}
		else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			typeOfUser = "partner"
		}
		def userInstanceList = User.where{
			hasDeleted == false
		}
		userInstanceList = userInstanceList.list(params)
		
        [userInstanceList: userInstanceList, userInstanceTotal: User.count(),  typeOfUser:  typeOfUser]
    }
	
	@Secured(['ROLE_ADMIN'])
    def create() {
		def typeOfUser
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			typeOfUser = "admin"
		}
		else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			typeOfUser = "partner"
		}
		
        [userInstance: new User(params), typeOfUser:typeOfUser]
    }
	
	@Secured(['ROLE_ADMIN'])
    def save() {
        def userInstance = new User(params)
        if (!userInstance.save(flush: true)) {
            render(view: "create", model: [userInstance: userInstance])
            return
        }
		def partnerRole = Role.findByAuthority('ROLE_PARTNER')
		
		def relationship = new UserRole(user: userInstance, role: partnerRole).save(failOnError: true)
		
        flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "edit", id: userInstance.id)
		
    }
	
	@Secured(['ROLE_ADMIN'])
    def show(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }
	
	@Secured(['ROLE_ADMIN'])
    def edit(Long id) {
		
		def typeOfUser
		
		if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN')) {
			typeOfUser = "admin"
		}
		else if (SpringSecurityUtils.ifAllGranted('ROLE_PARTNER')) {
			typeOfUser = "partner"
		}
		
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance, typeOfUser: typeOfUser]
    }
	
	@Secured(['ROLE_ADMIN'])
    def update(Long id, Long version) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'user.label', default: 'User')] as Object[],
                          "Another user has updated this User while you were editing")
                render(view: "edit", model: [userInstance: userInstance])
                return
            }
        }

        userInstance.properties = params

        if (!userInstance.save(flush: true)) {
            render(view: "edit", model: [userInstance: userInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "edit", id: userInstance.id)
    }
	
	@Secured(['ROLE_ADMIN'])
    def delete(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

      /*  try {
            userInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "show", id: id)
        } */
		userInstance.hasDeleted = true
		userInstance.enabled = false
		
		if (!userInstance.save(flush: true)) {
			render(view: "edit", model: [userInstance: userInstance])
			return
		}
		redirect(action: "list")
    }
}
