'use strict';

describe('Controller Tests', function() {

    describe('Subcategory Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSubcategory, MockSubcategoryLang, MockAction, MockRequest, MockCategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSubcategory = jasmine.createSpy('MockSubcategory');
            MockSubcategoryLang = jasmine.createSpy('MockSubcategoryLang');
            MockAction = jasmine.createSpy('MockAction');
            MockRequest = jasmine.createSpy('MockRequest');
            MockCategory = jasmine.createSpy('MockCategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Subcategory': MockSubcategory,
                'SubcategoryLang': MockSubcategoryLang,
                'Action': MockAction,
                'Request': MockRequest,
                'Category': MockCategory
            };
            createController = function() {
                $injector.get('$controller')("SubcategoryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'demoApp:subcategoryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
