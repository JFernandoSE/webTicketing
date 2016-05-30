'use strict';

describe('Controller Tests', function() {

    describe('Category Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCategory, MockCategoryLang, MockSubcategory, MockRequest;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCategory = jasmine.createSpy('MockCategory');
            MockCategoryLang = jasmine.createSpy('MockCategoryLang');
            MockSubcategory = jasmine.createSpy('MockSubcategory');
            MockRequest = jasmine.createSpy('MockRequest');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Category': MockCategory,
                'CategoryLang': MockCategoryLang,
                'Subcategory': MockSubcategory,
                'Request': MockRequest
            };
            createController = function() {
                $injector.get('$controller')("CategoryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'demoApp:categoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
