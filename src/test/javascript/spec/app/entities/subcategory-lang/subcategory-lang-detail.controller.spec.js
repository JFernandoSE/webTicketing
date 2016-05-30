'use strict';

describe('Controller Tests', function() {

    describe('SubcategoryLang Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSubcategoryLang, MockSubcategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSubcategoryLang = jasmine.createSpy('MockSubcategoryLang');
            MockSubcategory = jasmine.createSpy('MockSubcategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'SubcategoryLang': MockSubcategoryLang,
                'Subcategory': MockSubcategory
            };
            createController = function() {
                $injector.get('$controller')("SubcategoryLangDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'demoApp:subcategoryLangUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
