package qcards

import com.Qcards.ItemRequests


class NumberOfRequestsJob {
    static triggers = {
       cron name:'cronTrigger', cronExpression: "0 0 4 * * ?"
    }

    def execute() {
        // execute job
		
//		def itemRequestsInstanceList = ItemRequests.list()
//		
//		for (itemRequestsInstance in itemRequestsInstanceList ){
//			
//			itemRequestsInstance.numberofRequests = 0
//			itemRequestsInstance.save(flush: true)
//		}
		
    }
}
