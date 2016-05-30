'use strict';

describe('Controller Tests', function() {

    describe('ActionLang Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockActionLang, MockAction;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockActionLang = jasmine.createSpy('MockActionLang');
            MockAction = jasmine.createSpy('MockAction');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ActionLang': MockActionLang,
                'Action': MockAction
            };
            createController = function() {
                $injector.get('$controller')("ActionLangDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'demoApp:actionLangUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
