(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('SubcategoryDetailController', SubcategoryDetailController);

    SubcategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Subcategory', 'SubcategoryLang', 'Action', 'Request', 'Category'];

    function SubcategoryDetailController($scope, $rootScope, $stateParams, entity, Subcategory, SubcategoryLang, Action, Request, Category) {
        var vm = this;

        vm.subcategory = entity;

        var unsubscribe = $rootScope.$on('demoApp:subcategoryUpdate', function(event, result) {
            vm.subcategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
