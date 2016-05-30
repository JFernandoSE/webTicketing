'use strict';

describe('Controller Tests', function() {

    describe('Request Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRequest, MockCategory, MockSubcategory, MockAction;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRequest = jasmine.createSpy('MockRequest');
            MockCategory = jasmine.createSpy('MockCategory');
            MockSubcategory = jasmine.createSpy('MockSubcategory');
            MockAction = jasmine.createSpy('MockAction');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Request': MockRequest,
                'Category': MockCategory,
                'Subcategory': MockSubcategory,
                'Action': MockAction
            };
            createController = function() {
                $injector.get('$controller')("RequestDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'demoApp:requestUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
