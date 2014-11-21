import com.Qcards.ItemRequests
import com.Qcards.Role
import com.Qcards.User
import com.Qcards.UserRole

class BootStrap {

    def init = { servletContext ->
		
		def adminRole
		def adminUser
		
		if(!Role.findByAuthority('ROLE_ADMIN'))
			adminRole = new Role(authority: 'ROLE_ADMIN').save(failOnError: true)
		if(!Role.findByAuthority('ROLE_PARTNER'))
			def partnerRole = new Role(authority: 'ROLE_PARTNER').save(failOnError: true)

		adminUser =User.findByUsername('admin')?: new User(	username:'admin',
															password:'dingac708',																
															enabled:true).save(failOnError:true)
										
		if(!UserRole.findByUser(adminUser))
			def adminRelationship = new UserRole(user: adminUser, role: adminRole).save(failOnError: true)
		
		
//		def adminUser =User.findByUsername('admin')?: new User(
//			username:'admin',
//			password:'dingac708',
//			
//			enabled:true).save(failOnError:true)  
    }
	
	def destroy = {
    }
}
