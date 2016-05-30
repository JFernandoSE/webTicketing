'use strict';

describe('Controller Tests', function() {

    describe('CategoryLang Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCategoryLang, MockCategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCategoryLang = jasmine.createSpy('MockCategoryLang');
            MockCategory = jasmine.createSpy('MockCategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CategoryLang': MockCategoryLang,
                'Category': MockCategory
            };
            createController = function() {
                $injector.get('$controller')("CategoryLangDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'demoApp:categoryLangUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
