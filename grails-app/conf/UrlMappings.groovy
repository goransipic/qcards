class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		
		"/login/$action?"(controller: "login")
		
		"/logout/$action?"(controller: "logout")
		
//		"/" {
//			controller = "Router"
//			action = "index"
//		 }
		
		"/" {
			controller = "Category"
			action = "index"
		 }
		
		"500"(view:'/error')
		
		"/item/get/$code" {
			controller = "Item"
			action = "get"
		 }
		
	}
}
