'use strict';

describe('Controller Tests', function() {

    describe('Action Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAction, MockActionLang, MockRequest, MockSubcategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAction = jasmine.createSpy('MockAction');
            MockActionLang = jasmine.createSpy('MockActionLang');
            MockRequest = jasmine.createSpy('MockRequest');
            MockSubcategory = jasmine.createSpy('MockSubcategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Action': MockAction,
                'ActionLang': MockActionLang,
                'Request': MockRequest,
                'Subcategory': MockSubcategory
            };
            createController = function() {
                $injector.get('$controller')("ActionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'demoApp:actionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
