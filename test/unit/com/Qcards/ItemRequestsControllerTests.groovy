package com.Qcards



import org.junit.*
import grails.test.mixin.*

@TestFor(ItemRequestsController)
@Mock(ItemRequests)
class ItemRequestsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/itemRequests/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.itemRequestsInstanceList.size() == 0
        assert model.itemRequestsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.itemRequestsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.itemRequestsInstance != null
        assert view == '/itemRequests/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/itemRequests/show/1'
        assert controller.flash.message != null
        assert ItemRequests.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/itemRequests/list'

        populateValidParams(params)
        def itemRequests = new ItemRequests(params)

        assert itemRequests.save() != null

        params.id = itemRequests.id

        def model = controller.show()

        assert model.itemRequestsInstance == itemRequests
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/itemRequests/list'

        populateValidParams(params)
        def itemRequests = new ItemRequests(params)

        assert itemRequests.save() != null

        params.id = itemRequests.id

        def model = controller.edit()

        assert model.itemRequestsInstance == itemRequests
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/itemRequests/list'

        response.reset()

        populateValidParams(params)
        def itemRequests = new ItemRequests(params)

        assert itemRequests.save() != null

        // test invalid parameters in update
        params.id = itemRequests.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/itemRequests/edit"
        assert model.itemRequestsInstance != null

        itemRequests.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/itemRequests/show/$itemRequests.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        itemRequests.clearErrors()

        populateValidParams(params)
        params.id = itemRequests.id
        params.version = -1
        controller.update()

        assert view == "/itemRequests/edit"
        assert model.itemRequestsInstance != null
        assert model.itemRequestsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/itemRequests/list'

        response.reset()

        populateValidParams(params)
        def itemRequests = new ItemRequests(params)

        assert itemRequests.save() != null
        assert ItemRequests.count() == 1

        params.id = itemRequests.id

        controller.delete()

        assert ItemRequests.count() == 0
        assert ItemRequests.get(itemRequests.id) == null
        assert response.redirectedUrl == '/itemRequests/list'
    }
}
